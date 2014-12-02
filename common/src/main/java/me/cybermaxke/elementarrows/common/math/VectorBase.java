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

public class VectorBase implements Vector {
	private final double x;
	private final double y;
	private final double z;

	protected VectorBase(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public int getBlockX() {
		return (int) Math.floor(this.x);
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public Vector setX(double x) {
		return new VectorBase(x, this.y, this.z);
	}

	@Override
	public int getBlockY() {
		return (int) Math.floor(this.y);
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public Vector setY(double y) {
		return new VectorBase(this.x, y, this.z);
	}

	@Override
	public int getBlockZ() {
		return (int) Math.floor(this.z);
	}

	@Override
	public double getZ() {
		return this.z;
	}

	@Override
	public Vector setZ(double z) {
		return new VectorBase(this.x, this.y, this.z);
	}

	@Override
	public Vector add(double x, double y, double z) {
		return new VectorBase(this.x + x, this.y + y, this.z + z);
	}

}