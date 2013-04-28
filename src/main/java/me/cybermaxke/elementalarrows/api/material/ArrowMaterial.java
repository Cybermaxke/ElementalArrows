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
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.permissions.Permission;

public interface ArrowMaterial {

	public double getDamageMultiplier();

	public void setDamageMultiplier(double multiplier);

	public double getSpeedMutiplier();

	public void setSpeedMultiplier(double multiplier);

	public double getKnockbackStrengthMultiplier();

	public void setKnockbackStrengthMultiplier(double multiplier);

	public boolean isPickupable();

	public void setPickupable(boolean pickup);

	public Permission getPermission();

	public boolean hasPermission();

	public void setPermission(Permission permission);

	public int getFireTicks();

	public void setFireTicks(int amount);

	public ItemStack getDrop();

	public void setDrop(ItemStack itemstack);

	public List<World> getBlackListWorlds();

	public boolean isBlackListWorld(World world);

	public void addBlackListWorld(World world);

	public String getSkeletonSkin();

	public void setSkeletonSkin(String texture);

	public void onInit();

	public Recipe[] getRecipes();

	public void onShoot(LivingEntity shooter, ElementalArrow arrow, ItemStack bow);

	public void onHit(LivingEntity shooter, LivingEntity entity, ElementalArrow arrow);

	public void onHit(LivingEntity shooter, ElementalArrow arrow);

	public void onTick(LivingEntity shooter, ElementalArrow arrow);
}