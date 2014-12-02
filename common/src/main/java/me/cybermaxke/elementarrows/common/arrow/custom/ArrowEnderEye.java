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
import me.cybermaxke.elementarrows.common.source.Source;
import me.cybermaxke.elementarrows.common.source.SourceEntity;

public class ArrowEnderEye extends ElementArrowBase {

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsEnderEye";

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowEnderEye.png";
		this.texture = "elementArrows:arrowEntityEnderEye.png";
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
		Source source = arrow.getSource();

		if (source instanceof SourceEntity) {
			((SourceEntity) source).getEntity().teleportTo(arrow.getWorld(), arrow.getPosition());
		}
	}

}