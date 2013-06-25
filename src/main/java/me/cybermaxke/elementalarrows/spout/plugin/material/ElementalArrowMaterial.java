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

import org.spout.api.math.Vector2;
import org.spout.api.render.RenderMaterial;

import me.cybermaxke.elementalarrows.spout.api.entity.component.ArrowComponent;
import me.cybermaxke.elementalarrows.spout.plugin.data.ElementalRenderMaterials;

public class ElementalArrowMaterial extends me.cybermaxke.elementalarrows.spout.api.material.ElementalArrowMaterial {

	public ElementalArrowMaterial(String name, int data, Vector2 pos, Class<? extends ArrowComponent>... components) {
		super(name, data, pos, components);
	}

	@Override
	public RenderMaterial getRenderMaterial() {
		return ElementalRenderMaterials.ARROWS_MATERIAL;
	}
}