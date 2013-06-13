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
package me.cybermaxke.elementalarrows.spout.api.data;

import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyImpl;

public class ElementalData {
	/**
	 * Data keys for the arrows.
	 */
	public static final DefaultedKey<Boolean> CRITICAL = new DefaultedKeyImpl<Boolean>("critical", false);
	public static final DefaultedKey<PickupMode> PICKUP_MODE = new DefaultedKeyImpl<PickupMode>("pickup_mode", PickupMode.UNABLE);

	/**
	 * Data keys for humans.
	 */
	public static final DefaultedKey<Byte> ARROWS_IN_BODY = new DefaultedKeyImpl<Byte>("arrows_in_body", (byte) 0);
}