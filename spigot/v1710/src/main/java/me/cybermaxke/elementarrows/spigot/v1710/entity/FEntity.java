/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.spigot.v1710.entity;

import java.util.UUID;

import org.bukkit.Location;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.World;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.property.DirtyPropertyMap;
import me.cybermaxke.elementarrows.common.property.PropertyMap;
import me.cybermaxke.elementarrows.spigot.v1710.FElementArrows;
import me.cybermaxke.elementarrows.spigot.v1710.world.FWorld;

public class FEntity<T extends Entity> implements me.cybermaxke.elementarrows.common.entity.Entity {
	public DirtyPropertyMap properties;
	public T entity;

	public FEntity(T entity) {
		this.entity = entity;
	}

	@Override
	public UUID getUniqueId() {
		return this.entity.getUniqueID();
	}

	@Override
	public FWorld getWorld() {
		return FWorld.of(this.entity.world);
	}

	@Override
	public Vector getMotion() {
		return Vectors.of(this.entity.motX, this.entity.motY, this.entity.motZ);
	}

	@Override
	public Vector getPosition() {
		return Vectors.of(this.entity.locX, this.entity.locY, this.entity.locZ);
	}

	@Override
	public boolean isOnFire() {
		return this.entity.isBurning();
	}

	@Override
	public void setFireTicks(int ticks) {
		this.entity.setOnFire(ticks);
	}

	@Override
	public float getYaw() {
		return this.entity.yaw;
	}

	@Override
	public float getPitch() {
		return this.entity.pitch;
	}

	@Override
	public void setMotion(Vector motion) {
		Preconditions.checkNotNull(motion);

		this.entity.motX = motion.getX();
		this.entity.motY = motion.getY();
		this.entity.motZ = motion.getZ();
	}

	@Override
	public void setPosition(Vector position) {
		Preconditions.checkNotNull(position);

		this.entity.setPosition(position.getX(), position.getY(), position.getZ());
	}

	@Override
	public void teleportTo(me.cybermaxke.elementarrows.common.world.World world, Vector position) {
		Preconditions.checkNotNull(world);
		Preconditions.checkNotNull(position);

		World world0 = ((FWorld) world).world;

		this.entity.fallDistance = 0f;
		this.entity.teleportTo(new Location(world0.getWorld(), position.getX(), position.getY(), position.getZ()), false);
	}

	@Override
	public int getTicksLived() {
		return this.entity.ticksLived;
	}

	@Override
	public boolean isOnGround() {
		return this.entity.onGround;
	}

	@Override
	public void setRotation(float yaw, float pitch) {
		this.entity.yaw = yaw;
		this.entity.pitch = pitch;
	}

	@Override
	public PropertyMap getPropertyMap() {
		return this.properties;
	}

	/**
	 * Gets the wrapper for the minecraft entity.
	 * 
	 * @param entity the minecraft entity
	 * @return the wrapper
	 */
	public static <V extends FEntity> V of(Entity entity) {
		return FElementArrows.entities.of(entity);
	}

}