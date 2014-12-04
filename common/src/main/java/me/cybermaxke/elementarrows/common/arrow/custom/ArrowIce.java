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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.cybermaxke.elementarrows.common.arrow.ElementArrowBase;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.entity.Entities;
import me.cybermaxke.elementarrows.common.entity.Entity;
import me.cybermaxke.elementarrows.common.entity.EntityLiving;
import me.cybermaxke.elementarrows.common.entity.EntityTickHandler;
import me.cybermaxke.elementarrows.common.json.JsonField;
import me.cybermaxke.elementarrows.common.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.potion.Potions;

public class ArrowIce extends ElementArrowBase {
	/**
	 * The times of how long the entities will be frozen.
	 */
	private Map<UUID, Integer> frozen = new HashMap<UUID, Integer>();

	/**
	 * The chance between a frozen entity or just one slown down.
	 */
	@JsonField("frozenChance")
	private float frozenChance = 0.25f;

	@JsonField("frozenPotionEffect")
	private PotionEffect frozenPotionEffect = Potions.of(PotionType.Slowness, 100, 6);

	@JsonField("slownPotionEffect")
	private PotionEffect slownPotionEffect = Potions.of(PotionType.Slowness, 80, 0);

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsIce";

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowIce.png";
		this.texture = "elementArrows:arrowEntityIce.png";

		Entities.addTickHandler(new EntityTickHandler() {

			@Override
			public void handle(Entity entity) {
				UUID uuid = entity.getUniqueId();

				if (entity.getTicksLived() % 3 == 0 && frozen.containsKey(uuid)) {
					int ticks = frozen.get(uuid);

					if (entity.getTicksLived() - ticks >= frozenPotionEffect.getDuration()) {
						frozen.remove(uuid);

						entity.getPropertyMap().set(Entity.Frozen, false);
						entity.getPropertyMap().set(Entity.FrozenMovement, false);
					}
				}
			}

		});
	}

	@Override
	public void handle(EventEntityHitEntity event) {
		Entity damaged = event.getDamageSource().getDamaged();

		if (!(damaged instanceof EntityLiving)) {
			return;
		}

		if (damaged.getWorld().getRandom().nextFloat() < this.frozenChance) {

			/**
			 * Put the entity in the map.
			 */
			this.frozen.put(damaged.getUniqueId(), damaged.getTicksLived());

			damaged.getPropertyMap().set(Entity.Frozen, true);
			damaged.getPropertyMap().set(Entity.FrozenMovement, true);

			/**
			 * Apply the normal slow effect.
			 */
			((EntityLiving) damaged).addPotionEffect(this.frozenPotionEffect.clone());
		} else {
			((EntityLiving) damaged).addPotionEffect(this.slownPotionEffect.clone());
		}
	}

}