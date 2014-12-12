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
package me.cybermaxke.elementarrows.spigot.v1800.enchant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.v1_8_R1.Enchantment;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.enchant.Enchant;
import me.cybermaxke.elementarrows.common.enchant.EnchantFactory;
import me.cybermaxke.elementarrows.common.util.lookup.IntToString;
import me.cybermaxke.elementarrows.common.util.lookup.IntToStringMod;
import me.cybermaxke.elementarrows.common.util.reflect.Fields;

public class FEnchantFactory implements EnchantFactory {
	private final Map<Integer, FEnchant> items = new HashMap<Integer, FEnchant>();
	private final IntToString map = new IntToStringMod();

	public FEnchantFactory() throws Exception {
		this.map.load(FEnchantFactory.class.getResourceAsStream("/assets/elementarrows/data/identifiersEnchant.dat"));

		/**
		 * Get all the fields of types that are available.
		 */
		Field[] fields = Fields.findFields(Enchant.class, Enchant.class);

		Field field0 = Field.class.getDeclaredField("modifiers");
		field0.setAccessible(true);

		Iterator<Entry<Integer, String>> it = this.map.entries();
		while (it.hasNext()) {
			Entry<Integer, String> entry = it.next();

			String name = entry.getValue();
			Enchantment enchant = Enchantment.getById(entry.getKey());

			if (enchant != null) {
				FEnchant type = new FEnchant(name, enchant);
				String name0;

				int index = name.indexOf(':');
				if (index == -1) {
					name0 = name;
				} else {
					name0 = name.substring(index + 1, name.length());
				}

				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];

					if (field.getName().equalsIgnoreCase(name0)) {
						field0.set(field, field.getModifiers() & ~Modifier.FINAL);

						field.setAccessible(true);
						field.set(null, type);
						break;
					}
				}

				this.items.put(enchant.id, type);
			}
		}
	}

	@Override
	public FEnchant typeById(String id) {
		Preconditions.checkNotNull(id);

		/**
		 * Get the internal id.
		 */
		int id0 = this.map.get(id);

		FEnchant type = this.items.get(this.map.get(id));
		if (type != null) {
			return type;
		}

		Enchantment type0 = Enchantment.getById(id0);
		if (type0 != null) {
			type = new FEnchant(this.map.get(id0), type0);

			/**
			 * Cache the type.
			 */
			this.items.put(id0, type);
		}

		return type;
	}

	@Override
	public FEnchant typeById(int id) {
		FEnchant type = this.items.get(id);

		if (type != null) {
			return type;
		}

		Enchantment enchant = Enchantment.getById(id);
		if (enchant != null) {
			String id0 = this.map.get(id);

			if (id0 == null) {
				return null;
			}

			type = new FEnchant(id0, enchant);
			this.items.put(id, type);
		}

		return type;
	}

}