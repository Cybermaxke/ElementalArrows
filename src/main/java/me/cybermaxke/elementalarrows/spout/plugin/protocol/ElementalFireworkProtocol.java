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
package me.cybermaxke.elementalarrows.spout.plugin.protocol;

import java.util.List;

import me.cybermaxke.elementalarrows.spout.plugin.component.entity.ElementFireworks;

import org.spout.api.entity.Entity;
import org.spout.api.inventory.ItemStack;
import org.spout.api.util.Parameter;

import org.spout.vanilla.protocol.entity.object.ObjectEntityProtocol;
import org.spout.vanilla.protocol.entity.object.ObjectType;

public class ElementalFireworkProtocol extends ObjectEntityProtocol {
	/**
	 * The MC metadata index to determine the fireworks effect.
	 */
	public final static int EFFECT_INDEX = 8;

	public ElementalFireworkProtocol() {
		super(ObjectType.FIREWORKS_ROCKET);
	}

	@Override
	public List<Parameter<?>> getSpawnParameters(Entity entity) {
		List<Parameter<?>> params = super.getSpawnParameters(entity);
		params.add(new Parameter<ItemStack>(Parameter.TYPE_ITEM, EFFECT_INDEX, entity.add(ElementFireworks.class).getItem()));
		return params;
	}
}