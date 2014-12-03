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
package me.cybermaxke.elementarrows.common.enchant;

public class Enchants {
	private static EnchantFactory factory;

	/**
	 * Finds a enchantment by using its id.
	 * 
	 * @param id the id
	 * @return the enchantment
	 */
	public static Enchant typeById(String id) {
		return factory.typeById(id);
	}

	/**
	 * Finds a enchantment by using its internal id.
	 * 
	 * @param internalId the internal id
	 * @return the enchantment
	 */
	@Deprecated
	public static Enchant typeById(int internalId) {
		return factory.typeById(internalId);
	}

}