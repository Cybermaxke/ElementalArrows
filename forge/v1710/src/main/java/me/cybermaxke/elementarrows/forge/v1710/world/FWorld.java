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
package me.cybermaxke.elementarrows.forge.v1710.world;

import java.util.Random;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.forge.v1710.FProxyCommon;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntity;

public class FWorld implements me.cybermaxke.elementarrows.common.world.World {
	public final FWorldLightning lightning = new FWorldLightning();

	/**
	 * Weak reference to track if the world is unloaded.
	 */
	public World world;

	public FWorld(World world) {
		this.world = world;
	}

	@Override
	public void spawnLightning(Vector position, boolean placeFire) {
		Preconditions.checkNotNull(position);

		if (this.world.isRemote) {
			return;
		}

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();

		this.lightning.spawnLightningAt(this.world, x, y, z, placeFire);
	}

	@Override
	public void spawnLightning(Vector position) {
		this.spawnLightning(position, true);
	}

	@Override
	public boolean isAir(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return this.world.isAirBlock(x, y, z);
	}

	@Override
	public String getBlockType(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return Block.blockRegistry.getNameForObject(this.world.getBlock(x, y, z));
	}

	@Override
	public void setBlock(Vector position, String type, int data, int flags) {
		Preconditions.checkNotNull(position);
		Preconditions.checkNotNull(type);

		Block block = (Block) Block.blockRegistry.getObject(type);

		if (block ==  null) {
			throw new IllegalArgumentException("Unknown block type! (" + type + ")");
		}

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		this.world.setBlock(x, y, z, block, data, flags);
	}

	@Override
	public void setBlock(Vector position, String type, int data) {
		this.setBlock(position, type, data, me.cybermaxke.elementarrows.common.world.World.All);
	}

	@Override
	public void addEntity(me.cybermaxke.elementarrows.common.entity.Entity entity) {
		Preconditions.checkNotNull(entity);

		if (!this.world.isRemote) {
			this.world.spawnEntityInWorld(((FEntity<?>) entity).entity);
		}
	}

	@Override
	public String getName() {
		return FWorldManager.getFixedName(this.world);
	}

	@Override
	public boolean isRemote() {
		return this.world.isRemote;
	}

	@Override
	public Random getRandom() {
		return this.world.rand;
	}

	@Override
	public void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks, me.cybermaxke.elementarrows.common.entity.Entity source) {
		Preconditions.checkNotNull(position);

		if (this.world.isRemote) {
			return;
		}

		Entity source0 = source == null ? null : ((FEntity) source).entity;

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();

		this.world.newExplosion(source0, x, y, z, power, placeFire, destroyBlocks);
	}

	@Override
	public void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks) {
		this.createExplosion(position, power, placeFire, destroyBlocks, null);
	}

	@Override
	public FMovingObjectPosition rayTrace(Vector start, Vector end) {
		return this.rayTrace(start, end, false);
	}

	@Override
	public FMovingObjectPosition rayTrace(Vector start, Vector end, boolean liquid) {
		return this.rayTrace(start, end, liquid, true);
	}

	@Override
	public FMovingObjectPosition rayTrace(Vector start, Vector end, boolean liquid, boolean nonCollidable) {
		Preconditions.checkNotNull(start);
		Preconditions.checkNotNull(end);

		Vec3 start0 = Vec3.createVectorHelper(start.getX(), start.getY(), start.getZ());
		Vec3 end0 = Vec3.createVectorHelper(end.getX(), end.getY(), end.getZ());

		MovingObjectPosition mop = this.world.func_147447_a(start0, end0, liquid, !nonCollidable, false);

		if (mop != null) {
			return new FMovingObjectPosition(this, mop);
		} else {
			return null;
		}
	}

	/**
	 * Gets the forge world for the minecraft world.
	 * 
	 * @param world the minecraft world
	 * @return the world
	 */
	public static FWorld of(World world) {
		return FProxyCommon.worlds.of(world);
	}

}