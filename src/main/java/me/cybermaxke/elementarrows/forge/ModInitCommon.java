/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.forge;

import java.io.File;
import java.io.IOException;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import me.cybermaxke.elementarrows.forge.arrows.ArrowRegistryCommon;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowBlindness;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowDazing;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowDirt;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowEgg;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowEnderEye;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowExplosion;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowFire;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowIce;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowLightning;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowPoison;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowRazor;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowVampiric;
import me.cybermaxke.elementarrows.forge.arrows.custom.ArrowVolley;
import me.cybermaxke.elementarrows.forge.dispenser.DispenseElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrowListener;
import me.cybermaxke.elementarrows.forge.entity.EntityRegistry;
import me.cybermaxke.elementarrows.forge.item.ItemArrow;
import me.cybermaxke.elementarrows.forge.item.ItemArrowTab;
import me.cybermaxke.elementarrows.forge.item.ItemBow;
import me.cybermaxke.elementarrows.forge.item.ItemRegistry;
import me.cybermaxke.elementarrows.forge.json.JsonFactory;
import me.cybermaxke.elementarrows.forge.network.HandlerModInfo;
import me.cybermaxke.elementarrows.forge.network.MessageInjectorCommon;
import me.cybermaxke.elementarrows.forge.network.MessageModInfo;
import me.cybermaxke.elementarrows.forge.recipe.RecipeManager;

public class ModInitCommon {
	/**
	 * The arrow registry.
	 */
	public ArrowRegistryCommon registry;

	/**
	 * The players info.
	 */
	public EPlayers players;

	/**
	 * The overridden items.
	 */
	public ItemArrow itemArrow;
	public ItemBow itemBow;

	/**
	 * Arrows item tab.
	 */
	public ItemArrowTab arrowsTab;

	/**
	 * The item registry.
	 */
	public ItemRegistry itemRegistry;

	/**
	 * The entity registry.
	 */
	public EntityRegistry entityRegistry;

	/**
	 * The network wrapper.
	 */
	public SimpleNetworkWrapper network;

	/**
	 * The json factory.
	 */
	public JsonFactory jsonFactory;

	/**
	 * The recipe manager.
	 */
	public RecipeManager recipeManager;

	/**
	 * Listener to define when the arrow a entity hits.
	 */
	private EntityElementArrowListener listener0;

	/**
	 * The main directory.
	 */
	private File mainDirectory;

	/**
	 * Called in the initialize event.
	 */
	public void onInit() {
		this.jsonFactory = new JsonFactory();
		this.recipeManager = new RecipeManager(this.jsonFactory);
		this.registry = this.newArrowRegistry();
		this.itemRegistry = new ItemRegistry();
		this.entityRegistry = new EntityRegistry();
		this.players = new EPlayers();

		/**
		 * Create the arrow damage listener.
		 */
		this.listener0 = new EntityElementArrowListener(this.registry);

		/**
		 * Create the new items.
		 */
		this.itemArrow = new ItemArrow(this.registry);
		this.itemBow = new ItemBow(this.registry);

		/**
		 * Create the arrows tab.
		 */
		this.arrowsTab = new ItemArrowTab("elementArrowsTab", this.itemArrow, this.registry);

		/**
		 * Set the new tab of the arrow and bow item.
		 */
		this.itemArrow.setCreativeTab(this.arrowsTab);
		this.itemBow.setCreativeTab(this.arrowsTab);

		/**
		 * Register the new items and override the old ones.
		 */
		this.itemRegistry.register("minecraft:arrow", this.itemArrow);
		this.itemRegistry.register("minecraft:bow", this.itemBow);

		BlockDispenser.dispenseBehaviorRegistry.putObject(this.itemArrow, new DispenseElementArrow(this.registry));

		/**
		 * Override the normal arrow.
		 */
		this.entityRegistry.register(EntityElementArrow.class, "Arrow", 10);

		/**
		 * Register the listener.
		 */
		MinecraftForge.EVENT_BUS.register(this.listener0);

		/**
		 * Register the custom arrows.
		 */
		try {
			this.onInitArrows();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.network = NetworkRegistry.INSTANCE.newSimpleChannel("elementArrows");
		this.network.registerMessage(new HandlerModInfo(this.players), MessageModInfo.class, 1, Side.SERVER);

		/**
		 * Add the protocol injector to support the elemental
		 * arrow names to be visible on vanilla clients.
		 */
		MessageInjectorCommon injector = new MessageInjectorCommon(this.registry, this.players);
		injector.onInit();
	}

	/**
	 * Called in the initialize event.
	 * @throws IOException 
	 */
	public void onInitArrows() throws IOException {
		File directory = new File(this.mainDirectory + File.separator + "elementArrows" + File.separator + "arrows");

		this.registry.register(1, this.jsonFactory.fromJsonFile(new File(directory, "arrowBlindness.json"), ArrowBlindness.class));
		this.registry.register(2, this.jsonFactory.fromJsonFile(new File(directory, "arrowDazing.json"), ArrowDazing.class));
		this.registry.register(3, this.jsonFactory.fromJsonFile(new File(directory, "arrowDirt.json"), ArrowDirt.class));
		this.registry.register(4, this.jsonFactory.fromJsonFile(new File(directory, "arrowEgg.json"), ArrowEgg.class));
		this.registry.register(5, this.jsonFactory.fromJsonFile(new File(directory, "arrowEnderEye.json"), ArrowEnderEye.class));
		this.registry.register(6, this.jsonFactory.fromJsonFile(new File(directory, "arrowExplosion.json"), ArrowExplosion.class));
		this.registry.register(7, this.jsonFactory.fromJsonFile(new File(directory, "arrowFire.json"), ArrowFire.class));
		this.registry.register(8, this.jsonFactory.fromJsonFile(new File(directory, "arrowLightning.json"), ArrowLightning.class));
		this.registry.register(9, this.jsonFactory.fromJsonFile(new File(directory, "arrowPoison.json"), ArrowPoison.class));
		this.registry.register(10, this.jsonFactory.fromJsonFile(new File(directory, "arrowRazor.json"), ArrowRazor.class));
		this.registry.register(11, this.jsonFactory.fromJsonFile(new File(directory, "arrowVampiric.json"), ArrowVampiric.class));
		this.registry.register(12, this.jsonFactory.fromJsonFile(new File(directory, "arrowVolley.json"), ArrowVolley.class));
		this.registry.register(13, this.jsonFactory.fromJsonFile(new File(directory, "arrowIce.json"), ArrowIce.class));

		GameRegistry.addShapedRecipe(new ItemStack(this.itemBow), " xy", "x y", " xy",
				'x', new ItemStack(Items.stick),
				'y', new ItemStack(Items.string));
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.dispenser), "xxx", "xyx", "xzx",
				'x', new ItemStack(Blocks.cobblestone),
				'y', new ItemStack(this.itemBow),
				'z', new ItemStack(Items.redstone));
		GameRegistry.addShapedRecipe(new ItemStack(this.itemArrow), "x", "y", "z",
				'x', new ItemStack(Items.flint),
				'y', new ItemStack(Items.stick),
				'z', new ItemStack(Items.feather));
	}

	/**
	 * Called in the pre initialize event.
	 * 
	 * @param file the main directory
	 */
	public void onPreInit(File file) {
		this.mainDirectory = file;
	}

	/**
	 * Called in the post initialize event.
	 */
	public void onPostInit() {
		try {
			this.recipeManager.load(new File(this.mainDirectory + File.separator + "elementArrows", "recipes.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called in the disable event.
	 */
	public void onDisable() {

	}

	/**
	 * Gets a new elemental arrow registry.
	 * 
	 * @return the registry
	 */
	public ArrowRegistryCommon newArrowRegistry() {
		return new ArrowRegistryCommon(this.recipeManager);
	}

}