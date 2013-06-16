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
package me.cybermaxke.elementalarrows.spout.api.entity.player;

import org.spout.api.component.entity.EntityComponent;

public abstract class ElementalPlayer extends EntityComponent {

	/**
	 * Gets the arrows in the players body.
	 * @return arrows
	 */
	public abstract byte getArrowsInBody();

	/**
	 * Sets the arrows in the players body.
	 * @param arrows
	 */
	public abstract void setArrowsInBody(byte amount);
}