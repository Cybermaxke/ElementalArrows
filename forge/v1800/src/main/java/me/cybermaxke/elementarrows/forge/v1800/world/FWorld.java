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
package me.cybermaxke.elementarrows.forge.v1800.world;

import java.util.Random;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.forge.v1800.FProxyCommon;
import me.cybermaxke.elementarrows.forge.v1800.block.FBlockType;
import me.cybermaxke.elementarrows.forge.v1800.entity.FEntity;

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

		return this.world.isAirBlock(new BlockPos(x, y, z));
	}

	@Override
	public BlockType getBlockType(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return FProxyCommon.blocks.of(this.world.getBlockState(new BlockPos(x, y, z)).getBlock());
	}

	@Override
	public void setBlock(Vector position, BlockType type, int data, int flags) {
		Preconditions.checkNotNull(position);
		Preconditions.checkNotNull(type);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		int id = ((FBlockType) type).internalId;

		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = (IBlockState) Block.BLOCK_STATE_IDS.getByValue(id << 4 | data);

		this.world.setBlockState(pos, state, flags);
	}

	@Override
	public void setBlock(Vector position, BlockType type, int data) {
		this.setBlock(position, type, data, me.cybermaxke.elementarrows.common.world.World.All);
	}

	@Override
	public int getBlockData(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return Block.BLOCK_STATE_IDS.get(this.world.getBlockState(new BlockPos(x, y, z))) & 0xf;
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

		Vec3 start0 = new Vec3(start.getX(), start.getY(), start.getZ());
		Vec3 end0 = new Vec3(end.getX(), end.getY(), end.getZ());

		MovingObjectPosition mop = this.world.rayTraceBlocks(start0, end0, liquid, !nonCollidable, false);

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