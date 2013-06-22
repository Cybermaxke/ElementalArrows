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
package me.cybermaxke.elementalarrows.bukkit.api.entity;

import me.cybermaxke.elementalarrows.bukkit.api.entity.selector.TargetSelector;
import me.cybermaxke.elementalarrows.bukkit.api.inventory.TurretInventory;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;

public interface ElementalTurret extends Entity, InventoryHolder {

	/**
	 * Gets the inventory the turret is holding.
	 * @return inventory
	 */
	public TurretInventory getInventory();

	/**
	 * Gets the custom name of the turret.
	 * @return name
	 */
	public String getName();

	/**
	 * Sets the custom name of the turret.
	 * @param name
	 */
	public void setName(String name);

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
	 * Gets the health of the turret.
	 * @return health
	 */
	public int getHealth();

	/**
	 * Sets the health of the turret.
	 * @param health
	 */
	public void setHealth(int health);

	/**
	 * Gets the range the turret will target entities.
	 * @return range
	 */
	public float getTargetRange();

	/**
	 * Sets the range the turret will target entities.
	 * @param range
	 */
	public void setTargetRange(float range);

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

	/**
	 * Gets the target selector the turret is using.
	 * @return selector
	 */
	public TargetSelector getTargetSelector();

	/**
	 * Sets the target selector the turret is using.
	 * @param selector
	 */
	public void setTargetSelector(TargetSelector selector);
}