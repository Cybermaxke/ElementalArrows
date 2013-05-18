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
package me.cybermaxke.elementalarrows.plugin.inventory;

import me.cybermaxke.elementalarrows.api.entity.ElementalTurret;
import me.cybermaxke.elementalarrows.api.inventory.TurretInventory;
import me.cybermaxke.elementalarrows.plugin.inventory.nms.InventoryTurret;

import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;

public class CraftTurretInventory extends CraftInventory implements TurretInventory {

	public CraftTurretInventory(InventoryTurret inventory) {
		super(inventory);
	}

	@Override
	public ElementalTurret getHolder() {
		return (ElementalTurret) super.getHolder();
	}

	@Override
	public InventoryTurret getInventory() {
		return (InventoryTurret) super.getInventory();
	}
}