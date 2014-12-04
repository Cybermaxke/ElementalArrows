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

public interface ItemFactory {

	/**
	 * Finds the item type using it's identifier.
	 * 
	 * @param id the identifier
	 * @return the item type
	 */
	ItemType typeById(String id);

	/**
	 * Finds the item type using it's internal id.
	 * 
	 * @param internalId the internal id
	 * @return the item type
	 */
	@Deprecated
	ItemType typeById(int internalId);

	/**
	 * Gets a new item stack of a specific type with a quantity and data value.
	 * 
	 * @param type the material type
	 * @param quantity the quantity items
	 * @param data the data value
	 * @return the item stack
	 */
	ItemStack of(ItemType type, int quantity, int data);

	/**
	 * Gets a new item stack of a specific type with a quantity.
	 * 
	 * @param type the material type
	 * @param quantity the quantity items
	 * @return the item stack
	 */
	ItemStack of(ItemType type, int quantity);

	/**
	 * Gets a new item stack of a specific type.
	 * 
	 * @param type the material type
	 * @return the item stack
	 */
	ItemStack of(ItemType type);

}