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
package me.cybermaxke.elementarrows.common.source;

import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.world.World;

public class SourceBlock implements Source {
	private final Vector position;
	private final World world;
	private final BlockFace face;

	/**
	 * Creates a new block source.
	 * 
	 * @param world the world the block is located in
	 * @param position the block position
	 * @param face the block face
	 */
	public SourceBlock(World world, Vector position, BlockFace face) {
		this.position = position;
		this.world = world;
		this.face = face;
	}

	/**
	 * Gets the block position of this source.
	 * 
	 * @return the block position
	 */
	public Vector getPosition() {
		return this.position;
	}

	/**
	 * Gets the world of this source.
	 * 
	 * @return the world
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * Gets the face of this source.
	 * 
	 * @return the face
	 */
	public BlockFace getFace() {
		return this.face;
	}

}