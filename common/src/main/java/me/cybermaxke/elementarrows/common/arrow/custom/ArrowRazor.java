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
package me.cybermaxke.elementarrows.common.arrow.custom;

import me.cybermaxke.elementarrows.common.arrow.ElementArrowBase;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;

public class ArrowRazor extends ElementArrowBase {
	private float powerMultiplier = 1.45f;
	private int knockbackStrength = 2;

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsRazor";

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowRazor.png";
		this.texture = "elementArrows:arrowEntityRazor.png";
	}

	@Override
	public void handle(EventEntityBuild event) {
		/**
		 * Modify the power (speed)
		 */
		event.setPower(event.getPower() * this.powerMultiplier);

		/**
		 * Let the underlying method build the arrow.
		 */
		super.handle(event);
	}

	@Override
	public void handle(EventEntityShot event) {
		event.getEntity().setKnockbackPower(this.knockbackStrength);
	}

}