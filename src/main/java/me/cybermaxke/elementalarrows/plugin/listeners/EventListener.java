/**
 * 
 * This software is part of the ElementalArrows
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.plugin.listeners;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;

public class EventListener implements Listener {

	public EventListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerArrowHit(ProjectileHitEvent e) {
		if (!(e.getEntity() instanceof ElementalArrow)) {
			return;
		}

		ElementalArrow a = (ElementalArrow) e.getEntity();
		if (!(a.getShooter() instanceof LivingEntity)) {
			return;
		}

		LivingEntity shooter = a.getShooter();
		ArrowMaterial m = a.getMaterial();
		if (m != null) {
			m.onHit(shooter, a);
		}
	}

	@EventHandler
	public void onPlayerArrowDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof ElementalArrow)) {
			return;
		}

		if (!(e.getEntity() instanceof LivingEntity)) {
			return;
		}

		ElementalArrow a = (ElementalArrow) e.getDamager();
		LivingEntity ent = (LivingEntity) e.getEntity();

		if (!(a.getShooter() instanceof LivingEntity)) {
			return;
		}

		LivingEntity shooter = a.getShooter();
		ArrowMaterial m = a.getMaterial();
		if (m != null) {
			m.onHit(shooter, ent, a);
		}
	}
}