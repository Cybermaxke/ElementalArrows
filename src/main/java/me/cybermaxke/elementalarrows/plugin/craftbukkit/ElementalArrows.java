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
package me.cybermaxke.elementalarrows.plugin.craftbukkit;

import net.minecraft.server.v1_5_R2.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import me.cybermaxke.elementalarrows.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.api.entity.ElementalSkeleton;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.dispenser.nms.DispenseBehaviorManager;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.CraftElementalPlayer;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms.EntityElementalArrow;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms.EntityElementalSkeleton;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms.EntityManager;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.item.nms.ItemManager;

public class ElementalArrows implements ElementalArrowsAPI {

	public ElementalArrows() {
		new ItemManager();
		new EntityManager();
		new DispenseBehaviorManager();
	}

	@Override
	public ElementalPlayer getPlayer(Player player) {
		return new CraftElementalPlayer(player);
	}

	@Override
	public boolean isRegionProtected(Location location) {
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
			WorldGuardPlugin worldguard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
 			return !worldguard.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.TNT);
		}
		return false;
	}

	@Override
	public ElementalArrow shootElementalArrow(Location location, Vector vector, float speed, float spread) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		EntityElementalArrow a = new EntityElementalArrow(w);
		a.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
		a.shoot(vector.getX(), vector.getY(), vector.getZ(), speed, spread);
		a.speed = speed;
		w.addEntity(a);
		return a.getBukkitEntity();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T spawn(Class<T> entity, Location location) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		if (ElementalSkeleton.class.isAssignableFrom(entity)) {
			EntityElementalSkeleton ent = new EntityElementalSkeleton(w);
			ent.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
			return (T) ent.getBukkitEntity();
		}
		return location.getWorld().spawn(location, entity);
	}
}