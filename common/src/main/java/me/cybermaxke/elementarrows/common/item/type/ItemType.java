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
package me.cybermaxke.elementarrows.common.item.type;

public interface ItemType {

	public static final ItemType Arrow = null;
	public static final ItemType Bow = null;

	/**
	 * Gets the id of the item type.
	 * 
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the internal id of the item type.
	 * 
	 * @return the internal id
	 */
	@Deprecated
	int getInternalId();

	/**
	 * Gets whether the item type durability has.
	 * 
	 * @return has durability
	 */
	boolean hasDurability();

	/**
	 * Gets the maximum durability of the item type.
	 * 
	 * @return the durability
	 */
	int getMaxDurability();

	/**
	 * Gets the maximum size of a item stack.
	 * 
	 * @return the maximum size
	 */
	int getMaxStackSize();

}