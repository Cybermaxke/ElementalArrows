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
package me.cybermaxke.elementarrows.spigot.v1800.world;

import java.util.Random;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.MovingObjectPosition;
import net.minecraft.server.v1_8_R1.Vec3D;
import net.minecraft.server.v1_8_R1.World;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.spigot.v1800.FElementArrows;
import me.cybermaxke.elementarrows.spigot.v1800.block.FBlockType;
import me.cybermaxke.elementarrows.spigot.v1800.entity.FEntity;

public class FWorld implements me.cybermaxke.elementarrows.common.world.World {
	public final FWorldLightning lightning = new FWorldLightning();

	/**
	 * Weak reference to track if the world is unloaded.
	 */
	public World world;

	/**
	 * The name of the world.
	 */
	public String name;

	public FWorld(World world) {
		this.world = world;
		this.name = world.getWorld().getName();
	}

	@Override
	public void spawnLightning(Vector position, boolean placeFire) {
		Preconditions.checkNotNull(position);

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

		return this.world.isEmpty(new BlockPosition(x, y, z));
	}

	@Override
	public FBlockType getBlockType(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return FElementArrows.blocks.of(this.world.c(new BlockPosition(x, y, z)));
	}

	@Override
	public int getBlockData(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		BlockPosition pos = new BlockPosition(x, y, z);
		IBlockData state = this.world.getType(pos);

		return Block.d.b(state) & 0xf;
	}

	@Override
	public void setBlock(Vector position, BlockType type, int data, int flags) {
		Preconditions.checkNotNull(position);
		Preconditions.checkNotNull(type);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		int id = ((FBlockType) type).internalId;

		BlockPosition pos = new BlockPosition(x, y, z);
		IBlockData state = (IBlockData) Block.d.a(id << 4 | data);

		this.world.setTypeAndData(pos, state, flags);
	}

	@Override
	public void setBlock(Vector position, BlockType type, int data) {
		this.setBlock(position, type, data, me.cybermaxke.elementarrows.common.world.World.All);
	}

	@Override
	public void addEntity(me.cybermaxke.elementarrows.common.entity.Entity entity) {
		Preconditions.checkNotNull(entity);

		this.world.addEntity(((FEntity<?>) entity).entity);
	}

	@Override
	public String getName() {
		return this.world.getWorld().getName();
	}

	@Override
	public boolean isRemote() {
		return this.world.isStatic;
	}

	@Override
	public Random getRandom() {
		return this.world.random;
	}

	@Override
	public void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks, me.cybermaxke.elementarrows.common.entity.Entity source) {
		Preconditions.checkNotNull(position);
		Entity source0 = source == null ? null : ((FEntity) source).entity;

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();

		this.world.createExplosion(source0, x, y, z, power, placeFire, destroyBlocks);
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

		Vec3D start0 = new Vec3D(start.getX(), start.getY(), start.getZ());
		Vec3D end0 = new Vec3D(end.getX(), end.getY(), end.getZ());

		MovingObjectPosition mop = this.world.rayTrace(start0, end0, liquid, !nonCollidable, false);

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
		return FElementArrows.worlds.of(world);
	}

}