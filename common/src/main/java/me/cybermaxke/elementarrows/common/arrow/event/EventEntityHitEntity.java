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
package me.cybermaxke.elementarrows.common.arrow.event;

import me.cybermaxke.elementarrows.common.entity.EntityArrow;
import me.cybermaxke.elementarrows.common.source.DamageSource;
import me.cybermaxke.elementarrows.common.source.Source;

public class EventEntityHitEntity implements Event {
	private EntityArrow arrow;
	private DamageSource damage;
	private Source source;

	public EventEntityHitEntity(EntityArrow arrow, DamageSource damage, Source source) {
		this.source = source;
		this.damage = damage;
		this.arrow = arrow;
	}

	/**
	 * Gets the source of the arrow.
	 * 
	 * @return the source
	 */
	public Source getSource() {
		return this.source;
	}

	/**
	 * Gets the damage source.
	 * 
	 * @return the source
	 */
	public DamageSource getDamageSource() {
		return this.damage;
	}

	/**
	 * Sets the damage source.
	 * 
	 * @param source the damage source
	 */
	public void setDamageSource(DamageSource source) {
		this.damage = source;
	}

	/**
	 * Gets the arrow entity that is getting spawned.
	 * 
	 * @return the arrow entity
	 */
	public EntityArrow getEntity() {
		return this.arrow;
	}

}