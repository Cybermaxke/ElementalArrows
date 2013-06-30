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
package me.cybermaxke.elementalarrows.bukkit.api.material;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalArrow;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;

public class GenericCustomArrow extends GenericCustomItem implements ArrowMaterial {
	private double damageMulti;
	private double speedMulti;
	private double knockbackMulti;
	private int fireTicks;
	private boolean pickup = true;
	private boolean skeletonUsable = true;

	private String skeletonTexture;
	private List<String> bWorlds = new ArrayList<String>();
	private Permission permission;
	private Permission craftingPermission;
	private ItemStack drop;

	public GenericCustomArrow(Plugin plugin, String name, File texture) {
		super(plugin, name, texture);
		this.init();
	}

	public GenericCustomArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.init();
	}

	private void init() {
		this.setId(Material.ARROW.getId());
		this.drop = new SpoutItemStack(this);
		if (this.getRecipes() != null) {
			for (Recipe r : this.getRecipes()) {
				SpoutManager.getMaterialManager().registerSpoutRecipe(r);
			}
		}
	}

	@Override
	public void onInit() {
		this.permission = new Permission((this.getPlugin().getName() + ".arrows." + this.getName()).toLowerCase().replace(" ", "") + ".use", PermissionDefault.TRUE);
		this.craftingPermission = new Permission((this.getPlugin().getName() + ".arrows." + this.getName()).toLowerCase().replace(" ", "") + ".craft", PermissionDefault.TRUE);
		this.damageMulti = 1.0D;
		this.speedMulti = 1.0D;
		this.knockbackMulti = 0.0D;
		this.fireTicks = 0;
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
	public String getSkeletonSkin() {
		return this.skeletonTexture;
	}

	@Override
	public void setSkeletonSkin(String texture) {
		this.skeletonTexture = texture;
	}

	@Override
	public boolean isSkeletonUsable() {
		return this.skeletonUsable;
	}

	@Override
	public void setSkeletonUsable(boolean usable) {
		this.skeletonUsable = usable;
	}

	@Override
	public Permission getCraftingPermission() {
		return this.craftingPermission;
	}

	@Override
	public void setCraftingPermission(Permission permission) {
		this.craftingPermission = permission;
	}

	@Override
	public void onLoad(YamlConfiguration config) {
		super.onLoad(config);
		this.damageMulti = config.getDouble("DamageMultiplier");
		this.speedMulti = config.getDouble("SpeedMultiplier");
		this.knockbackMulti = config.getDouble("KnockbackMultiplier");
		this.fireTicks = config.getInt("FireTicks");
		this.skeletonUsable = config.getBoolean("SkeletonUsable");
		this.bWorlds = config.getStringList("WorldsBlackList");
		String perm = config.getString("Permission");
		this.permission = perm.length() > 0 ? new Permission(perm, PermissionDefault.TRUE) : null;
		String craftPerm = config.getString("CraftingPermission");
		this.craftingPermission = craftPerm.length() > 0 ? new Permission(craftPerm, PermissionDefault.TRUE) : null;
		String texture = config.getString("SkeletonTexture");
		if (texture.length() > 0) {
			this.skeletonTexture = texture;
		}
	}

	@Override
	public void onSave(YamlConfiguration config) {
		super.onSave(config);
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
		if (!config.contains("SkeletonUsable")) {
			config.set("SkeletonUsable", this.skeletonUsable);
		}
		if (!config.contains("WorldsBlackList")) {
			config.set("WorldsBlackList", this.bWorlds);
		}
		if (!config.contains("Permission")) {
			config.set("Permission", this.permission == null ? "" : this.permission.getName());
		}
		if (!config.contains("CraftingPermission")) {
			config.set("CraftingPermission", this.craftingPermission == null ? "" : this.craftingPermission.getName());
		}
		if (!config.contains("SkeletonTexture")) {
			config.set("SkeletonTexture", this.skeletonTexture == null ? "" : this.skeletonTexture);
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
	public void onHit(LivingEntity shooter, Entity entity, ElementalArrow arrow) {
		if (entity instanceof LivingEntity) {
			this.onHit(shooter, (LivingEntity) entity, arrow);
		}
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