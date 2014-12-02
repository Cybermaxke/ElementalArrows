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

public interface PotionType {

	public static final PotionType Speed = null;
	public static final PotionType Slowness = null;
	public static final PotionType Haste = null;
	public static final PotionType Fatigue = null;
	public static final PotionType Strength = null;
	public static final PotionType Heal = null;
	public static final PotionType Harm = null;
	public static final PotionType Jump = null;
	public static final PotionType Nausea = null;
	public static final PotionType Regeneration = null;
	public static final PotionType Resistance = null;
	public static final PotionType FireResistance = null;
	public static final PotionType WaterBreathing = null;
	public static final PotionType Invisibility = null;
	public static final PotionType Blindness = null;
	public static final PotionType NightVision = null;
	public static final PotionType Hunger = null;
	public static final PotionType Weakness = null;
	public static final PotionType Poison = null;
	public static final PotionType Wither = null;
	public static final PotionType HealthBoost = null;
	public static final PotionType Absorbtion = null;
	public static final PotionType Saturation = null;

	/**
	 * Gets the id of the potion effect type.
	 * 
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the internal id of the potion effect type.
	 * 
	 * @return the internal id
	 */
	int getInternalId();

}