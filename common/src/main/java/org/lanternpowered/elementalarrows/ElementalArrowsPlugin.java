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

import org.lanternpowered.elementalarrows.arrow.BaseArrow;
import org.lanternpowered.elementalarrows.function.ObjectConsumer;
import org.lanternpowered.elementalarrows.function.data.AddPotionEffects;
import org.lanternpowered.elementalarrows.function.data.SetDataKey;
import org.lanternpowered.elementalarrows.function.entity.DestroyEntity;
import org.lanternpowered.elementalarrows.function.locatable.CreateExplosion;
import org.lanternpowered.elementalarrows.function.locatable.PlaySound;
import org.lanternpowered.elementalarrows.item.BaseItem;
import org.lanternpowered.elementalarrows.parser.gson.GsonParser;
import org.lanternpowered.elementalarrows.parser.gson.JsonTypeRegistryObjectDeserializer;
import org.lanternpowered.elementalarrows.parser.gson.ObjectConsumerDeserializer;
import org.lanternpowered.elementalarrows.registry.SimpleTypeRegistry;
import org.lanternpowered.elementalarrows.registry.TypeRegistry;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "elemental_arrows")
public final class ElementalArrowsPlugin {

    /**
     * The registry of all the {@link ObjectConsumer}s classes.
     */
    private final TypeRegistry<ObjectConsumer> objectConsumersTypeRegistry = new SimpleTypeRegistry<>();

    /**
     * The registry of all the {@link BaseItem}s classes.
     */
    private final TypeRegistry<BaseItem> itemTypesRegistry = new SimpleTypeRegistry<>();

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {

    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        // Object consumer types
        this.objectConsumersTypeRegistry.register("add-potion-effects", AddPotionEffects.class);
        this.objectConsumersTypeRegistry.register("set-data-key", SetDataKey.class);
        this.objectConsumersTypeRegistry.register("destroy-entity", DestroyEntity.class);
        this.objectConsumersTypeRegistry.register("create-explosion", CreateExplosion.class);
        this.objectConsumersTypeRegistry.register("play-sound", PlaySound.class);

        // Base item types
        this.itemTypesRegistry.register("base-item", BaseItem.class);
        this.itemTypesRegistry.register("base-arrow", BaseArrow.class);

        // Setup the gson parser
        final GsonParser gsonParser = new GsonParser();
        gsonParser.registerTypeAdapter(ObjectConsumer.class, new ObjectConsumerDeserializer(this.objectConsumersTypeRegistry));
        gsonParser.registerTypeAdapter(BaseItem.class, new JsonTypeRegistryObjectDeserializer<>(this.itemTypesRegistry));
    }

    @Listener
    public void onPostInit(GamePostInitializationEvent event) {
    }
}
