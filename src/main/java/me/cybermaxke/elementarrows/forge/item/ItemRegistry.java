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
package me.cybermaxke.elementarrows.forge.item;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.common.registry.GameRegistry;

import me.cybermaxke.elementarrows.forge.util.UtilFields;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;

@SuppressWarnings("unchecked")
public final class ItemRegistry {
	private final Map<String, Item> overriden = new HashMap<String, Item>();

	/**
	 * Registers a new item and overrides the old one if present.
	 * 
	 * @param id the id
	 * @param item the item
	 */
	public void register(String id, Item item) {
		if (!id.startsWith("minecraft:")) {
			GameRegistry.registerItem(item, id);
		} else {
			/**
			 * Currently is the {@link GameRegistry#addSubstitutionAlias(...)} not working. :/
			 */

			/**
			try {
				GameRegistry.addSubstitutionAlias(id, Type.ITEM, item);
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/

			/**
			 * We will also override the fields in the Items class to avoid issues.
			 */
			RegistryNamespaced registry = Item.itemRegistry;

			try {
				Item item0 = (Item) registry.getObject(id);
				Integer id0 = registry.getIDForObject(item0);

				this.overriden.put(id, item0);

				Map<Object, Object> map0 = null;
				ObjectIntIdentityMap map1 = null;

				Field field0 = UtilFields.findField(RegistrySimple.class, Map.class, 0);
				field0.setAccessible(true);

				Field field1 = UtilFields.findField(RegistryNamespaced.class, ObjectIntIdentityMap.class, 0);
				field1.setAccessible(true);

				map0 = (Map<Object, Object>) field0.get(registry);
				map1 = (ObjectIntIdentityMap) field1.get(registry);

				map0.remove(id);
				map0.put(id, item);
				map0.put("removed:" + id.replace("minecraft:", ""), item0);
				map1.func_148746_a(item, id0);

				/**
				 * Make sure that the item is gone.
				 */
				item0.setCreativeTab(null);

				for (Field field : Items.class.getFields()) {
					int modifiers = field.getModifiers();

					if (Modifier.isStatic(modifiers)) {
						try {
							if (field.get(null).equals(item0)) {
								Field field2 = Field.class.getDeclaredField("modifiers");
								field2.setAccessible(true);
								field2.set(field, modifiers & ~Modifier.FINAL);

								field.setAccessible(true);
								field.set(null, item);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				Field field2 = UtilFields.findField(StatCrafting.class, Item.class, 0);
				field2.setAccessible(true);

				List<StatCrafting> list = StatList.itemStats;
				Iterator<StatCrafting> it = list.iterator();

				while (it.hasNext()) {
					StatCrafting stat = it.next();

					if (stat.func_150959_a() == item0) {
						Field field3 = Field.class.getDeclaredField("modifiers");
						field3.setAccessible(true);
						field3.set(field2, field2.getModifiers() & ~Modifier.FINAL);

						field2.setAccessible(true);
						field2.set(stat, item);
					}
				}

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void clean() {
		for (Entry<String, Item> en : this.overriden.entrySet()) {
			this.register(en.getKey(), en.getValue());
		}
		this.overriden.clear();
	}

}