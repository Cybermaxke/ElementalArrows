/*
 * This file is part of ElementalArrows, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered <https://github.com/Cybermaxke/ElementalArrows>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.elementalarrows;

import static org.spongepowered.api.Sponge.getEventManager;
import static org.spongepowered.api.Sponge.getRegistry;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import org.lanternpowered.elementalarrows.arrow.ArrowEventHandler;
import org.lanternpowered.elementalarrows.arrow.ArrowKeys;
import org.lanternpowered.elementalarrows.arrow.CustomArrow;
import org.lanternpowered.elementalarrows.arrow.event.ArrowHitEntityEvent;
import org.lanternpowered.elementalarrows.arrow.event.ArrowHitGroundEvent;
import org.lanternpowered.elementalarrows.arrow.event.ArrowShotEvent;
import org.lanternpowered.elementalarrows.event.EventActionSet;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.lanternpowered.elementalarrows.function.data.AddPotionEffects;
import org.lanternpowered.elementalarrows.function.data.ModifyVelocity;
import org.lanternpowered.elementalarrows.function.data.SetDataKey;
import org.lanternpowered.elementalarrows.function.entity.DestroyEntity;
import org.lanternpowered.elementalarrows.function.locatable.CreateExplosion;
import org.lanternpowered.elementalarrows.function.locatable.PlaySound;
import org.lanternpowered.elementalarrows.function.locatable.SpawnEntity;
import org.lanternpowered.elementalarrows.function.locatable.SpawnParticles;
import org.lanternpowered.elementalarrows.item.BaseItem;
import org.lanternpowered.elementalarrows.item.ConstructableItem;
import org.lanternpowered.elementalarrows.item.InbuiltBaseItem;
import org.lanternpowered.elementalarrows.item.CustomItem;
import org.lanternpowered.elementalarrows.parser.gson.BlockStateDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.CatalogTypeAdapterFactory;
import org.lanternpowered.elementalarrows.parser.gson.EventActionSetDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.GsonParser;
import org.lanternpowered.elementalarrows.parser.gson.JsonTypeRegistryObjectDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.ObjectConsumerDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.ParticleEffectDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.PotionEffectDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.TextDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.Vector3dDeserializer;
import org.lanternpowered.elementalarrows.registry.CatalogTypeRegistry;
import org.lanternpowered.elementalarrows.registry.SimpleCatalogTypeRegistry;
import org.lanternpowered.elementalarrows.registry.SimpleTypeRegistry;
import org.lanternpowered.elementalarrows.registry.TypeRegistry;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

@Plugin(id = "elemental_arrows")
public final class ElementalArrowsPlugin {

    /**
     * The registry of all the {@link ObjectConsumer}s classes.
     */
    private final TypeRegistry<ObjectConsumer> objectConsumersTypeRegistry = new SimpleTypeRegistry<>();

    /**
     * The registry of all the {@link BaseItem}s classes.
     */
    private final TypeRegistry<BaseItem> itemTypeRegistry = new SimpleTypeRegistry<>();

    /**
     * The registry of all the {@link BaseItem}s.
     */
    private final CatalogTypeRegistry<BaseItem> itemRegistry = new SimpleCatalogTypeRegistry<>();

    private final TypeRegistry<Event> eventTypeRegistry = new SimpleTypeRegistry<>();

    @Inject
    private PluginContainer pluginContainer;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configFolder;

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {

    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        // Extract the default configs
        final JarFile jarFile;
        try {
            jarFile = new JarFile(this.pluginContainer.getSource().get().toFile());
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }

        final Pattern jsonPattern = Pattern.compile(".+");
        final Path assets = this.configFolder.resolve("assets");

        if (!Files.exists(assets)) {
            try {
                extractFolder(jarFile, "assets", assets, jsonPattern);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to extract assets", e);
            }
        }

        // Object consumer types
        this.objectConsumersTypeRegistry.register("add-potion-effects", AddPotionEffects.class);
        this.objectConsumersTypeRegistry.register("modify-velocity", ModifyVelocity.class);
        this.objectConsumersTypeRegistry.register("set-data-key", SetDataKey.class);
        this.objectConsumersTypeRegistry.register("destroy-entity", DestroyEntity.class);
        this.objectConsumersTypeRegistry.register("create-explosion", CreateExplosion.class);
        this.objectConsumersTypeRegistry.register("play-sound", PlaySound.class);
        this.objectConsumersTypeRegistry.register("spawn-entity", SpawnEntity.class);
        this.objectConsumersTypeRegistry.register("spawn-particles", SpawnParticles.class);

        // Base item types
        this.itemTypeRegistry.register("base-item", CustomItem.class);
        this.itemTypeRegistry.register("base-arrow", CustomArrow.class);

        this.eventTypeRegistry.register("arrow-hit-entity", ArrowHitEntityEvent.class);
        this.eventTypeRegistry.register("arrow-hit-ground", ArrowHitGroundEvent.class);
        this.eventTypeRegistry.register("arrow-shot", ArrowShotEvent.class);

        getRegistry().registerModule(BaseItem.class, this.itemRegistry);

        // Register the default item types
        getRegistry().getAllOf(ItemType.class)
                .forEach(type -> this.itemRegistry.register(type.getId(), new InbuiltBaseItem(type)));

        getRegistry().register(Key.class, ArrowKeys.ARROW_TYPE);
        getEventManager().registerListeners(this, new ArrowEventHandler());

        // Setup the gson parser
        final GsonParser gsonParser = new GsonParser();
        gsonParser.registerTypeAdapter(BlockState.class, new BlockStateDeserializer());
        gsonParser.registerTypeAdapterFactory(new CatalogTypeAdapterFactory());
        gsonParser.registerTypeAdapter(ConstructableItem.class, new JsonTypeRegistryObjectDeserializer<>(this.itemTypeRegistry));
        gsonParser.registerTypeAdapter(BaseItem.class, new JsonTypeRegistryObjectDeserializer<>(this.itemTypeRegistry));
        gsonParser.registerTypeAdapter(PotionEffect.class, new PotionEffectDeserializer());
        gsonParser.registerTypeAdapter(ParticleEffect.class, new ParticleEffectDeserializer());
        gsonParser.registerTypeAdapter(Text.class, new TextDeserializer());
        gsonParser.registerTypeAdapter(Vector3d.class, new Vector3dDeserializer());
        gsonParser.registerTypeAdapter(ModifyVelocity.Type.class, new ModifyVelocity.TypeDeserializer());
        gsonParser.registerTypeAdapter(EventActionSet.class, new EventActionSetDeserializer(this.eventTypeRegistry));
        gsonParser.registerTypeAdapter(ObjectConsumer.class, new ObjectConsumerDeserializer(this.objectConsumersTypeRegistry));

        try {
            Files.list(assets).forEach(path -> {
                final Path items = path.resolve("items");
                if (Files.exists(items)) {
                    try {
                        Files.list(items).forEach(item -> {
                            if (!item.getFileName().toString().endsWith(".json")) {
                                return;
                            }
                            try (BufferedReader reader = Files.newBufferedReader(item)) {
                                try {
                                    final BaseItem baseItem = gsonParser.getGson().fromJson(reader, ConstructableItem.class);
                                    this.itemRegistry.register(baseItem.getId(), baseItem);
                                    this.logger.info("Successfully registered the BaseItem with id {} from the path: {}", baseItem.getId(), item);
                                } catch (Exception e) {
                                    this.logger.error("Failed to parse the BaseItem from the path: {}", item, e);
                                }
                            } catch (IOException e) {
                                throw Throwables.propagate(e);
                            }
                        });
                    } catch (IOException e) {
                        throw Throwables.propagate(e);
                    }
                }
            });
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Listener
    public void onPostInit(GamePostInitializationEvent event) {
    }

    private static void extractFolder(JarFile jarFile, String folder, Path outputFolder, Pattern namePattern) throws IOException {
        if (!folder.endsWith("/")) {
            folder = folder + "/";
        }
        final java.util.Enumeration<JarEntry> it = jarFile.entries();
        while (it.hasMoreElements()) {
            final JarEntry entry = it.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            String name = entry.getName();
            if (!name.startsWith(folder)) {
                continue;
            }
            name = name.replaceFirst(folder, "");
            final String name0;
            if (name.contains("/")) {
                name0 = name.substring(name.lastIndexOf('/') + 1, name.length());
            } else {
                name0 = name;
            }
            if (!namePattern.matcher(name0).matches()) {
                continue;
            }
            final Path path = outputFolder.resolve(name);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            try (OutputStream os = Files.newOutputStream(path); InputStream is = jarFile.getInputStream(entry)) {
                ByteStreams.copy(is, os);
                os.flush();
            }
        }
    }
}
