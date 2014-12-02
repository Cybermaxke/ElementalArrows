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

import me.cybermaxke.elementarrows.common.entity.Entity;

public class DamageSource {
	private Entity damaged;
	private Source source;

	private double damage;

	/**
	 * Creates a new damage source.
	 * 
	 * @param damaged the damaged entity
	 * @param source the damage source
	 * @param damage the damage value
	 */
	public DamageSource(Entity damaged, Source source, double damage) {
		this.damaged = damaged;
		this.source = source;
		this.damage = damage;
	}

	/**
	 * Gets the entity that was damaged by this source.
	 * 
	 * @return the damaged entity
	 */
	public Entity getDamaged() {
		return this.damaged;
	}

	/**
	 * Gets the source of the damage.
	 * 
	 * @return the source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * Gets the amount of damage caused by the source.
	 * 
	 * @return the damage
	 */
	public double getDamage() {
		return this.damage;
	}

}