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
package me.cybermaxke.elementarrows.common.potion;

public class Potions {
	private static PotionFactory factory;

	/**
	 * Finds a potion type by using its id.
	 * 
	 * @param id the id
	 * @return the potion type
	 */
	public static PotionType typeById(String id) {
		return factory.typeById(id);
	}

	/**
	 * Finds a potion type by using its internal id.
	 * 
	 * @param internalId the internal id
	 * @return the potion type
	 */
	public static PotionType typeById(int internalId) {
		return factory.typeById(internalId);
	}

	/**
	 * Gets a new potion effect with the properties.
	 * 
	 * @param type the potion type
	 * @param duration the duration of the effect
	 * @param amplifier the amplifier of the effect
	 * @return the new potion effect
	 */
	public static PotionEffect of(PotionType type, int duration, int amplifier) {
		return factory.of(type, duration, amplifier);
	}

	/**
	 * Gets a new potion effect with the properties.
	 * 
	 * @param type the potion type
	 * @param duration the duration of the effect
	 * @param amplifier the amplifier of the effect
	 * @param ambient whether the potion ambient is
	 * @return the new potion effect
	 */
	public static PotionEffect of(PotionType type, int duration, int amplifier, boolean ambient) {
		return factory.of(type, duration, amplifier, ambient);
	}

}