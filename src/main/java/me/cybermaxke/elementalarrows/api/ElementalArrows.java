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
package me.cybermaxke.elementalarrows.api;

public class ElementalArrows {
	public static ElementalArrowsAPI instance;

	/**
	 * Gets the api instance, 'null' if it is disabled.
	 * @return
	 */
	public static ElementalArrowsAPI getAPI() {
		return instance;
	}

	/**
	 * Sets the api instance.
	 * @param api
	 */
	public static void setAPI(ElementalArrowsAPI api) {
		instance = api;
	}
}