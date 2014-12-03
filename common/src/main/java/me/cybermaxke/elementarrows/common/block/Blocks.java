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
package me.cybermaxke.elementarrows.common.block;

public class Blocks {
	private static BlockFactory factory;

	/**
	 * Finds the block type using it's identifier.
	 * 
	 * @param id the identifier
	 * @return the block type
	 */
	public static BlockType find(String id) {
		return factory.typeById(id);
	}

	/**
	 * Finds the block type using it's internal id.
	 * 
	 * @param internalId the internal id
	 * @return the block type
	 */
	@Deprecated
	public static BlockType find(int internalId) {
		return factory.typeById(internalId);
	}

}