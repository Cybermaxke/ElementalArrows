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
package me.cybermaxke.elementarrows.forge.v1710.entity;

import java.util.UUID;

import com.google.common.base.Preconditions;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.property.DirtyPropertyHashMap;
import me.cybermaxke.elementarrows.common.property.DirtyPropertyMap;
import me.cybermaxke.elementarrows.common.property.PropertyMap;
import me.cybermaxke.elementarrows.forge.v1710.FProxyCommon;
import me.cybermaxke.elementarrows.forge.v1710.world.FWorld;

public class FEntity<T extends Entity> implements me.cybermaxke.elementarrows.common.entity.Entity {
	public DirtyPropertyMap properties = new DirtyPropertyHashMap();
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
		return FWorld.of(this.entity.worldObj);
	}

	@Override
	public Vector getMotion() {
		return Vectors.of(this.entity.motionX, this.entity.motionY, this.entity.motionZ);
	}

	@Override
	public Vector getPosition() {
		return Vectors.of(this.entity.posX, this.entity.posY, this.entity.posZ);
	}

	@Override
	public boolean isOnFire() {
		return this.entity.isBurning();
	}

	@Override
	public void setFireTicks(int ticks) {
		this.entity.setFire(ticks);
	}

	@Override
	public float getYaw() {
		return this.entity.rotationYaw;
	}

	@Override
	public float getPitch() {
		return this.entity.rotationPitch;
	}

	@Override
	public void setMotion(Vector motion) {
		Preconditions.checkNotNull(motion);

		this.entity.motionX = motion.getX();
		this.entity.motionY = motion.getY();
		this.entity.motionZ = motion.getZ();
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

		if (!world0.isRemote) {
			this.entity.travelToDimension(((WorldServer) world0).provider.dimensionId);
		}

		this.entity.fallDistance = 0f;
		this.entity.setPosition(position.getX(), position.getY(), position.getZ());
	}

	@Override
	public int getTicksLived() {
		return this.entity.ticksExisted;
	}

	@Override
	public boolean isOnGround() {
		return this.entity.onGround;
	}

	@Override
	public void setRotation(float yaw, float pitch) {
		this.entity.rotationYaw = yaw;
		this.entity.rotationPitch = pitch;
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
		return FProxyCommon.entities.of(entity);
	}

}