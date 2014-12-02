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
package me.cybermaxke.elementarrows.spigot.v1710.entity;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.source.DamageSource;
import me.cybermaxke.elementarrows.common.source.Source;

import net.minecraft.server.v1_7_R4.Entity;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class EntityElementArrowListener implements Listener {

	/**
	 * To prevent multiple calls and loops.
	 */
	private boolean callEvent = true;

	/**
	 * Registers the events.
	 */
	public void onInit(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onLivingHurt(EntityDamageByEntityEvent event) {
		org.bukkit.entity.Entity damager = event.getDamager();

		if (!this.callEvent) {
			return;
		}

		if (damager instanceof Arrow) {
			Entity entity = ((CraftEntity) damager).getHandle();

			if (entity instanceof EntityElementArrow) {
				EntityElementArrow entity0 = (EntityElementArrow) entity;

				short data = entity0.getElementData();
				if (data != 0) {
					ElementArrow arrow = Arrows.find(data);

					if (arrow != null) {
						Source source0 = entity0.source;
						FEntityArrow wrapper = FEntity.of(entity0);
						FEntity damaged = FEntity.of(((CraftEntity) event.getEntity()).getHandle());

						double damage = event.getDamage();

						this.callEvent = false;
						arrow.handle(new EventEntityHitEntity(wrapper, new DamageSource(damaged, source0, damage), source0));
						this.callEvent = true;
					}
				}
			}
		}
	}

}