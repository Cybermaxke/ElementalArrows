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
package me.cybermaxke.ElementalArrows.Materials;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import me.cybermaxke.ElementalArrows.ArrowEntity;
import net.jzx7.regios.RegiosPlugin;
import net.jzx7.regiosapi.regions.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.material.item.GenericCustomItem;
import org.getspout.spoutapi.material.item.GenericItem;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public abstract class CustomArrowItem extends GenericCustomItem {
	private File file;
	private YamlConfiguration config;
	
	private List<String> blackListWorlds = null;
	
	private String permission = null;

	private int damage = 0;
	private int knockback = 0;
	private int fireticks = 0;
	private int amountpershot = 1;
	
	private boolean canpickup = true;
	
	private ItemStack drop;

	public CustomArrowItem(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		
		try {
			Field f = GenericItem.class.getDeclaredField("id");
			f.setAccessible(true);
			
			Field mf = Field.class.getDeclaredField("modifiers");
			mf.setAccessible(true);
		    mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
		    
		    f.set(this, MaterialData.arrow.getRawId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.file = new File(plugin.getDataFolder() + File.separator + "Arrows", this.getClass().getSimpleName() + ".yml");
		this.permission = plugin.getName() + "." + this.getClass().getSimpleName() + ".Use";
		
		if (!this.file.exists()) {
			this.config = new YamlConfiguration();
			
			this.config.set("Name", name);
			this.config.set("Texture", texture);
			this.config.set("Permission", this.permission);
			
			this.saveConfig();	
		} else {
			this.config = YamlConfiguration.loadConfiguration(this.file);
			
			this.setName(this.config.getString("Name"));
			this.setTexture(this.config.getString("Texture"));
			this.permission = this.config.getString("Permission");
			
			if (this.config.contains("BlackListWorlds")) {
				this.blackListWorlds = this.config.getStringList("BlackListWorlds");
			}
		}
		
		SpoutManager.getFileManager().addToCache(plugin, this.getTexture());
		this.registerRecipes();
		this.drop = new SpoutItemStack(this);
		
		MaterialData.addCustomItem(this);
	}
	
	public boolean hasPermission(SpoutPlayer player) {
		return player.hasPermission(new Permission(this.permission, PermissionDefault.OP));
	}

	public void setUsePermission(String permission) {
		if (permission != null) {
			this.permission = permission;
		} else {
			throw new NullPointerException("The permission can't be null!");
		}
	}
	
	public String getUsePermission() {
		return this.permission;
	}
	
	public boolean isBlackListWorld(World world) {
		return this.isBlackListWorld(world.getName());
	}
	
	public boolean isBlackListWorld(String world) {
		return this.blackListWorlds != null && this.blackListWorlds.contains(world) ? true : false;
	}
	
	private void saveConfig() {
		try {
			this.config.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addConfigData(String name, Object data) {
		if (!this.config.contains(name)) {
			this.config.set(name, data);
		}
		
		this.saveConfig();
	}
	
	public Object getConfigData(String name) {
		if (this.config.contains(name)) {
			return this.config.get(name);
		}
		
		return null;
	}
	
	public boolean isFactionProtected(Location location) {
		return Bukkit.getServer().getPluginManager().isPluginEnabled("Factions") && !Board.getFactionAt(new FLocation(location)).isNone() ? true : false;
	}
	
	public boolean isWorldGuardProtected(Location location) {
		
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
			WorldGuardPlugin worldguard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
 			return worldguard.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.TNT);
		}
		
		return false;
	}
	
	public boolean isRegionsProtected(Location location) {
		
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Regios")) {
			RegiosPlugin r = (RegiosPlugin) Bukkit.getServer().getPluginManager().getPlugin("Regios");
			
			if (r.isInRegion(location)) {
				Region reg = r.getRegion(location);
				
				if (reg.isExplosionsEnabled()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void setMultiplePerShot(int amount) {
		if (amount > 0) {
			this.amountpershot = amount;
		} else {
			throw new IllegalArgumentException("The amount can't be 0!");
		}
	}
	
	public int getMultiplePerShot() {
		return this.amountpershot;
	}
	
	public boolean canPickup() {
		return this.canpickup;
	}
	
	public void setCanPickup(boolean pickup) {
		this.canpickup = pickup;
	}
	
	public void setDamage(int amount) {
		this.damage = amount;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setKnockback(int amount) {
		this.knockback = amount;
	}
	
	public int getKnockback() {
		return this.knockback;
	}
	
	public void setFireTicks(int amount) {
		this.fireticks = amount;
	}
	
	public int getFireTicks() {
		return this.fireticks;
	}
	
	public void setArrowDrop(ItemStack itemstack) {
		if (itemstack != null) {
			this.drop = itemstack;
		} else {
			throw new NullPointerException("The arrow drop can't be null!");
		}
	}
	
	public ItemStack getArrowDrop() {
		return this.drop;
	}
	
	public abstract void registerRecipes();
	
	public abstract void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow);
	
	public abstract void onHit(Player shooter, ArrowEntity arrow);
	
	public abstract void onShoot(Player shooter, ArrowEntity arrow);
	
	public abstract void onTick(Player shooter, ArrowEntity arrow);
}