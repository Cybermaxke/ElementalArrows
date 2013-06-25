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
package me.cybermaxke.elementalarrows.spout.api.material;

import me.cybermaxke.elementalarrows.spout.api.entity.component.ArrowComponent;

import org.spout.api.math.Vector2;
import org.spout.vanilla.material.VanillaMaterials;

public class ElementalArrowMaterial extends ElementalItemMaterial implements ArrowMaterial {
	private Class<? extends ArrowComponent>[] components;

	public ElementalArrowMaterial(String name, int data, Class<? extends ArrowComponent>... components) {
		this(name, data, null, components);
	}

	public ElementalArrowMaterial(String name, int data, Vector2 pos, Class<? extends ArrowComponent>... components) {
		super(name, VanillaMaterials.ARROW, data, pos);
		this.components = components;
	}

	@Override
	public Class<? extends ArrowComponent>[] getArrowComponents() {
		return this.components;
	}
}