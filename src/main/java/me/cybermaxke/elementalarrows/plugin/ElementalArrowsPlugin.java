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
import me.cybermaxke.elementalarrows.plugin.arrow.ArrowManager;
import me.cybermaxke.elementalarrows.plugin.cmd.Commands;
import me.cybermaxke.elementalarrows.plugin.listeners.EventListener;
import me.cybermaxke.elementalarrows.plugin.utils.Metrics;

import org.bukkit.plugin.java.JavaPlugin;

public class ElementalArrowsPlugin extends JavaPlugin {
	private static ElementalArrowsPlugin instance;
	private ElementalArrowsAPI api;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		try {
			Class.forName("org.libigot.Libigot");
			this.api = new me.cybermaxke.elementalarrows.plugin.libigot.ElementalArrows();
		} catch (Exception e) {
			this.api = new me.cybermaxke.elementalarrows.plugin.craftbukkit.ElementalArrows();
		}

		new ArrowManager(this);
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

	public ElementalArrowsAPI getAPI() {
		return this.api;
	}

	public static ElementalArrowsPlugin getInstance() {
		return instance;
	}
}