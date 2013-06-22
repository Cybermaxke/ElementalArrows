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
package me.cybermaxke.elementalarrows.bukkit.api.material;

public interface CustomItem extends org.getspout.spoutapi.material.CustomItem {

	/**
	 * Gets the id a custom item will use on a normal minecraft client.
	 * @return id
	 */
	public int getId();

	/**
	 * Sets the id a custom item will use on a normal minecraft client.
	 * @param id
	 */
	public void setId(int id);
}