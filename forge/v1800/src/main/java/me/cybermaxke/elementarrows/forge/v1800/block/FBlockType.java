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
package me.cybermaxke.elementarrows.forge.v1800.block;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.item.type.ItemType;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.world.World;
import me.cybermaxke.elementarrows.forge.v1800.FProxyCommon;
import me.cybermaxke.elementarrows.forge.v1800.world.FWorld;

public class FBlockType implements BlockType {
	public final Block block;
	public final String id;
	public final int internalId;

	public FBlockType(Block block) {
		this.internalId = Block.blockRegistry.getIDForObject(block);
		this.id = (String) Block.blockRegistry.getNameForObject(block);
		this.block = block;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getInternalId() {
		return this.internalId;
	}

	@Override
	public ItemType getItem() {
		return FProxyCommon.items.typeById(this.internalId);
	}

	@Override
	public boolean isPlaceableAt(World world, Vector position) {
		Preconditions.checkNotNull(world);
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return this.block.canPlaceBlockAt(((FWorld) world).world, new BlockPos(x, y, z));
	}

	@Override
	public int getDataForRotationAndPosition(World world, Vector position, BlockFace facing) {
		Preconditions.checkNotNull(world);
		Preconditions.checkNotNull(position);
		Preconditions.checkNotNull(facing);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		float fx = (float) (position.getX() - x);
		float fy = (float) (position.getY() - y);
		float fz = (float) (position.getZ() - z);

		Vector dir = facing.getDirection();

		float dx = (float) dir.getX();
		float dy = (float) dir.getY();
		float dz = (float) dir.getZ();

		EnumFacing face = EnumFacing.getFacingFromVector(dx, dy, dz);
		IBlockState state = this.block.onBlockPlaced(((FWorld) world).world, new BlockPos(x, y, z), face, fx, fy, fz, 0, null);

		return this.block.getMetaFromState(state);
	}

}