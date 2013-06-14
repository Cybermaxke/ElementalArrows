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
package me.cybermaxke.elementalarrows.spout.plugin.component.player;

import java.lang.reflect.Method;

import me.cybermaxke.elementalarrows.spout.api.component.player.ElementalPlayer;
import me.cybermaxke.elementalarrows.spout.plugin.data.ElementalData;

import org.spout.api.util.Parameter;

import org.spout.vanilla.component.entity.VanillaEntityComponent;
import org.spout.vanilla.component.entity.living.Human;

public class ElementPlayer extends ElementalPlayer {

	@Override
	public void onAttached() {
		super.onAttached();
	}

	@Override
	public void onDetached() {
		super.onDetached();
	}

	@Override
	public byte getArrowsInBody() {
		return this.getDatatable().get(ElementalData.ARROWS_IN_BODY);
	}

	@Override
	public void setArrowsInBody(byte amount) {
		this.getDatatable().put(ElementalData.ARROWS_IN_BODY, amount);

		try {
			Method m = VanillaEntityComponent.class.getDeclaredMethod("setMetadata", Parameter.class);
			m.setAccessible(true);
			m.invoke(this.getOwner().add(Human.class), new Parameter<Byte>(Parameter.TYPE_BYTE, 10, amount));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}