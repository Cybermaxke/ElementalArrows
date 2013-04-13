/**
 * 
 * This software is part of the ElementalArrows
 * 
 * This plugins adds custom arrows to the game like they from the
 * ElemantalArrows mod but ported to spoutplugin and bukkit.
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
package me.cybermaxke.ElementalArrows;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;

import net.minecraft.server.v1_5_R2.*;

import org.bukkit.craftbukkit.v1_5_R2.entity.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ArrowListener implements Listener {

	@EventHandler
	public void onPlayerArrowHit(ProjectileHitEvent e) {
		if (!(e.getEntity() instanceof Arrow)) {
			return;
		}

		Arrow a = (Arrow) e.getEntity();

		if (!(a.getShooter() instanceof Player)) {
			return;
		}

		SpoutPlayer p = SpoutManager.getPlayer((Player) a.getShooter());
		EntityArrow en = ((CraftArrow) a).getHandle();

		if (!(en instanceof ArrowEntity)) {
			return;
		}

		ArrowEntity ea = (ArrowEntity) en;
		if (ea.getArrow() != null) {
			CustomArrowItem i = ea.getArrow();
			i.onHit(p, ea);
		}
	}

	@EventHandler
	public void onPlayerArrowDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Arrow)) {
			return;
		}

		if (!(e.getEntity() instanceof LivingEntity)) {
			return;
		}

		Arrow a = (Arrow) e.getDamager();
		LivingEntity ent = (LivingEntity) e.getEntity();

		if (!(a.getShooter() instanceof Player)) {
			return;
		}

		SpoutPlayer p = SpoutManager.getPlayer((Player) a.getShooter());		
		EntityArrow en = ((CraftArrow) a).getHandle();

		if (!(en instanceof ArrowEntity)) {
			return;
		}

		ArrowEntity ea = (ArrowEntity) en;

		if (ea.getArrow() != null) {
			CustomArrowItem i = ea.getArrow();
			i.onHit(p, ent, ea);
		}
	}
}