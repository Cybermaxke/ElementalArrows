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
package me.cybermaxke.elementalarrows.spout.plugin.data;

import org.spout.api.Platform;
import org.spout.api.Spout;
import org.spout.api.render.RenderMaterial;

public class ElementalRenderMaterials {
	public static final RenderMaterial ARROWS_MATERIAL;

	static {
		if (Spout.getPlatform().equals(Platform.CLIENT)) {
			ARROWS_MATERIAL = Spout.getEngine().getFileSystem().getResource("material://ElementalArrows/materials/ArrowsMaterial.smt");
		} else {
			ARROWS_MATERIAL = null;
		}
	}
}