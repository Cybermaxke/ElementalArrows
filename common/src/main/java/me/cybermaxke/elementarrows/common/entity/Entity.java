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

import java.util.UUID;

import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.property.Property;
import me.cybermaxke.elementarrows.common.property.PropertyMap;
import me.cybermaxke.elementarrows.common.world.World;

public interface Entity {
	public static final Property<Boolean> Frozen = Property.of("frozen", false);
	public static final Property<Boolean> FrozenMovement = Property.of("frozenMovement", false);

	/**
	 * Gets the unique id of the entity.
	 * 
	 * @return the unique id
	 */
	UUID getUniqueId();

	/**
	 * Gets how many ticks the entity has lived.
	 * 
	 * @return the ticks
	 */
	int getTicksLived();

	/**
	 * Gets the world of the entity.
	 * 
	 * @return the world
	 */
	World getWorld();

	/**
	 * Gets whether the entity on the ground is.
	 * 
	 * @return on the ground
	 */
	boolean isOnGround();

	/**
	 * Gets the motion of the entity.
	 * 
	 * @return the motion
	 */
	Vector getMotion();

	/**
	 * Sets the motion of the entity.
	 * 
	 * @param motion the motion
	 */
	void setMotion(Vector motion);

	/**
	 * Gets the position of the entity.
	 * 
	 * @return the position
	 */
	Vector getPosition();

	/**
	 * Sets the position of the entity.
	 * 
	 * @param position the position
	 */
	void setPosition(Vector position);

	/**
	 * Teleports the entity to a different world and position.
	 * 
	 * @param world the new world
	 * @param position the position
	 */
	void teleportTo(World world, Vector position);

	/**
	 * Gets whether the entity on fire is.
	 * 
	 * @return is on fire
	 */
	boolean isOnFire();

	/**
	 * Sets the fire ticks of the entity.
	 * 
	 * @param ticks the fire ticks
	 */
	void setFireTicks(int ticks);

	/**
	 * Gets the yaw rotation. (Around the y axis.)
	 * 
	 * @return the yaw
	 */
	float getYaw();

	/**
	 * Gets the pitch rotation. (Around the x axis.)
	 * 
	 * @return the pitch
	 */
	float getPitch();

	/**
	 * Sets the rotation of the entity.
	 * 
	 * @param yaw the yaw rotation
	 * @param pitch the pitch rotation
	 */
	void setRotation(float yaw, float pitch);

	/**
	 * Gets the property map of the entity.
	 * 
	 * @return the property map
	 */
	PropertyMap getPropertyMap();

}