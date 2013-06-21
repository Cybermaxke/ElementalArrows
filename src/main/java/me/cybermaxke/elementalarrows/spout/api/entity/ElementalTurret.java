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

import me.cybermaxke.elementalarrows.spout.api.entity.selector.EntitySelector;
import me.cybermaxke.elementalarrows.spout.api.inventory.TurretInventory;

import org.spout.api.entity.Entity;

import org.spout.vanilla.component.entity.misc.Health;

public interface ElementalTurret extends ElementalEntity {

	/**
	 * Gets the inventory the turret is holding.
	 * @return inventory
	 */
	public TurretInventory getInventory();

	/**
	 * Gets the target selector of the turret.
	 * @return selector
	 */
	public EntitySelector getTargetSelector();

	/**
	 * Sets the target selector of the turret.
	 * @param selector
	 */
	public void setTargetSelector(EntitySelector selector);

	/**
	 * Gets the target range of the turret.
	 * @return range
	 */
	public int getTargetRange();

	/**
	 * Sets the target range of the turret.
	 * @param range
	 */
	public void setTargetRange(int range);

	/**
	 * Gets the health component of the turret.
	 * @return health
	 */
	public Health getHealth();

	/**
	 * Gets the amount of ticks between every shot.
	 * @return delay
	 */
	public int getAttackDelay();

	/**
	 * Sets the amount of ticks between every shot.
	 * @param delay
	 */
	public void setAttackDelay(int delay);

	/**
	 * Gets the target the turret is shooting at.
	 * @return target
	 */
	public Entity getTarget();

	/**
	 * Sets the target the turret is shooting at.
	 * @param target
	 */
	public void setTarget(Entity target);
}