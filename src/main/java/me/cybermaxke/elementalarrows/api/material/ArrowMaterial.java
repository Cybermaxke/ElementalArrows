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

import java.util.List;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.permissions.Permission;

public interface ArrowMaterial {

	/**
	 * Gets the damage multiplier of this material.
	 * @return multiplier
	 */
	public double getDamageMultiplier();

	/**
	 * Sets the damage multiplier of this material.
	 * @param multiplier
	 */
	public void setDamageMultiplier(double multiplier);

	/**
	 * Gets the speed multiplier of this material.
	 * @return multiplier
	 */
	public double getSpeedMutiplier();

	/**
	 * Sets the speed multiplier of this material.
	 * @param multiplier
	 */
	public void setSpeedMultiplier(double multiplier);

	/**
	 * Gets the knockback multiplier of this material.
	 * @return multiplier
	 */
	public double getKnockbackStrengthMultiplier();

	/**
	 * Sets the knockback multiplier of this material.
	 * @param multiplier
	 */
	public void setKnockbackStrengthMultiplier(double multiplier);

	/**
	 * Gets if this material is pickupable.
	 * @return pickup
	 */
	public boolean isPickupable();

	/**
	 * Sets if this material is pickupable.
	 * @param pickup
	 */
	public void setPickupable(boolean pickup);

	/**
	 * Gets the permission used by this material.
	 * @return permission
	 */
	public Permission getPermission();

	/**
	 * Sets the permission used by this material.
	 * @param permission
	 */
	public void setPermission(Permission permission);

	/**
	 * Gets the fire ticks of this material.
	 * @return fireTicks
	 */
	public int getFireTicks();

	/**
	 * Sets the fire ticks of this material.
	 * @param ticks
	 */
	public void setFireTicks(int ticks);

	/**
	 * Gets the item drop of this material.
	 * @return item
	 */
	public ItemStack getDrop();

	/**
	 * Sets the item drop of this material.
	 * @param item
	 */
	public void setDrop(ItemStack item);

	/**
	 * Gets all the worlds this material is blacklisted.
	 * @return worlds
	 */
	public List<World> getBlackListWorlds();

	/**
	 * Gets if this material is blacklisted for the world.
	 * @param world
	 * @return blacklisted
	 */
	public boolean isBlackListWorld(World world);

	/**
	 * Adds a new blacklist world.
	 * @param world
	 */
	public void addBlackListWorld(World world);

	/**
	 * Gets the skeleton skin of this material.
	 * @return skin
	 */
	public String getSkeletonSkin();

	/**
	 * Sets the skeleton skin of this material.
	 * @param skin
	 */
	public void setSkeletonSkin(String skin);

	/**
	 * Gets the permission the player needs to craft this arrow.
	 * @return permission
	 */
	public Permission getCraftingPermission();

	/**
	 * Sets the permission the player needs to craft this arrow.
	 * @param permission
	 */
	public void setCraftingPermission(Permission permission);

	/**
	 * Gets all the recipes used by this material.
	 * @return recipes
	 */
	public Recipe[] getRecipes();

	/**
	 * Called when the arrow is shot by a player or from a dispenser.
	 * @param shooter
	 * @param arrow
	 * @param bow
	 */
	public void onShoot(LivingEntity shooter, ElementalArrow arrow, ItemStack bow);

	/**
	 * Called when the arrow hits the entity.
	 * @param shooter
	 * @param entity
	 * @param arrow
	 */
	public void onHit(LivingEntity shooter, Entity entity, ElementalArrow arrow);

	/**
	 * Called when the arrow hits the entity.
	 * @param shooter
	 * @param entity
	 * @param arrow
	 */
	public void onHit(LivingEntity shooter, LivingEntity entity, ElementalArrow arrow);

	/**
	 * Called when the entity hits the ground or a entity.
	 * @param shooter
	 * @param arrow
	 */
	public void onHit(LivingEntity shooter, ElementalArrow arrow);

	/**
	 * Called every tick.
	 * @param shooter
	 * @param arrow
	 */
	public void onTick(LivingEntity shooter, ElementalArrow arrow);
}