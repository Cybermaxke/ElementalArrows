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
package me.cybermaxke.elementarrows.common.potion;

public interface PotionEffect {

	/**
	 * Gets the type of the potion effect.
	 * 
	 * @return the type
	 */
	PotionType getType();

	/**
	 * Gets the duration of the potion effect.
	 * 
	 * @return the duration
	 */
	int getDuration();

	/**
	 * Gets the amplifier of the potion effect.
	 * 
	 * @return the amplifier
	 */
	int getAmplifier();

	/**
	 * Gets whether the potion effect ambient is.
	 * 
	 * @return is ambient
	 */
	boolean isAmbient();

	/**
	 * Combines the potion effect with this potion
	 * effect and returns the new combined effect.
	 * 
	 * @param effect the potion effect
	 * @return the combined effect
	 */
	PotionEffect combineWith(PotionEffect effect);

	/**
	 * Clones the potion effect.
	 * 
	 * @return the clone
	 */
	PotionEffect clone();

}