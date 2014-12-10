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
package me.cybermaxke.elementarrows.forge.v1710.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionFactory;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.util.lookup.IntToString;
import me.cybermaxke.elementarrows.common.util.lookup.IntToStringMod;
import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import me.cybermaxke.elementarrows.common.util.resource.Resources;

public class FPotionFactory implements PotionFactory {
	private final Map<Integer, FPotionType> potions = new HashMap<Integer, FPotionType>();
	private final IntToString map = new IntToStringMod();

	public FPotionFactory() throws Exception {
		this.map.load(Resources.find("assets/elementarrows/data/identifiersPotion.dat"));

		/**
		 * Get all the fields of types that are available.
		 */
		Field[] fields = Fields.findFields(PotionType.class, PotionType.class);

		Field field0 = Field.class.getDeclaredField("modifiers");
		field0.setAccessible(true);

		Potion[] types = Potion.potionTypes;
		Iterator<Entry<Integer, String>> it = this.map.entries();

		while (it.hasNext()) {
			Entry<Integer, String> entry = it.next();

			String name = entry.getValue();
			Potion effect = types[entry.getKey()];

			if (effect != null) {
				FPotionType type = new FPotionType(name, effect);

				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];

					if (field.getName().equalsIgnoreCase(name)) {
						field0.set(field, field.getModifiers() & ~Modifier.FINAL);

						field.setAccessible(true);
						field.set(null, type);
						break;
					}
				}

				this.potions.put(effect.id, type);
			}
		}
	}

	@Override
	public FPotionType typeById(String id) {
		Preconditions.checkNotNull(id);

		/**
		 * Get the internal id.
		 */
		int id0 = this.map.get(id);

		FPotionType type = this.potions.get(this.map.get(id));
		if (type != null) {
			return type;
		}

		if (id0 < 0 || id0 >= Potion.potionTypes.length) {
			return null;
		}

		Potion type0 = Potion.potionTypes[id0];
		if (type0 != null) {
			type = new FPotionType(this.map.get(id0), type0);

			/**
			 * Cache the type.
			 */
			this.potions.put(id0, type);
		}

		return type;
	}

	@Override
	public FPotionEffect of(PotionType type, int duration, int amplifier) {
		Preconditions.checkNotNull(type);
		return new FPotionEffect(new PotionEffect(type.getInternalId(), duration, amplifier));
	}

	@Override
	public FPotionEffect of(PotionType type, int duration, int amplifier, boolean ambient) {
		Preconditions.checkNotNull(type);
		return new FPotionEffect(new PotionEffect(type.getInternalId(), duration, amplifier, ambient));
	}

	@Override
	public FPotionEffect of(PotionType type, int duration, int amplifier, boolean ambient, boolean particles) {
		Preconditions.checkNotNull(type);
		return new FPotionEffect(new PotionEffect(type.getInternalId(), duration, amplifier, ambient));
	}

	@Override
	public FPotionType typeById(int id) {
		FPotionType type = this.potions.get(id);

		if (type != null) {
			return type;
		}

		if (id >= Potion.potionTypes.length) {
			return null;
		}

		Potion potion = Potion.potionTypes[id];
		if (potion != null) {
			String id0 = this.map.get(id);

			if (id0 == null) {
				return null;
			}

			type = new FPotionType(id0, potion);
			this.potions.put(id, type);
		}

		return type;
	}

}