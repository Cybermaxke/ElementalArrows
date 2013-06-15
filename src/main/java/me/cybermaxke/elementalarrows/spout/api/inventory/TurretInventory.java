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
package me.cybermaxke.elementalarrows.spout.api.inventory;

import org.spout.api.inventory.Inventory;

public class TurretInventory extends Inventory {
	private static final long serialVersionUID = 8993938763746030857L;

	public static final int SIZE = 9;
	public static final int HEIGHT = 3;
	public static final int LENGTH = 3;

	public TurretInventory() {
		super(SIZE);
	}
}