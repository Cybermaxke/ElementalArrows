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

import me.cybermaxke.elementalarrows.spout.api.data.PickupMode;
import me.cybermaxke.elementalarrows.spout.api.inventory.TurretInventory;

import org.spout.api.inventory.ItemStack;
import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyImpl;
import org.spout.vanilla.material.VanillaMaterials;

public class ElementalData {

	/**
	 * Data keys for the arrows.
	 */
	public static final DefaultedKey<Boolean> CRITICAL = new DefaultedKeyImpl<Boolean>("critical", false);
	public static final DefaultedKey<PickupMode> PICKUP_MODE = new DefaultedKeyImpl<PickupMode>("pickup_mode", PickupMode.UNABLE);
	public static final DefaultedKey<Integer> ARROW_DAMAGE = new DefaultedKeyImpl<Integer>("arrow_damage", 2);
	public static final DefaultedKey<Integer> FIRE_TICKS = new DefaultedKeyImpl<Integer>("fire_ticks", 0);

	/**
	 * Data keys for humans.
	 */
	public static final DefaultedKey<Byte> ARROWS_IN_BODY = new DefaultedKeyImpl<Byte>("arrows_in_body", (byte) 0);

	/**
	 * Data keys for fireworks.
	 */
	public static final DefaultedKey<ItemStack> FIREWORK_EFFECT = new DefaultedKeyImpl<ItemStack>("firework_effect", new ItemStack(VanillaMaterials.FIREWORKS, 1));
	public static final DefaultedKey<Integer> FIREWORK_POWER = new DefaultedKeyImpl<Integer>("firework_power", 1);

	/**
	 * Data keys for turrets.
	 */
	public static final DefaultedKey<TurretInventory> TURRET_INVENTORY = new DefaultedKeyImpl<TurretInventory>("turret_inventory", new TurretInventory());
	public static final DefaultedKey<Integer> TURRET_RANGE = new DefaultedKeyImpl<Integer>("turret_range", 7);
	public static final DefaultedKey<Integer> TURRET_ATTACK_DELAY = new DefaultedKeyImpl<Integer>("turret_attack_delay", 15);
}