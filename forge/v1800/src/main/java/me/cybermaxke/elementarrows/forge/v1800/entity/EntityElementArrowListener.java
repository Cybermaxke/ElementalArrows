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
package me.cybermaxke.elementarrows.forge.v1800.entity;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.source.DamageSource;
import me.cybermaxke.elementarrows.common.source.Source;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityElementArrowListener {

	/**
	 * To prevent multiple calls and loops.
	 */
	private boolean callEvent = true;

	/**
	 * Registers the events.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		net.minecraft.util.DamageSource source = event.source;

		if (!this.callEvent) {
			return;
		}

		if (source.isProjectile()) {
			Entity entity = source.getSourceOfDamage();
			if (entity instanceof EntityElementArrow) {
				EntityElementArrow entity0 = (EntityElementArrow) entity;

				short data = entity0.getElementData();
				if (data != 0) {
					ElementArrow arrow = Arrows.find(data);

					if (arrow != null) {
						Source source0 = entity0.source;
						FEntityArrow wrapper = FEntity.of(entity0);
						FEntity damaged = FEntity.of(event.entity);

						double damage = event.ammount;

						this.callEvent = false;
						arrow.handle(new EventEntityHitEntity(wrapper, new DamageSource(damaged, source0, damage), source0));
						this.callEvent = true;
					}
				}
			}
		}
	}

}