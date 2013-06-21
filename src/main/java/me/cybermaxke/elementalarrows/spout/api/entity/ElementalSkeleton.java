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

import org.spout.vanilla.component.entity.living.Hostile;

public interface ElementalSkeleton extends ElementalEntity, Hostile {

	/**
	 * Gets the arrow the skeleton is using.
	 * @return components
	 */
	public Class<? extends ElementalArrow>[] getArrowComponents();

	/**
	 * Sets the arrow the skeleton is using.
	 * @param components
	 */
	public void setArrowComponents(Class<? extends ElementalArrow>... components);
}