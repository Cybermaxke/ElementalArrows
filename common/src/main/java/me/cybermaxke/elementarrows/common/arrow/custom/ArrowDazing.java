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
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.entity.Entity;
import me.cybermaxke.elementarrows.common.entity.EntityLiving;
import me.cybermaxke.elementarrows.common.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.potion.Potions;

public class ArrowDazing extends ElementArrowBase {

	private PotionEffect confusionEffect = Potions.of(PotionType.Nausea, 75, 12);

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsDazing";

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowDazing.png";
		this.texture = "elementArrows:arrowEntityDazing.png";
	}

	@Override
	public void handle(EventEntityHitEntity event) {
		Entity entity = event.getDamageSource().getDamaged();

		if (entity instanceof EntityLiving && this.confusionEffect != null) {
			((EntityLiving) entity).addPotionEffect(this.confusionEffect.clone());
		}
	}

}