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
package me.cybermaxke.elementarrows.common.inventory;

public interface Inventory {

	/**
	 * Tries to add a item to the inventory. Returns the rest if it isn't added.
	 * 
	 * @param itemStack the item stack
	 * @return the rest of the item stack
	 */
	ItemStack add(ItemStack itemStack);

	/**
	 * Gets the size of the inventory.
	 * 
	 * @return the size
	 */
	int getSize();

	/**
	 * Gets the item stack at the index.
	 * 
	 * @param index the index
	 * @return the item stack
	 */
	ItemStack getAt(int index);

	/**
	 * Sets the item stack at the index.
	 * 
	 * @param index the index
	 * @param itemStack the item stack
	 */
	void setAt(int index, ItemStack itemStack);

	/**
	 * Gets all the item stacks.
	 * 
	 * @return the item stacks
	 */
	ItemStack[] getContents();

	/**
	 * Clears all the content of the inventory.
	 */
	void clear();

}