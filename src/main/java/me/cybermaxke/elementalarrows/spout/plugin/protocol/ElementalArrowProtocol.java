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

import me.cybermaxke.elementalarrows.spout.plugin.entity.ElementArrow;

import org.spout.api.entity.Entity;
import org.spout.api.util.Parameter;

import org.spout.vanilla.protocol.entity.object.ObjectEntityProtocol;
import org.spout.vanilla.protocol.entity.object.ObjectType;

public class ElementalArrowProtocol extends ObjectEntityProtocol {
	/**
	 * The MC metadata index to determine if the arrow is on fire.
	 */
	public final static int FIRE_INDEX = 0;
	/**
	 * The MC metadata index to determine if the arrow has the critical effect.
	 */
	public final static int CRITICAL_INDEX = 16;

	public ElementalArrowProtocol() {
		super(ObjectType.ARROW);
	}

	@Override
	public List<Parameter<?>> getSpawnParameters(Entity entity) {
		List<Parameter<?>> params = super.getSpawnParameters(entity);
		params.add(new Parameter<Byte>(Parameter.TYPE_BYTE, FIRE_INDEX, (byte) (entity.add(ElementArrow.class).isOnFire() ? 1 : 0)));
		params.add(new Parameter<Byte>(Parameter.TYPE_BYTE, CRITICAL_INDEX, (byte) (entity.add(ElementArrow.class).isCritical() ? 1 : 0)));
		return params;
	}
}