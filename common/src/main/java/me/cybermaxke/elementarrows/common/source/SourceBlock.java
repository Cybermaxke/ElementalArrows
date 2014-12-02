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

import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.world.World;

public class SourceBlock implements Source {
	private final Vector position;
	private final World world;
	private final Face face;

	/**
	 * Creates a new block source.
	 * 
	 * @param world the world the block is located in
	 * @param position the block position
	 * @param face the block face
	 */
	public SourceBlock(World world, Vector position, Face face) {
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
	public Face getFace() {
		return this.face;
	}

	public enum Face {
		North	( 0,  0, -1),
		South	( 0,  0,  1),
		East	(-1,  0,  0),
		West	( 1,  0,  0),
		Down	( 0, -1,  0),
		Up		( 0,  1,  0);

		/**
		 * Gets the direction of the face. (The direction vector a block would dispense.)
		 */
		private Vector direction;
		private Face opposite;

		Face(double x, double y, double z) {
			this.direction = Vectors.of(x, y, z);
		}

		/**
		 * Gets the opposite face.
		 * 
		 * @return the face
		 */
		public Face getOpposite() {
			return this.opposite;
		}

		/**
		 * Gets the direction vector of this face.
		 * 
		 * @return the direction
		 */
		public Vector getDirection() {
			return this.direction;
		}

		static void setOpposite(Face face0, Face face1) {
			face0.opposite = face1;
			face1.opposite = face0;
		}

		static {
			Face.setOpposite(Face.North, Face.South);
			Face.setOpposite(Face.East, Face.West);
			Face.setOpposite(Face.Down, Face.Up);
		}

	}
}