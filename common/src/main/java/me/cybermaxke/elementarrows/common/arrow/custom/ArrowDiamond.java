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
import me.cybermaxke.elementarrows.common.entity.EntityArrow;
import me.cybermaxke.elementarrows.common.json.JsonField;

public class ArrowDiamond extends ElementArrowBase {

	@JsonField("powerMultiplier")
	private float powerMultiplier = 1.45f;

	@JsonField("damageMultiplier")
	private float damageMultiplier = 1.45f;

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsDiamond";

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowDiamond.png";
		this.texture = "elementArrows:arrowEntityDiamond.png";
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
		EntityArrow arrow = event.getEntity();

		arrow.setDamage(arrow.getDamage() * this.damageMultiplier);
		arrow.setCritical(false);
	}

}