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
package me.cybermaxke.elementarrows.forge.v1800;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import me.cybermaxke.elementarrows.common.PluginBase;
import me.cybermaxke.elementarrows.common.arrow.ArrowRegistry;
import me.cybermaxke.elementarrows.common.arrow.ArrowRegistryBase;
import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.block.BlockFactory;
import me.cybermaxke.elementarrows.common.block.Blocks;
import me.cybermaxke.elementarrows.common.enchant.EnchantFactory;
import me.cybermaxke.elementarrows.common.enchant.Enchants;
import me.cybermaxke.elementarrows.common.entity.Entities;
import me.cybermaxke.elementarrows.common.entity.EntityFactory;
import me.cybermaxke.elementarrows.common.item.ItemFactory;
import me.cybermaxke.elementarrows.common.item.Items;
import me.cybermaxke.elementarrows.common.json.Json;
import me.cybermaxke.elementarrows.common.json.JsonFactory;
import me.cybermaxke.elementarrows.common.locale.LocaleRegistry;
import me.cybermaxke.elementarrows.common.locale.Locales;
import me.cybermaxke.elementarrows.common.potion.PotionFactory;
import me.cybermaxke.elementarrows.common.potion.Potions;
import me.cybermaxke.elementarrows.common.recipe.RecipeFactory;
import me.cybermaxke.elementarrows.common.recipe.Recipes;
import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import me.cybermaxke.elementarrows.common.world.WorldManager;
import me.cybermaxke.elementarrows.common.world.Worlds;
import me.cybermaxke.elementarrows.forge.v1800.block.FBlockFactory;
import me.cybermaxke.elementarrows.forge.v1800.dispenser.DispenseElementArrow;
import me.cybermaxke.elementarrows.forge.v1800.enchant.FEnchantFactory;
import me.cybermaxke.elementarrows.forge.v1800.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.v1800.entity.EntityElementArrowListener;
import me.cybermaxke.elementarrows.forge.v1800.entity.EntityRegistry;
import me.cybermaxke.elementarrows.forge.v1800.entity.FEntityFactory;
import me.cybermaxke.elementarrows.forge.v1800.entity.FEntityTickHandler;
import me.cybermaxke.elementarrows.forge.v1800.inventory.FItemFactory;
import me.cybermaxke.elementarrows.forge.v1800.item.ItemArrow;
import me.cybermaxke.elementarrows.forge.v1800.item.ItemArrowTab;
import me.cybermaxke.elementarrows.forge.v1800.item.ItemBow;
import me.cybermaxke.elementarrows.forge.v1800.item.ItemRegistry;
import me.cybermaxke.elementarrows.forge.v1800.json.FJsonFactory;
import me.cybermaxke.elementarrows.forge.v1800.locale.FLocaleRegistry;
import me.cybermaxke.elementarrows.forge.v1800.network.MessageInjectorCommon;
import me.cybermaxke.elementarrows.forge.v1800.potion.FPotionFactory;
import me.cybermaxke.elementarrows.forge.v1800.recipe.FRecipeFactory;
import me.cybermaxke.elementarrows.forge.v1800.world.FWorldManager;

public class FProxyCommon implements FProxy {
	public static ArrowRegistry arrows;
	public static FBlockFactory blocks;
	public static FEnchantFactory enchants;
	public static FPotionFactory potions;
	public static FRecipeFactory recipes;
	public static FLocaleRegistry locales;
	public static FEntityFactory entities;
	public static FWorldManager worlds;
	public static FItemFactory items;
	public static FJsonFactory json;

	private PluginBase plugin = new PluginBase();

	@Override
	public void onPreInit(File file) {
		this.plugin.onPreInit(file);
	}

	@Override
	public void onInit(File file) {
		entities = this.newEntities();
		entities.onInit();

		arrows = this.newArrows();
		blocks = new FBlockFactory();
		worlds = new FWorldManager();
		recipes = new FRecipeFactory();
		locales = new FLocaleRegistry();
		items = new FItemFactory();
		json = new FJsonFactory();

		try {
			enchants = new FEnchantFactory();
			potions = new FPotionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			setFactoryInstance(Arrows.class, ArrowRegistry.class, arrows);
			setFactoryInstance(Blocks.class, BlockFactory.class, blocks);
			setFactoryInstance(Enchants.class, EnchantFactory.class, enchants);
			setFactoryInstance(Locales.class, LocaleRegistry.class, locales);
			setFactoryInstance(Recipes.class, RecipeFactory.class, recipes);
			setFactoryInstance(Entities.class, EntityFactory.class, entities);
			setFactoryInstance(Potions.class, PotionFactory.class, potions);
			setFactoryInstance(Worlds.class, WorldManager.class, worlds);
			setFactoryInstance(Items.class, ItemFactory.class, items);
			setFactoryInstance(Json.class, JsonFactory.class, json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * Create the arrows (archery) tab.
		 */
		CreativeTabs archery = new ItemArrowTab("elementArrowsTab");

		/**
		 * Create the new items.
		 */
		Item itemArrow = new ItemArrow();
		Item itemBow = new ItemBow();

		itemArrow.setCreativeTab(archery);
		itemBow.setCreativeTab(archery);

		ItemRegistry itemRegistry = new ItemRegistry();
		itemRegistry.register("minecraft:arrow", itemArrow);
		itemRegistry.register("minecraft:bow", itemBow);

		/**
		 * Override the default arrow.
		 */
		EntityRegistry entityRegistry = new EntityRegistry();
		entityRegistry.register(EntityElementArrow.class, "Arrow", 10);

		/**
		 * Initialize the server message injector.
		 */
		MessageInjectorCommon injector = new MessageInjectorCommon();
		injector.onInit();

		EntityElementArrowListener listener = new EntityElementArrowListener();
		listener.onInit();

		FEntityTickHandler handler = new FEntityTickHandler();
		handler.onInit();

		/**
		 * Register the new dispenser behavior.
		 */
		BlockDispenser.dispenseBehaviorRegistry.putObject(itemArrow, new DispenseElementArrow());

		this.plugin.onInit(file);
	}

	@Override
	public void onPostInit(File file) {
		this.plugin.onPostInit(file);
	}

	/**
	 * Gets a new entity factory instance.
	 * 
	 * @return the instance
	 */
	protected FEntityFactory newEntities() {
		return new FEntityFactory();
	}

	/**
	 * Gets a new arrow registry instance.
	 * 
	 * @return the instance
	 */
	protected ArrowRegistry newArrows() {
		return new ArrowRegistryBase();
	}

	protected static void setFactoryInstance(Class<?> target, Class<?> type, Object instance) throws Exception {
		Field field = Fields.findField(target, type, 0, false);
		field.setAccessible(true);

		int modifiers = field.getModifiers();

		if (Modifier.isFinal(modifiers)) {
			Field field0 = Field.class.getDeclaredField("modifiers");
			field0.setAccessible(true);
			field0.set(field, modifiers & ~Modifier.FINAL);
		}

		field.set(null, instance);
	}

}