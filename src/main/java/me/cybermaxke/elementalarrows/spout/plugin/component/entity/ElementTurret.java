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
package me.cybermaxke.elementalarrows.spout.plugin.component.entity;

import me.cybermaxke.elementalarrows.spout.api.component.entity.ElementalTurret;
import me.cybermaxke.elementalarrows.spout.api.inventory.TurretInventory;
import me.cybermaxke.elementalarrows.spout.plugin.data.ElementalData;

import org.spout.vanilla.VanillaPlugin;
import org.spout.vanilla.protocol.entity.object.ObjectEntityProtocol;
import org.spout.vanilla.protocol.entity.object.ObjectType;

public class ElementTurret extends ElementalTurret {

	@Override
	public void onAttached() {
		this.getOwner().getNetwork().setEntityProtocol(VanillaPlugin.VANILLA_PROTOCOL_ID, new ObjectEntityProtocol(ObjectType.ENDER_CRYSTAL));
		super.onAttached();
	}

	@Override
	public void onDetached() {
		super.onDetached();
	}

	@Override
	public void onTick(float dt) {
		super.onTick(dt);
		//TODO: Targetting and shooting arrows.
	}

	@Override
	public TurretInventory getInventory() {
		return this.getDatatable().get(ElementalData.TURRET_INVENTORY);
	}
}