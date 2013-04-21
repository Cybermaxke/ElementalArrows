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
package me.cybermaxke.elementalarrows.plugin;

import java.util.logging.Level;

import me.cybermaxke.elementalarrows.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.plugin.arrow.ArrowManager;
import me.cybermaxke.elementalarrows.plugin.entity.CraftElementalPlayer;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalArrow;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityManager;
import me.cybermaxke.elementalarrows.plugin.item.nms.ItemManager;
import me.cybermaxke.elementalarrows.plugin.listeners.EventListener;
import me.cybermaxke.elementalarrows.plugin.utils.Metrics;
import net.minecraft.server.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class ElementalArrows extends JavaPlugin implements ElementalArrowsAPI {
	private static ElementalArrows instance;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		new ItemManager();
		new EntityManager();
		new ArrowManager(this);
		new EventListener(this);

		try {
			Metrics m = new Metrics(this);
			m.start();
			this.getLogger().log(Level.INFO, "Metrics loaded.");
		} catch (Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.WARNING, "Couldn't load Metrics!");
		}
	}

	@Override
	public void onDisable() {

	}

	@Override
	public ElementalPlayer getPlayer(Player player) {
		return new CraftElementalPlayer(player);
	}

	public static ElementalArrows getInstance() {
		return instance;
	}

	@Override
	public boolean isRegionProtected(Location location) {
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
			WorldGuardPlugin worldguard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
 			return worldguard.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.TNT);
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
}