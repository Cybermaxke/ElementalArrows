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
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitGround;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.entity.EntityArrow;
import me.cybermaxke.elementarrows.common.json.JsonField;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.world.World;

public class ArrowLightning extends ElementArrowBase {

	@JsonField("placeFire")
	private boolean placeFire = true;

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsLightning";

		/**
		 * Setup client settings.
		 */
		this.model = "elementArrows:arrowLightning";
		this.texture = "elementArrows:arrowEntityLightning";
	}

	@Override
	public void handle(EventEntityHitEntity event) {
		this.handleHit(event.getEntity());
	}

	@Override
	public void handle(EventEntityHitGround event) {
		this.handleHit(event.getEntity());
	}

	public void handleHit(EntityArrow arrow) {
		Vector position = arrow.getPosition();
		World world = arrow.getWorld();

		world.spawnLightning(position, this.placeFire);
		arrow.setElementData(0);
	}

}