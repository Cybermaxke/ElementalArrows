package me.cybermaxke.ElementalArrows.Materials;

import java.io.File;
import java.util.List;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomItem;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
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
		
		this.file = new File(plugin.getDataFolder() + File.separator + "Arrows", this.getClass().getSimpleName() + ".yml");
		this.permission = "ElementalArrows." + this.getClass().getSimpleName() + ".Use";
		
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
	}
	
	public boolean hasPermission(SpoutPlayer player) {
		if (player.isOp() || player.hasPermission("ElementalArrows.*") || player.hasPermission(this.permission))
			return true;
		
		return false;
	}
	
	public void setUsePermission(String permission) {
		if (permission != null)
			this.permission = permission;
		else
			throw new NullPointerException("The permission can't be null!");
	}
	
	public String getUsePermission() {
		return this.permission;
	}
	
	public boolean isBlackListWorld(World world) {
		return this.isBlackListWorld(world.getName());
	}
	
	public boolean isBlackListWorld(String world) {
		
		if (this.blackListWorlds != null) {
			if (this.blackListWorlds.contains(world))
				return true;
		}
		
		return false;
	}
	
	private void saveConfig() {
		try {
			this.config.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addConfigData(String name, Object data) {
		if (!this.config.contains(name))
			this.config.set(name, data);
		
		this.saveConfig();
	}
	
	public Object getConfigData(String name) {
		if (this.config.contains(name))
			return this.config.get(name);
		
		return null;
	}
	
	public boolean isFactionProtected(Location location) {
		
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("Factions")) {
		      Faction f = Board.getFactionAt(new FLocation(location));
		      
		      if (!f.isNone())
		    	  return true;
		}
		
		return false;
	}
	
	public boolean isWorldGuardProtected(Location location) {
		
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
			WorldGuardPlugin worldguard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
			
 			return worldguard.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.TNT);
		}
		
		return false;
	}
	
	public void setMultiplePerShot(int amount) {
		if (amount > 0)
			this.amountpershot = amount;
		else 
			throw new IllegalArgumentException("The amount can't be 0!");
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
		if (itemstack != null)
			this.drop = itemstack;
		else
			throw new NullPointerException("The arrow drop can't be null!");
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