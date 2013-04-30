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

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ElementalArrows {
	public static final String PLUGIN_NAME = "ElementalArrows";

	/**
	 * Gets the api on a save way.
	 * @return api
	 */
	public static ElementalArrowsAPI getAPI() {
		if (!Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME)) {
			return null;
		}

		Plugin p = Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
		return (ElementalArrowsAPI) (p instanceof ElementalArrowsAPI ? p : null);
	}
}