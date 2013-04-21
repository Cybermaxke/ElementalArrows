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
import java.util.ArrayList;
import java.util.List;

import me.cybermaxke.elementalarrows.api.config.ConfigHolder;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class GenericCustomArrow extends GenericCustomItem implements ArrowMaterial, ConfigHolder {
	private double damageMulti = 1.0D;
	private double speedMulti = 1.0D;
	private double knockbackMulti = 0.0D;

	private int fireTicks = 0;

	private boolean pickup = true;

	private List<String> bWorlds = new ArrayList<String>();
	private Permission permission;
	private ItemStack drop;
	private YamlConfiguration config;

	private File file;
	private File folder;

	public GenericCustomArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.drop = new SpoutItemStack(this);
		this.folder = new File(plugin.getDataFolder() + File.separator + "Arrows");
		this.file = new File(this.folder, name + ".yml");
		this.permission = new Permission((plugin.getName() + ".arrows." + name).toLowerCase().replace(" ", ""), PermissionDefault.OP);
		this.onInit();
		this.load();
		this.save();
		this.onLoad(this.config);
		if (this.getRecipes() != null) {
			for (Recipe r : this.getRecipes()) {
				SpoutManager.getMaterialManager().registerSpoutRecipe(r);
			}
		}
		SpoutManager.getFileManager().addToCache(plugin, this.getTexture());
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
	public double getDamageMultiplier() {
		return this.damageMulti;
	}

	@Override
	public void setDamageMultiplier(double multiplier) {
		this.damageMulti = multiplier;
	}

	@Override
	public double getSpeedMutiplier() {
		return this.speedMulti;
	}

	@Override
	public void setSpeedMultiplier(double multiplier) {
		this.speedMulti = multiplier;
	}

	@Override
	public double getKnockbackStrengthMultiplier() {
		return this.knockbackMulti;
	}

	@Override
	public void setKnockbackStrengthMultiplier(double multiplier) {
		this.knockbackMulti = multiplier;
	}

	@Override
	public boolean isPickupable() {
		return this.pickup;
	}

	@Override
	public void setPickupable(boolean pickup) {
		this.pickup = pickup;
	}

	@Override
	public Permission getPermission() {
		return this.permission;
	}

	@Override
	public boolean hasPermission() {
		return this.permission != null;
	}

	@Override
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@Override
	public int getFireTicks() {
		return this.fireTicks;
	}

	@Override
	public void setFireTicks(int amount) {
		this.fireTicks = amount;
	}

	@Override
	public List<World> getBlackListWorlds() {
		List<World> l = new ArrayList<World>();
		for (String s : this.bWorlds) {
			World w = Bukkit.getWorld(s);
			if (w != null) {
				l.add(w);
			}
		}
		return l;
	}

	@Override
	public boolean isBlackListWorld(World world) {
		return this.bWorlds.contains(world.getName().toLowerCase());
	}

	@Override
	public void addBlackListWorld(World world) {
		this.bWorlds.add(world.getName().toLowerCase());
	}

	@Override
	public ItemStack getDrop() {
		return this.drop;
	}

	@Override
	public void setDrop(ItemStack itemstack) {
		this.drop = itemstack;
	}

	@Override
	public void onInit() {

	}

	@Override
	public void onLoad(YamlConfiguration config) {
		this.damageMulti = config.contains("DamageMultiplier") ? config.getDouble("DamageMultiplier") : this.damageMulti;
		this.speedMulti = config.contains("SpeedMultiplier") ? config.getDouble("SpeedMultiplier") : this.speedMulti;
		this.knockbackMulti = config.contains("KnockbackMultiplier") ? config.getDouble("KnockbackMultiplier") : this.knockbackMulti;
		this.fireTicks = config.contains("FireTicks") ? config.getInt("FireTicks") : this.fireTicks;
		this.bWorlds = config.contains("WorldsBlackList") ? config.getStringList("WorldsBlackList") : this.bWorlds;
		if (config.contains("Permission")) {
			this.permission = new Permission(config.getString("Permission"), PermissionDefault.OP);
		}
		if (config.contains("Texture")) {
			this.setTexture(config.getString("Texture"));
		}
		if (config.contains("Name")) {
			this.setName(config.getString("Name"));
		}
	}

	@Override
	public void onSave(YamlConfiguration config) {
		if (!config.contains("DamageMultiplier")) {
			config.set("DamageMultiplier", this.damageMulti);
		}
		if (!config.contains("SpeedMultiplier")) {
			config.set("SpeedMultiplier", this.speedMulti);
		}
		if (!config.contains("KnockbackMultiplier")) {
			config.set("KnockbackMultiplier", this.knockbackMulti);
		}
		if (!config.contains("FireTicks")) {
			config.set("FireTicks", this.fireTicks);
		}
		if (!config.contains("WorldsBlackList")) {
			config.set("WorldsBlackList", this.bWorlds);
		}
		if (!config.contains("Permission")) {
			config.set("Permission", this.permission == null ? this.permission : this.permission.getName());
		}
		if (!config.contains("Texture")) {
			config.set("Texture", this.getTexture());
		}
		if (!config.contains("Name")) {
			config.set("Name", this.getName());
		}
	}

	@Override
	public Recipe[] getRecipes() {
		return null;
	}

	@Override
	public void onShoot(LivingEntity shooter, ElementalArrow arrow, ItemStack bow) {

	}

	@Override
	public void onHit(LivingEntity shooter, LivingEntity entity, ElementalArrow arrow) {

	}

	@Override
	public void onHit(LivingEntity shooter, ElementalArrow arrow) {

	}

	@Override
	public void onTick(LivingEntity shooter, ElementalArrow arrow) {

	}
}