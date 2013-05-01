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
package me.cybermaxke.elementalarrows.api.material;

import java.io.File;

import me.cybermaxke.elementalarrows.api.config.ConfigHolder;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class GenericCustomItem extends org.getspout.spoutapi.material.item.GenericCustomItem implements CustomItem, ConfigHolder {
	private int id;

	private YamlConfiguration config;
	private File file;
	private File folder;

	public GenericCustomItem(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.id = this.getRawId();
		this.folder = new File(plugin.getDataFolder() + File.separator + "Materials");
		this.file = new File(this.folder, name.replace(" ", "") + ".yml");
		this.onInit();
		this.reload();
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void onInit() {

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
	public void onLoad(YamlConfiguration config) {
		this.setTexture(config.getString("Texture"));
		this.setName(config.getString("Name"));
	}

	@Override
	public void onSave(YamlConfiguration config) {
		if (!config.contains("Texture")) {
			config.set("Texture", this.getTexture());
		}
		if (!config.contains("Name")) {
			config.set("Name", this.getName());
		}
	}
}