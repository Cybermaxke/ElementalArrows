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
package me.cybermaxke.elementarrows.forge.v1710;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import me.cybermaxke.elementarrows.common.PluginBase;
import me.cybermaxke.elementarrows.common.entity.Entities;
import me.cybermaxke.elementarrows.common.entity.EntityFactory;
import me.cybermaxke.elementarrows.common.inventory.ItemFactory;
import me.cybermaxke.elementarrows.common.inventory.ItemStacks;
import me.cybermaxke.elementarrows.common.locale.LocaleRegistry;
import me.cybermaxke.elementarrows.common.locale.Locales;
import me.cybermaxke.elementarrows.common.potion.PotionFactory;
import me.cybermaxke.elementarrows.common.potion.Potions;
import me.cybermaxke.elementarrows.common.recipe.RecipeFactory;
import me.cybermaxke.elementarrows.common.recipe.Recipes;
import me.cybermaxke.elementarrows.common.world.WorldManager;
import me.cybermaxke.elementarrows.common.world.Worlds;
import me.cybermaxke.elementarrows.forge.v1710.dispenser.DispenseElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityElementArrowListener;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityRegistry;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityFactory;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityTickHandler;
import me.cybermaxke.elementarrows.forge.v1710.inventory.FItemFactory;
import me.cybermaxke.elementarrows.forge.v1710.item.ItemArrow;
import me.cybermaxke.elementarrows.forge.v1710.item.ItemArrowTab;
import me.cybermaxke.elementarrows.forge.v1710.item.ItemBow;
import me.cybermaxke.elementarrows.forge.v1710.item.ItemRegistry;
import me.cybermaxke.elementarrows.forge.v1710.locale.FLocaleRegistry;
import me.cybermaxke.elementarrows.forge.v1710.network.MessageInjectorCommon;
import me.cybermaxke.elementarrows.forge.v1710.potion.FPotionFactory;
import me.cybermaxke.elementarrows.forge.v1710.recipe.FRecipeFactory;
import me.cybermaxke.elementarrows.forge.v1710.util.Fields;
import me.cybermaxke.elementarrows.forge.v1710.world.FWorldManager;

public class FProxyCommon implements FProxy {
	public static FPotionFactory potions;
	public static FRecipeFactory recipes;
	public static FLocaleRegistry locales;
	public static FEntityFactory entities;
	public static FWorldManager worlds;
	public static FItemFactory items;

	private PluginBase plugin = new PluginBase();

	@Override
	public void onPreInit() {
		this.plugin.onPreInit();
	}

	@Override
	public void onInit() {
		entities = this.newEntities();
		entities.onInit();

		worlds = new FWorldManager();
		recipes = new FRecipeFactory();
		locales = new FLocaleRegistry();
		items = new FItemFactory();

		try {
			potions = new FPotionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			setFactoryInstance(Locales.class, LocaleRegistry.class, locales);
			setFactoryInstance(Recipes.class, RecipeFactory.class, recipes);
			setFactoryInstance(Entities.class, EntityFactory.class, entities);
			setFactoryInstance(Potions.class, PotionFactory.class, potions);
			setFactoryInstance(Worlds.class, WorldManager.class, worlds);
			setFactoryInstance(ItemStacks.class, ItemFactory.class, items);
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

		this.plugin.onInit();
	}

	@Override
	public void onPostInit() {
		this.plugin.onPostInit();
	}

	/**
	 * Gets a new entity factory instance.
	 * 
	 * @return the instance
	 */
	protected FEntityFactory newEntities() {
		return new FEntityFactory();
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