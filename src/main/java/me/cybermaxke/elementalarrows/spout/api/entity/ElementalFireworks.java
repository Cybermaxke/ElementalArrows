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
package me.cybermaxke.elementalarrows.spout.api.entity;

import java.util.List;

import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkEffect;

public interface ElementalFireworks extends ElementalEntity {

	/**
	 * Gets the power of the firework.
	 * @return power
	 */
	public int getPower();

	/**
	 * Sets the power of the firework.
	 * @param power
	 */
	public void setPower(int power);

	/**
	 * Gets all the effects of the firework.
	 * @return effects
	 */
	public List<FireworkEffect> getEffects();

	/**
	 * Adds a effect to the firework.
	 * @param effect
	 */
	public void addEffect(FireworkEffect effect);

	/**
	 * Adds multiple effects to the firework.
	 * @param effects
	 */
	public void addEffects(FireworkEffect... effects);
}