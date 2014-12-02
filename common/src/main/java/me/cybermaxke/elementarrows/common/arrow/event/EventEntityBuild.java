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
import me.cybermaxke.elementarrows.common.source.Source;

public class EventEntityBuild implements Event {
	private EntityArrow arrow;
	private Source source;

	private float charge;
	private float power;

	public EventEntityBuild(Source source, float charge, float power) {
		this.source = source;
		this.charge = charge;
		this.power = power;
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
	 * Gets how hard the bow is charged.
	 * 
	 * @return the charge
	 */
	public float getCharge() {
		return this.charge;
	}

	/**
	 * Sets how hard the bow is charged.
	 * 
	 * @param charge the charge
	 */
	public void setCharge(float charge) {
		this.charge = charge;
	}

	/**
	 * Gets the power that is used to shoot the arrow.
	 * 
	 * @return the power
	 */
	public float getPower() {
		return this.power;
	}

	/**
	 * Sets the power that is used to shoot the arrow.
	 * 
	 * @param power the power
	 */
	public void setPower(float power) {
		this.power = power;
	}

	/**
	 * Gets the arrow entity that is getting build. Initially null.
	 * 
	 * @return the arrow entity
	 */
	public EntityArrow getEntity() {
		return this.arrow;
	}

	/**
	 * Sets the arrow entity that is getting build. Initially null.
	 * 
	 * @param arrow the arrow entity
	 */
	public void setEntity(EntityArrow arrow) {
		this.arrow = arrow;
	}

}