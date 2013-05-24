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
import me.cybermaxke.elementalarrows.api.ParticleEffect;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.plugin.cmd.Commands;
import me.cybermaxke.elementalarrows.plugin.listeners.EventListener;
import me.cybermaxke.elementalarrows.plugin.material.MaterialManager;
import me.cybermaxke.elementalarrows.plugin.utils.Metrics;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class ElementalArrowsPlugin extends JavaPlugin implements ElementalArrowsAPI {
	private static ElementalArrowsPlugin instance;
	private ElementalArrowsAPI api;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		try {
			Class.forName("org.getspout.spout.Spout");
		} catch (Exception e) {
			this.getLogger().warning("You need to install the SpoutPlugin before you can use this.");
			this.setEnabled(false);
			return;
		}

		this.api = new ElementalArrows();

		new MaterialManager(this);
		new EventListener(this);
		new Commands(this);

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

	public static ElementalArrowsPlugin getInstance() {
		return instance;
	}

	@Override
	public ElementalPlayer getPlayer(Player player) {
		return this.api.getPlayer(player);
	}

	@Override
	public ElementalArrow shootElementalArrow(Location location, Vector vector, float speed, float spread) {
		return this.api.shootElementalArrow(location, vector, speed, spread);
	}

	@Override
	public <T extends Entity> T spawn(Class<T> entity, Location location, SpawnReason reason) {
		return this.api.spawn(entity, location, reason);
	}

	@Override
	public <T extends Entity> T spawn(Class<T> entity, Location location) {
		return this.api.spawn(entity, location);
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
	public boolean isReplaceable(Material block) {
		return this.api.isReplaceable(block);
	}

	@Override
	public void playFireworkEffect(Location location, FireworkEffect... effects) {
		this.api.playFireworkEffect(location, effects);
	}

	@Override
	public float getEntityHeight(Entity entity) {
		return this.api.getEntityHeight(entity);
	}

	@Override
	public float getEntityLength(Entity entity) {
		return this.api.getEntityLength(entity);
	}

	@Override
	public float getEntityWidth(Entity entity) {
		return this.getEntityWidth(entity);
	}

	@Override
	public void playEffect(Location location, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, int count, Object... data) {
		this.api.playEffect(location, effect, offsetX, offsetY, offsetZ, count, data);
	}

	@Override
	public void playEffect(Player player, Location location, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, int count, Object... data) {
		this.api.playEffect(player, location, effect, offsetX, offsetY, offsetZ, count, data);
	}
}