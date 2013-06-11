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
package me.cybermaxke.elementalarrows.spout.plugin.material;

import java.lang.reflect.Field;

import org.spout.api.material.Material;

public class ElementalMaterialUtils {

	/**
	 * Setting the data mask to allow sub materials.
	 * @param material
	 * @param size
	 */
	public static void setDataMask(Material material, short size) {
		try {
			Field f = Material.class.getDeclaredField("dataMask");
			f.setAccessible(true);
			f.set(material, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}