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

import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.world.World;

public interface EntityFactory {

	/**
	 * Creates a new entity at the position in the world.
	 * 
	 * @param entity the entity
	 * @param world the world
	 * @param position the position
	 * @return the new entity
	 */
	<T extends Entity> T create(Class<T> entity, World world, Vector position);

	/**
	 * Creates a new entity in the world.
	 * 
	 * @param entity the entity
	 * @param world the world
	 * @return the new entity
	 */
	<T extends Entity> T create(Class<T> entity, World world);

}