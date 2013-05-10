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
package me.cybermaxke.elementalarrows.api.entity;

import org.bukkit.entity.Player;

public interface ElementalPlayer extends Player {

	/**
	 * Gets a arrow that will be shot with the speed.
	 * @param speed
	 * @return arrow
	 */
	public ElementalArrow shootElementalArrow(float speed);

	/**
	 * Gets the amount of arrows that are sticking in the players body.
	 * @return amount
	 */
	public int getArrowsInBody();

	/**
	 * Sets the amount of arrows that are sticking in the players body.
	 * @param amount
	 */
	public void setArrowsInBody(int amount);
}