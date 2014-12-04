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
package me.cybermaxke.elementarrows.common.item;

import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.item.type.ItemType;

public class Items {
	private static ItemFactory factory;

	/**
	 * Finds the item type using it's identifier.
	 * 
	 * @param id the identifier
	 * @return the item type
	 */
	public static ItemType typeById(String id) {
		return factory.typeById(id);
	}

	/**
	 * Finds the item type using it's internal id.
	 * 
	 * @param internalId the internal id
	 * @return the item type
	 */
	@Deprecated
	public static ItemType typeById(int internalId) {
		return factory.typeById(internalId);
	}

	/**
	 * Gets a new item stack of a specific type with a quantity and data value.
	 * 
	 * @param type the material type
	 * @param quantity the quantity items
	 * @param data the data value
	 * @return the item stack
	 */
	public static ItemStack of(ItemType type, int quantity, int data) {
		return factory.of(type, quantity, data);
	}

	/**
	 * Gets a new item stack of a specific type with a quantity.
	 * 
	 * @param type the material type
	 * @param quantity the quantity items
	 * @return the item stack
	 */
	public static ItemStack of(ItemType type, int quantity) {
		return factory.of(type, quantity);
	}

	/**
	 * Gets a new item stack of a specific type.
	 * 
	 * @param type the material type
	 * @return the item stack
	 */
	public static ItemStack of(ItemType type) {
		return factory.of(type);
	}

}