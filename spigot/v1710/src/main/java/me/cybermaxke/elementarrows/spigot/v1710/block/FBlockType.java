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
package me.cybermaxke.elementarrows.spigot.v1710.block;

import net.minecraft.server.v1_7_R4.Block;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.item.type.ItemType;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.world.World;
import me.cybermaxke.elementarrows.spigot.v1710.FElementArrows;
import me.cybermaxke.elementarrows.spigot.v1710.world.FWorld;

public class FBlockType implements BlockType {
	public final Block block;
	public final String id;
	public final int internalId;

	public FBlockType(Block block) {
		this.internalId = Block.REGISTRY.b(block);
		this.id = Block.REGISTRY.c(block);
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

		return this.block.canPlace(((FWorld) world).world, x, y, z);
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

		int face;

		if (facing == BlockFace.Down) {
			face = 0;
		} else if (facing == BlockFace.Up) {
			face = 1;
		} else if (facing == BlockFace.North) {
			face = 5;
		} else if (facing == BlockFace.South) {
			face = 4;
		} else if (facing == BlockFace.West) {
			face = 3;
		} else if (facing == BlockFace.East) {
			face = 2;
		} else {
			face = 0;
		}

		return this.block.getPlacedData(((FWorld) world).world, x, y, z, face, fx, fy, fz, 0);
	}

}