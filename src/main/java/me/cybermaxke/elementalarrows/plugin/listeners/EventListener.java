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

import me.cybermaxke.elementalarrows.api.ElementalArrows;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.inventory.ElementalItemStack;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.api.material.SpawnEggMaterial;
import me.cybermaxke.elementalarrows.plugin.ElementalArrowsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomItem;

public class EventListener implements Listener {

	public EventListener(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		ElementalArrowsPlugin.getInstance().getPlayer(e.getPlayer());
	}

	@EventHandler
	public void onPlayerArrowHit(ProjectileHitEvent e) {
		if (!(e.getEntity() instanceof ElementalArrow)) {
			return;
		}

		ElementalArrow a = (ElementalArrow) e.getEntity();
		if (a.getShooter() != null && !(a.getShooter() instanceof LivingEntity)) {
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
		if (a.getShooter() != null && !(a.getShooter() instanceof LivingEntity)) {
			return;
		}

		LivingEntity shooter = a.getShooter();
		ArrowMaterial m = a.getMaterial();
		if (m != null) {
			m.onHit(shooter, ent, a);
		}
	}

	@EventHandler
	public void onCraftItem(CraftItemEvent e) {
		Recipe r = e.getRecipe();
		SpoutItemStack is = new SpoutItemStack(r.getResult());
		if (is.isCustomItem() && is.getMaterial() instanceof ArrowMaterial) {
			ArrowMaterial m = (ArrowMaterial) is.getMaterial();
			if (m.hasPermission() && !e.getWhoClicked().hasPermission(m.getPermission())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.hasItem() || !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		ElementalItemStack is = new ElementalItemStack(e.getItem());
		if (!is.isCustomItem() || !(is.getMaterial() instanceof SpawnEggMaterial)) {
			return;
		}

		SpawnEggMaterial m = (SpawnEggMaterial) is.getMaterial();
		Class<? extends Entity> entity = m.getEntity();

		if (entity == null) {
			return;
		}

		Entity ent = ElementalArrows.getAPI().spawn(entity, e.getClickedBlock().getRelative(e.getBlockFace()).getLocation(), SpawnReason.SPAWNER_EGG);
		if (ent instanceof LivingEntity) {
			String n = ChatColor.RESET + ((CustomItem) m).getName();
			if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
				String n2 = is.getItemMeta().getDisplayName();
				if (!n.equals(n2)) {
					((LivingEntity) ent).setCustomName(n2);
				}
			}
		}
	}
}