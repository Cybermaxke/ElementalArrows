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

import java.util.List;

import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkEffect;

import org.spout.vanilla.component.entity.substance.Substance;

public abstract class ElementalFireworks extends Substance {

	/**
	 * Gets the power of the firework.
	 * @return power
	 */
	public abstract int getPower();

	/**
	 * Sets the power of the firework.
	 * @param power
	 */
	public abstract void setPower(int power);

	/**
	 * Gets all the effects of the firework.
	 * @return effects
	 */
	public abstract List<FireworkEffect> getEffects();

	/**
	 * Adds a effect to the firework.
	 * @param effect
	 */
	public abstract void addEffect(FireworkEffect effect);

	/**
	 * Adds multiple effects to the firework.
	 * @param effects
	 */
	public abstract void addEffects(FireworkEffect... effects);
}