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
package me.cybermaxke.elementarrows.common.arrow;

import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitGround;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityTick;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.entity.Entities;
import me.cybermaxke.elementarrows.common.entity.Entity;
import me.cybermaxke.elementarrows.common.entity.EntityArrow;
import me.cybermaxke.elementarrows.common.entity.EntityArrow.PickupMode;
import me.cybermaxke.elementarrows.common.entity.EntityLiving;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.source.Source;
import me.cybermaxke.elementarrows.common.source.SourceBlock;
import me.cybermaxke.elementarrows.common.source.SourceEntity;

public class ElementArrowBase implements ElementArrow {
	protected String icon;
	protected String texture;
	protected String unlocalizedName;

	protected boolean effect;

	@Override
	public String getIcon() {
		return this.icon;
	}

	@Override
	public String getTexture() {
		return this.texture;
	}

	@Override
	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	@Override
	public boolean hasEffect() {
		return this.effect;
	}

	@Override
	public void handle(EventEntityBuild event) {
		Source source = event.getSource();
		EntityArrow arrow = null;

		if (source instanceof SourceEntity) {
			Entity source0 = ((SourceEntity) source).getEntity();

			arrow = Entities.create(EntityArrow.class, source0.getWorld());
			arrow.setPosition(source0.getPosition());

			if (source0 instanceof EntityLiving) {
				arrow.setHeading((EntityLiving) source0, event.getPower() * 2.25f, 1f);
			}

			if (source0.getWorld().isRemote()) {
				System.out.print("\nDEBUG1");
			} else {
				System.out.print("\nDEBUG2"); // WHY IS THIS NOT CALLED? :(
			}
		} else if (source instanceof SourceBlock) {
			SourceBlock source0 = (SourceBlock) source;

			Vector position = source0.getPosition();
			Vector direction = source0.getFace().getDirection().add(0d, 0.1d, 0d);

			arrow = Entities.create(EntityArrow.class, source0.getWorld(), position);
			arrow.setHeading(direction, event.getPower() * 3f, 6f);
			arrow.setPickupMode(PickupMode.Creative);
		}

		event.setEntity(arrow);
	}

	@Override
	public void handle(EventInitialize event) {

	}

	@Override
	public void handle(EventEntityTick event) {

	}

	@Override
	public void handle(EventEntityShot event) {

	}

	@Override
	public void handle(EventEntityHitGround event) {

	}

	@Override
	public void handle(EventEntityHitEntity event) {

	}

}