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
package me.cybermaxke.elementarrows.common.entity;

import me.cybermaxke.elementarrows.common.inventory.Inventory;

public interface EntityPlayer extends EntityLiving {

	/**
	 * Gets the main inventory of the player.
	 * 
	 * @return the inventory
	 */
	Inventory getInventory();

	/**
	 * Gets whether the player the client mod installed has.
	 * 
	 * @return has client
	 */
	boolean hasClient();

}