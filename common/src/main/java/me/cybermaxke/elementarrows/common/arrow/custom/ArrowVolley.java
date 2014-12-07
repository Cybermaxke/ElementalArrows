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

import java.util.Random;

import me.cybermaxke.elementarrows.common.arrow.ElementArrowBase;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitGround;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.entity.Entities;
import me.cybermaxke.elementarrows.common.entity.EntityArrow;
import me.cybermaxke.elementarrows.common.json.JsonField;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.world.MovingObjectPosition;
import me.cybermaxke.elementarrows.common.world.World;

public class ArrowVolley extends ElementArrowBase {

	@JsonField("amount")
	private int amount = 7;

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsVolley";
		this.effect = true;

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowVolley.png";
		this.texture = "elementArrows:arrowEntityVolley.png";
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
		Vector vec = arrow.getPosition();
		World world = arrow.getWorld();
		Random random = world.getRandom();

		double x = vec.getX();
		double y = vec.getY();
		double z = vec.getZ();

		for (int i = 0; i < this.amount; i++) {
			double x0 = x + (random.nextFloat() - 0.5f) * 4f;
			double z0 = z + (random.nextFloat() - 0.5f) * 4f;
			double y0 = y + 6f;

			double x1 = x + (random.nextFloat() - 0.5f) * 2.5f;
			double z1 = z + (random.nextFloat() - 0.5f) * 2.5f;
			double y1 = y;

			Vector vec0 = Vectors.of(x0, y0, z0);
			MovingObjectPosition mop = world.rayTrace(vec, vec0, false, false);

			if (mop != null && !mop.getType().equals(MovingObjectPosition.Type.Entity)) {
				Vector vec1 = mop.getPoint();

				x0 = vec1.getX();
				y0 = vec1.getY();
				z0 = vec1.getZ();
			}

			double mx = x1 - x0;
			double my = y1 - y0;
			double mz = z1 - z0;

			EntityArrow arrow0 = Entities.create(EntityArrow.class, world);
			arrow0.setHeading(Vectors.of(mx, my, mz), 1.3f, 1f);
			arrow0.setSource(arrow.getSource());

			world.addEntity(arrow0);
		}

		arrow.setElementData(0);
	}

}