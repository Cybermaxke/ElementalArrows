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
package me.cybermaxke.elementalarrows.plugin.libigot.entity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftArrow;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.libigot.entity.nms.EntityElementalArrow;

public class CraftElementalArrow extends CraftArrow implements ElementalArrow {

	public CraftElementalArrow(EntityElementalArrow entity) {
		super(entity.world.getServer(), entity);
	}

	@Override
	public EntityElementalArrow getHandle() {
		return (EntityElementalArrow) this.entity;
	}

	@Override
	public boolean isPickupable() {
		return this.getHandle().fromPlayer == 1;
	}

	@Override
	public void setPickupable(boolean pickup) {
		this.getHandle().fromPlayer = pickup ? 1 : 2;
	}

	@Override
	public ArrowMaterial getMaterial() {
		return this.getHandle().arrow;
	}

	@Override
	public void setMaterial(ArrowMaterial material) {
		this.getHandle().arrow = material;
	}

	@Override
	public float getSpeed() {
		return this.getHandle().speed;
	}

	@Override
	public void setLocation(Location location) {
		this.getHandle().setPosition(location.getX(), location.getY(), location.getZ());
		this.getHandle().setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}

	@Override
	public boolean isInGround() {
		return this.getHandle().inGround;
	}

	@Override
	public int getShake() {
		return this.getHandle().shake;
	}
}