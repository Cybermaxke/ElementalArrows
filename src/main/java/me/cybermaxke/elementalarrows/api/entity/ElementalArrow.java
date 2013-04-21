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
package me.cybermaxke.elementalarrows.api.entity;

import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;

public interface ElementalArrow extends Arrow {

	public double getDamage();

	public void setDamage(double damage);

	public int getKnockbackStrength();

	public void setKnockbackStrength(int strength);

	public boolean isCritical();

	public void setCritical(boolean critical);

	public boolean isPickupable();

	public void setPickupable(boolean pickup);

	public ArrowMaterial getMaterial();

	public void setMaterial(ArrowMaterial material);

	public float getSpeed();

	public void setLocation(Location location);
}