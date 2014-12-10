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
package me.cybermaxke.elementarrows.spigot.v1800.item;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import net.minecraft.server.v1_8_R1.CraftingStatistic;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.RegistryID;
import net.minecraft.server.v1_8_R1.RegistryMaterials;
import net.minecraft.server.v1_8_R1.RegistrySimple;
import net.minecraft.server.v1_8_R1.Statistic;
import net.minecraft.server.v1_8_R1.StatisticList;

@SuppressWarnings("unchecked")
public final class ItemRegistry {
	int id = 500;

	/**
	 * Registers a new item and overrides the old one if present.
	 * 
	 * @param id the id
	 * @param item the item
	 */
	public void register(String id, Item item) {
		if (!id.startsWith("minecraft:")) {
			Item.REGISTRY.a(this.id++, id, item);
		} else {
			/**
			 * We will also override the fields in the Items class to avoid issues.
			 */
			RegistryMaterials registry = Item.REGISTRY;

			try {
				Item item0 = (Item) registry.get(id);
				Integer id0 = registry.b(item0);

				Map<Object, Object> map0 = null;
				RegistryID map1 = null;

				Field field0 = Fields.findField(RegistrySimple.class, Map.class, 0);
				field0.setAccessible(true);

				Field field1 = Fields.findField(RegistryMaterials.class, RegistryID.class, 0);
				field1.setAccessible(true);

				map0 = (Map<Object, Object>) field0.get(registry);
				map1 = (RegistryID) field1.get(registry);

				map0.remove(id);
				map0.put(id, item);
				map0.put("removed:" + id.replace("minecraft:", ""), item0);
				map1.a(item, id0);

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

				Field field2 = Fields.findField(CraftingStatistic.class, Item.class, 0);
				field2.setAccessible(true);

				List<Statistic> list = Arrays.asList(StatisticList.USE_ITEM_COUNT);
				Iterator<Statistic> it = list.iterator();

				while (it.hasNext()) {
					CraftingStatistic stat = (CraftingStatistic) it.next();

					if (stat != null && field2.get(stat) == item0) {
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

}