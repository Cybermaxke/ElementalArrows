/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.common.entity;

public interface EntityArrow extends EntityProjectile {

	/**
	 * Gets whether the arrow in the ground is.
	 * 
	 * @return in the ground
	 */
	boolean isInGround();

	/**
	 * Gets whether the arrow critical is.
	 * 
	 * @return is critical
	 */
	boolean isCiritical();

	/**
	 * Sets whether the arrow critical is.
	 * 
	 * @param critical is critical
	 */
	void setCritical(boolean critical);

	/**
	 * Gets the damage value of the arrow.
	 * 
	 * @return the damage
	 */
	double getDamage();

	/**
	 * Sets the damage value of the arrow.
	 * 
	 * @param damage the damage
	 */
	void setDamage(double damage);

	/**
	 * Gets the knockback power of the arrow.
	 * 
	 * @return the knockback power
	 */
	int getKnockbackPower();

	/**
	 * Sets the knockback power of the arrow.
	 * 
	 * @param power the knockback power
	 */
	void setKnockbackPower(int power);

	/**
	 * Gets the elemental data.
	 * 
	 * @return the data
	 */
	int getElementData();

	/**
	 * Sets the elemental data.
	 * 
	 * @param data the data
	 */
	void setElementData(int data);

	/**
	 * Gets the pickup mode.
	 * 
	 * @return the pickup mode
	 */
	PickupMode getPickupMode();

	/**
	 * Sets the pickup mode.
	 * 
	 * @param mode the pickup mode
	 */
	void setPickupMode(PickupMode mode);

	/**
	 * All the pickup modes.
	 */
	enum PickupMode {
		/**
		 * Anyone can pickup the arrow.
		 */
		True,
		/**
		 * Nobody can pickup the arrow.
		 */
		False,
		/**
		 * Only players in creative can pickup the arrow.
		 */
		Creative,
	}

}