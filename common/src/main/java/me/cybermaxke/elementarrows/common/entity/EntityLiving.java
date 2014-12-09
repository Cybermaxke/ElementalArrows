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

import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionType;

public interface EntityLiving extends Entity {

	/**
	 * Gets the item stack the player is holding in it's hand.
	 * 
	 * @return the item stack
	 */
	ItemStack getHeldItem();

	/**
	 * Gets the eye height of the entity.
	 * 
	 * @return the eye height
	 */
	float getEyeHeight();

	/**
	 * Gets the health of the entity.
	 * 
	 * @return the health
	 */
	float getHealth();

	/**
	 * Sets the health of the entity.
	 * 
	 * @param health the health
	 */
	void setHealth(float health);

	/**
	 * Gets the yaw rotation of the head. (Around the y axis.)
	 * 
	 * @return the head yaw
	 */
	float getHeadYaw();

	/**
	 * Gets the pitch rotation of the head. (Around the x axis.)
	 * 
	 * @return the head pitch
	 */
	float getHeadPitch();

	/**
	 * Gets whether the entity a potion effect of a specific type has.
	 * 
	 * @param type the potion type
	 * @return has potion effect
	 */
	boolean hasPotionEffect(PotionType type);

	/**
	 * Adds the potion effect to the entity. Combines with the current
	 * effect of the same type if present.
	 * 
	 * @param effect the potion effect
	 * @return the combined effect
	 */
	PotionEffect addPotionEffect(PotionEffect effect);

	/**
	 * Removes the potion effect from the entity and returns the instance of present.
	 * 
	 * @param type the potion type
	 * @return the removed effect
	 */
	PotionEffect removePotionEffect(PotionType type);

	/**
	 * Gets the attribute of the entity.
	 * 
	 * @return the attribute
	 */
	Attribute getCreatureAttribute();

	/**
	 * All the entity attributes.
	 */
	enum Attribute {
		/**
		 * Undead monsters like skeletons, zombies, etc.
		 */
		Undead,
		/**
		 * Arthropod monsters like spiders.
		 */
		Arthropod,
		/**
		 * All the other undefined monsters.
		 */
		Undefined,
	}

}