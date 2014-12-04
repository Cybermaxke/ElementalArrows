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
package me.cybermaxke.elementarrows.common.world;

import java.util.Random;

import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.entity.Entity;
import me.cybermaxke.elementarrows.common.math.Vector;

public interface World {
	/**
	 * Nothing will be updated.
	 */
	public static final int None = 0x0;

	/**
	 * The block physics will be updated.
	 */
	public static final int Physics = 0x1;

	/**
	 * Notify the world for changes.
	 */
	public static final int Notify = 0x2;

	/**
	 * The physics will be updated and the world will be notified.
	 */
	public static final int All = Physics | Notify;

	/**
	 * Gets the random of the world.
	 * 
	 * @return the random
	 */
	Random getRandom();

	/**
	 * Gets the name of the world.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Gets whether the world remote is. (Client world.)
	 * 
	 * @return is remote
	 */
	boolean isRemote();

	/**
	 * Spawns a lightning strike at the position.
	 * 
	 * @param position the location of the lightning strike
	 * @param placeFire whether you want fire block to be placed
	 */
	void spawnLightning(Vector position, boolean placeFire);

	/**
	 * Creates a explosion at the position with a specific power and source.
	 * 
	 * @param position the position of the explosion
	 * @param power the power of the explosion
	 * @param placeFire whether you want to place fire
	 * @param destroyBlocks whether you want to destroy blocks
	 * @param source the source of the explosion
	 */
	void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks, Entity source);

	/**
	 * Creates a explosion at the position with a specific power.
	 * 
	 * @param position the position of the explosion
	 * @param power the power of the explosion
	 * @param placeFire whether you want to place fire
	 * @param destroyBlocks whether you want to destroy blocks
	 */
	void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks);

	/**
	 * Spawns a lightning strike at the position.
	 * 
	 * @param position the position of the lightning strike
	 */
	void spawnLightning(Vector position);

	/**
	 * Gets whether the type of the block at the coordinates air is.
	 * 
	 * @param position the position
	 * @return is air
	 */
	boolean isAir(Vector position);

	/**
	 * Gets the block type of the block at the coordinates.
	 * 
	 * @param position the position
	 * @return the block type
	 */
	BlockType getBlockType(Vector position);

	/**
	 * Gets the block data value of the block at the coordinates.
	 * 
	 * @param position the position
	 * @return the block data value
	 */
	int getBlockData(Vector position);

	/**
	 * Sets the block type and data of a block in the world.
	 * 
	 * @param position the position of the block
	 * @param type the type of the block
	 * @param data the data value
	 * @param flags the update flags
	 */
	void setBlock(Vector position, BlockType type, int data, int flags);

	/**
	 * Sets the block type and data of a block in the world.
	 * 
	 * @param position the position of the block
	 * @param type the type of the block
	 * @param data the data value
	 */
	void setBlock(Vector position, BlockType type, int data);

	/**
	 * Adds the entity to the world.
	 * 
	 * @param entity the entity
	 */
	void addEntity(Entity entity);

	/**
	 * Ray traces between the two positions and returns a moving object position as result.
	 * 
	 * @param start the start position
	 * @param end the end position
	 * @return the moving object position
	 */
	MovingObjectPosition rayTrace(Vector start, Vector end);

	/**
	 * Ray traces between the two positions and returns a moving object position as result.
	 * 
	 * @param start the start position
	 * @param end the end position
	 * @param liquid whether you want to check collision with liquid blocks
	 * @return the moving object position
	 */
	MovingObjectPosition rayTrace(Vector start, Vector end, boolean liquid);

	/**
	 * Ray traces between the two positions and returns a moving object position as result.
	 * 
	 * @param start the start position
	 * @param end the end position
	 * @param liquid whether you want to check collision with liquid blocks
	 * @param nonCollidable whether you want to check non collidable blocks
	 * @return the moving object position
	 */
	MovingObjectPosition rayTrace(Vector start, Vector end, boolean liquid, boolean nonCollidable);

}