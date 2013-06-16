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
package me.cybermaxke.elementalarrows.spout.api.entity;

import me.cybermaxke.elementalarrows.spout.api.entity.selector.TargetSelector;
import me.cybermaxke.elementalarrows.spout.api.inventory.TurretInventory;

import org.bukkit.entity.Entity;
import org.spout.vanilla.component.entity.misc.Health;
import org.spout.vanilla.component.entity.substance.Substance;

public abstract class ElementalTurret extends Substance {

	/**
	 * Gets the inventory the turret is holding.
	 * @return inventory
	 */
	public abstract TurretInventory getInventory();

	/**
	 * Gets the target selector of the turret.
	 * @return selector
	 */
	public abstract TargetSelector getTargetSelector();

	/**
	 * Sets the target selector of the turret.
	 * @param selector
	 */
	public abstract void setTargetSelector(TargetSelector selector);

	/**
	 * Gets the target range of the turret.
	 * @return range
	 */
	public abstract int getTargetRange();

	/**
	 * Sets the target range of the turret.
	 * @param range
	 */
	public abstract void setTargetRange(int range);

	/**
	 * Gets the health component of the turret.
	 * @return health
	 */
	public abstract Health getHealth();

	/**
	 * Gets the amount of ticks between every shot.
	 * @return delay
	 */
	public abstract int getAttackDelay();

	/**
	 * Sets the amount of ticks between every shot.
	 * @param delay
	 */
	public abstract void setAttackDelay(int delay);

	/**
	 * Gets the target the turret is shooting at.
	 * @return target
	 */
	public abstract Entity getTarget();

	/**
	 * Sets the target the turret is shooting at.
	 * @param target
	 */
	public abstract void setTarget(Entity target);
}