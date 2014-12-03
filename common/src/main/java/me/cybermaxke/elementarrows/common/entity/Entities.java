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

public class Entities {
	private static EntityFactory factory;

	/**
	 * Creates a new entity at the position in the world.
	 * 
	 * @param entity the entity
	 * @param world the world
	 * @param position the position
	 * @return the new entity
	 */
	public static <T extends Entity> T create(Class<T> entity, World world, Vector position) {
		return factory.create(entity, world, position);
	}

	/**
	 * Creates a new entity at the position in the world.
	 * 
	 * @param entity the entity
	 * @param world the world
	 * @return the new entity
	 */
	public static <T extends Entity> T create(Class<T> entity, World world) {
		return factory.create(entity, world);
	}

	/**
	 * Adds a entity tick handler to the factory.
	 * 
	 * @param handler the handler
	 */
	public static void addTickHandler(EntityTickHandler handler) {
		factory.addTickHandler(handler);
	}

	/**
	 * Removed a entity tick handler to the factory and returns whether it is removed.
	 * 
	 * @param handler the handler
	 * @return whether it is removed
	 */
	public static boolean removeTickHandler(EntityTickHandler handler) {
		return factory.removeTickHandler(handler);
	}

}