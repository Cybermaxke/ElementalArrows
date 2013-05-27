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
package me.cybermaxke.elementalarrows.plugin.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.cybermaxke.elementalarrows.api.config.ConfigHolder;

public class ElementalConfigFile implements ConfigHolder {
	public static boolean ENABLE_ELEMENTAL_SKELETON_SPAWNING = true;

	private YamlConfiguration config;
	private File file;
	private File folder;

	public ElementalConfigFile(Plugin plugin) {
		this.folder = plugin.getDataFolder();
		this.file = new File(this.folder, "Config.yml");
		this.onInit();
		this.reload();
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public YamlConfiguration getConfig() {
		return this.config;
	}

	@Override
	public boolean save() {
		if (!this.file.exists()) {
			if (!this.folder.exists()) {
				this.folder.mkdirs();
			}
			try {
				this.file.createNewFile();
			} catch (Exception e) {
				return false;
			}
		}
		try {
			this.onSave(this.config);
			this.config.save(this.file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean load() {
		if (!this.folder.exists() || !this.file.exists()) {
			if (!this.folder.exists()) {
				this.folder.mkdirs();
			}
			try {
				this.file.createNewFile();
			} catch (Exception e) {
				return false;
			}
		}
		this.config = YamlConfiguration.loadConfiguration(this.file);
		return true;
	}

	@Override
	public void reload() {
		this.load();
		this.save();
		this.onLoad(this.config);
	}

	@Override
	public void onInit() {

	}

	@Override
	public void onLoad(YamlConfiguration config) {
		ENABLE_ELEMENTAL_SKELETON_SPAWNING = config.getBoolean("Enable-ElementalSkeleton-Spawning");
	}

	@Override
	public void onSave(YamlConfiguration config) {
		if (!config.contains("Enable-ElementalSkeleton-Spawning")) {
			config.set("Enable-ElementalSkeleton-Spawning", true);
		}
	}
}