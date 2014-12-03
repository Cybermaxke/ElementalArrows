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

import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.source.Source;

public interface MovingObjectPosition {

	/**
	 * Gets the source that was hit.
	 * 
	 * @return the source
	 */
	Source getHit();

	/**
	 * Gets the hit point position.
	 * 
	 * @return the point
	 */
	Vector getPoint();

	/**
	 * Gets the type of the moving object position.
	 * 
	 * @return the type
	 */
	Type getType();

	/**
	 * The result types.
	 */
	enum Type {
		/**
		 * If a entity is hit.
		 */
		Entity,
		/**
		 * If a block is hit.
		 */
		Block,
		/**
		 * If nothing is hit.
		 */
		Miss,
	}

}