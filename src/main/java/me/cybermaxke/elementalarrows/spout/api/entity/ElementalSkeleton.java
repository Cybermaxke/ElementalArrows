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

import org.spout.vanilla.component.entity.living.hostile.Skeleton;

public abstract class ElementalSkeleton extends Skeleton {

	/**
	 * Gets the arrow the skeleton is using.
	 * @return arrow
	 */
	public abstract Class<? extends ElementalArrow> getArrow();

	/**
	 * Sets the arrow the skeleton is using.
	 * @param arrow
	 */
	public abstract void setArrow(Class<? extends ElementalArrow> arrow);
}