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
package me.cybermaxke.elementalarrows.spout.api.component.entity;

import me.cybermaxke.elementalarrows.spout.api.data.PickupMode;

import org.spout.api.entity.Entity;
import org.spout.api.math.Vector3;

import org.spout.vanilla.component.entity.substance.Substance;
import org.spout.vanilla.component.entity.substance.projectile.Projectile;

public abstract class ElementalArrow extends Substance implements Projectile {

	/**
	 * Shoots the arrow with the specific speed, and using a vector using the head/body rotation.
	 * @param shooter
	 * @param speed
	 */
	public abstract void shoot(Entity shooter, float speed);

	/**
	 * Shoots the arrow with the specific speed, spread and motion vector.
	 * @param vector
	 * @param speed
	 * @param spread
	 */
	public abstract void shoot(Vector3 vector, float speed, float spread);

	/**
	 * Shoots the arrow with the specific speed, spread and motion.
	 * @param motX
	 * @param motY
	 * @param motZ
	 * @param speed
	 * @param spread
	 */
	public abstract void shoot(float motX, float motY, float motZ, float speed, float spread);

	/**
	 * Gets the shooter of the arrow.
	 * @return shooter
	 */
	public abstract Entity getShooter();

	/**
	 * Sets the shooter of the arrow.
	 * @param shooter
	 */
	public abstract void setShooter(Entity shooter);

	/**
	 * Gets the pickup mode of the arrow.
	 * @return pickupmode
	 */
	public abstract PickupMode getPickupMode();

	/**
	 * Sets the pickup mode of the arrow.
	 * @param pickupmode
	 */
	public abstract void setPickupMode(PickupMode mode);

	/**
	 * Gets if the arrow was a critical shot.
	 * @return critical
	 */
	public abstract boolean isCritical();

	/**
	 * Sets if the arrow was a critical shot.
	 * @param critical
	 */
	public abstract void setCritical(boolean critical);

	/**
	 * Gets the damage of the arrow.
	 * @return damage
	 */
	public abstract int getDamage();

	/**
	 * Sets the damage of the arrow.
	 * @param damage
	 */
	public abstract void setDamage(int damage);

	/**
	 * Gets if the arrow is on fire.
	 * @return fire
	 */
	public abstract boolean isOnFire();

	/**
	 * Gets the fire ticks of the arrow.
	 * @return ticks
	 */
	public abstract int getFireTicks();

	/**
	 * Sets the fire ticks of the arrow.
	 * @param ticks
	 */
	public abstract void setFireTicks(int ticks);
}