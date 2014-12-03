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
package me.cybermaxke.elementarrows.common.item.inventory;

import me.cybermaxke.elementarrows.common.enchant.Enchant;
import me.cybermaxke.elementarrows.common.item.type.ItemType;

public interface ItemStack {

	/**
	 * Gets the type of the item stack.
	 * 
	 * @return the type
	 */
	ItemType getType();

	/**
	 * Gets the quantity of items in this item stack.
	 * 
	 * @return the quantity
	 */
	int getQuantity();

	/**
	 * Adds the quantity of items in this item stack.
	 * 
	 * @param quantity the quantity
	 */
	int addQuantity(int quantity);

	/**
	 * Sets the quantity of items in this item stack.
	 * 
	 * @param quantity the quantity
	 */
	void setQuantity(int quantity);

	/**
	 * Gets the data value of this item stack.
	 * 
	 * @return the data value
	 */
	int getData();

	/**
	 * Sets the data value of this item stack.
	 * 
	 * @param data the data value
	 */
	void setData(int data);

	/**
	 * Gets whether the item stack the enchantment has.
	 * 
	 * @param enchantment the enchantment
	 * @return has enchantment
	 */
	boolean hasEnchant(Enchant enchantment);

	/**
	 * Gets the level of the enchantment. Where level 0 means
	 * that the enchantment doesn't exist.
	 * 
	 * @param enchantment the enchantment
	 * @return the level
	 */
	int getEnchantLevel(Enchant enchantment);

	/**
	 * Adds a enchantment with a specific level. This method
	 * overrides the current enchantment with the same type.
	 * 
	 * @param enchantment the enchantment
	 * @param level the level
	 */
	void addEnchant(Enchant enchantment, int level);

	/**
	 * Removes the enchantment from the item.
	 * 
	 * @param enchantment the enchantment
	 */
	void removeEnchant(Enchant enchantment);

}