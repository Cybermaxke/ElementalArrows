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
package me.cybermaxke.elementarrows.spigot.v1800.block;

import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.EnumDirection;
import net.minecraft.server.v1_8_R1.IBlockData;
import net.minecraft.server.v1_8_R1.MinecraftKey;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.item.type.ItemType;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.world.World;
import me.cybermaxke.elementarrows.spigot.v1800.FElementArrows;
import me.cybermaxke.elementarrows.spigot.v1800.world.FWorld;

public class FBlockType implements BlockType {
	public final Block block;
	public final String id;
	public final int internalId;

	public FBlockType(Block block) {
		this.internalId = Block.REGISTRY.b(block);
		this.id = ((MinecraftKey) Block.REGISTRY.c(block)).toString();
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
		return FElementArrows.items.typeById(this.internalId);
	}

	@Override
	public boolean isPlaceableAt(World world, Vector position) {
		Preconditions.checkNotNull(world);
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return this.block.canPlace(((FWorld) world).world, new BlockPosition(x, y, z));
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

		EnumDirection facing0 = convert(facing);
		BlockPosition pos = new BlockPosition(x, y, z);
		IBlockData state = this.block.getPlacedState(((FWorld) world).world, pos, facing0, fx, fy, fz, 0, null);

		return Block.d.b(state) & 0xf;
	}

	static EnumDirection convert(BlockFace facing) {
		switch (facing) {
			case Down:
				return EnumDirection.DOWN;
			case East:
				return EnumDirection.EAST;
			case North:
				return EnumDirection.NORTH;
			case South:
				return EnumDirection.SOUTH;
			case Up:
				return EnumDirection.UP;
			case West:
				return EnumDirection.WEST;
			default:
				return null;
		}
	}

}