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
package me.cybermaxke.elementarrows.spigot.v1710.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_7_R4.MobEffect;
import net.minecraft.server.v1_7_R4.MobEffectList;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.potion.PotionFactory;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.spigot.v1710.util.Fields;

public class FPotionFactory implements PotionFactory {
	private Map<String, FPotionType> potions0 = new HashMap<String, FPotionType>();
	private Map<Integer, FPotionType> potions1 = new HashMap<Integer, FPotionType>();

	public FPotionFactory() throws Exception {
		MobEffectList[] types = MobEffectList.byId;

		/**
		 * The current field.
		 */
		int fieldIndex = 0;

		/**
		 * Start id of the fields in {@link PotionType}.
		 */
		for (int i = 1; i < types.length; i++) {
			MobEffectList type = types[i];

			if (type != null) {
				String id = getIdFor(type);
				FPotionType type0 = new FPotionType(id, type);

				Field field = Fields.findField(PotionType.class, PotionType.class, fieldIndex++);

				if (field != null) {
					Field field0 = Field.class.getDeclaredField("modifiers");
					field0.setAccessible(true);
					field0.set(field, field.getModifiers() & ~Modifier.FINAL);

					field.setAccessible(true);
					field.set(null, type0);
				}

				this.potions0.put(id.toLowerCase(), type0);
				this.potions1.put(type.id, type0);
			}
		}
	}

	@Override
	public PotionType typeById(String id) {
		Preconditions.checkNotNull(id);

		int index = id.indexOf(':');
		if (index != -1) {
			id = id.substring(index + 1, id.length());
		}
		id = id.toLowerCase();

		FPotionType type = this.potions0.get(id);

		if (type != null) {
			return type;
		}

		MobEffectList[] types = MobEffectList.byId;

		/**
		 * Start id of the fields in {@link PotionType}.
		 */
		for (int i = 0; i < types.length; i++) {
			MobEffectList type0 = types[i];

			if (type0 != null) {
				String id0 = getIdFor(type0);

				if (id0.equals(id)) {
					FPotionType type1 = new FPotionType(id0, type0);

					this.potions0.put(id0, type1);
					this.potions1.put(i, type1);

					return type1;
				}
			}
		}

		return null;
	}

	@Override
	public FPotionEffect of(PotionType type, int duration, int amplifier) {
		return new FPotionEffect(new MobEffect(type.getInternalId(), duration, amplifier));
	}

	@Override
	public FPotionEffect of(PotionType type, int duration, int amplifier, boolean ambient) {
		return new FPotionEffect(new MobEffect(type.getInternalId(), duration, amplifier, ambient));
	}

	@Override
	public PotionType typeById(int id) {
		FPotionType type = this.potions1.get(id);

		if (type != null) {
			return type;
		}

		if (id >= MobEffectList.byId.length) {
			return null;
		}

		MobEffectList potion = MobEffectList.byId[id];
		if (potion != null) {
			String id0 = getIdFor(potion);
			FPotionType type0 = new FPotionType(id0, potion);

			this.potions0.put(id0, type0);
			this.potions1.put(id, type0);

			return type0;
		}

		return null;
	}

	/**
	 * Gets the id for the minecraft potion.
	 * 
	 * @param potion the potion
	 * @return the id
	 */
	public static String getIdFor(MobEffectList potion) {
		return potion.a().replaceFirst("potion.", "");
	}

}