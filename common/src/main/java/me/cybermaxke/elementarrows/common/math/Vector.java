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
package me.cybermaxke.elementarrows.common.math;

public interface Vector {

	/**
	 * Gets the x coordinate of the block.
	 * 
	 * @return the x coordinate
	 */
	int getBlockX();

	/**
	 * Gets the x coordinate.
	 * 
	 * @return the x coordinate
	 */
	double getX();

	/**
	 * Gets the x coordinate.
	 * 
	 * @param x the x coordinate
	 */
	Vector setX(double x);

	/**
	 * Gets the y coordinate of the block.
	 * 
	 * @return the y coordinate
	 */
	int getBlockY();

	/**
	 * Gets the y coordinate.
	 * 
	 * @return the y coordinate
	 */
	double getY();

	/**
	 * Gets the y coordinate.
	 * 
	 * @param y the y coordinate
	 */
	Vector setY(double y);

	/**
	 * Gets the z coordinate of the block.
	 * 
	 * @return the z coordinate
	 */
	int getBlockZ();

	/**
	 * Gets the z coordinate.
	 * 
	 * @return the z coordinate
	 */
	double getZ();

	/**
	 * Gets the z coordinate.
	 * 
	 * @param z the z coordinate
	 */
	Vector setZ(double z);

	/**
	 * Adds the values to the coordinates.
	 * 
	 * @param x the x value
	 * @param y the y value
	 * @param z the z value
	 * @return the new vector
	 */
	Vector add(double x, double y, double z);

}