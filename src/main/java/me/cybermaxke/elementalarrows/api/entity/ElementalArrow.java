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
package me.cybermaxke.elementalarrows.api.entity;

import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;

public interface ElementalArrow extends Arrow {

	/**
	 * Gets the damage of the arrow, by default based on speed and enchantments.
	 * @return damage
	 */
	public double getDamage();

	/**
	 * Sets the damage of the arrow.
	 * @param damage
	 */
	public void setDamage(double damage);

	/**
	 * Gets the knockback that the arrow applies when it hits a mob.
	 * @return knockback
	 */
	public int getKnockbackStrength();

	/**
	 * Sets the knockback that the arrow will apply when it hits a mob.
	 * @param strength
	 */
	public void setKnockbackStrength(int strength);

	/**
	 * Gets if the arrow has a critical shot, default when the bow is fully charged. (Particle effect.)
	 * @return critical
	 */
	public boolean isCritical();

	/**
	 * Sets if the arrow has a critical shot. (Particle effect.)
	 * @param critical
	 */
	public void setCritical(boolean critical);

	/**
	 * Gets if the arrow is pickupable by a player.
	 * @return pickupable
	 */
	public boolean isPickupable();

	/**
	 * Sets if the arrow is pickupable by a player.
	 * @param pickup
	 */
	public void setPickupable(boolean pickup);

	/**
	 * Gets the material applied to this arrow, null if it doesn't exists.
	 * @return material
	 */
	public ArrowMaterial getMaterial();

	/**
	 * Sets the material applied to this arrow.
	 * @param material
	 */
	public void setMaterial(ArrowMaterial material);

	/**
	 * Gets the speed the arrow is shot.
	 * @return speed
	 */
	public float getSpeed();

	/**
	 * Sets the location of the arrow.
	 * @param location
	 */
	public void setLocation(Location location);

	/**
	 * Gets if the arrow is in the ground
	 * @return inGround
	 */
	public boolean isInGround();

	/**
	 * Gets the shake on this arrow. The arrow will shake after it hits the ground.
	 * @return shake
	 */
	public int getShake();
}