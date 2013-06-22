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
package me.cybermaxke.elementalarrows.bukkit.api.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigHolder {

	/**
	 * Gets the configuration file.
	 * @return config
	 */
	public YamlConfiguration getConfig();

	/**
	 * Gets the file.
	 * @return file
	 */
	public File getFile();

	/**
	 * Saves and returns if the save was a succes.
	 * @return succes
	 */
	public boolean save();

	/**
	 * Loads and returns if the load was a succes.
	 * @return succes
	 */
	public boolean load();

	/**
	 * Reloads the config file.
	 */
	public void reload();

	/**
	 * Called when you intialize your material, before the config files are saved and loaded.
	 */
	public void onInit();

	/**
	 * Called when the config file is getting loaded.
	 * @param config
	 */
	public void onLoad(YamlConfiguration config);

	/**
	 * Called when the config file is getting saved.
	 * @param config
	 */
	public void onSave(YamlConfiguration config);
}