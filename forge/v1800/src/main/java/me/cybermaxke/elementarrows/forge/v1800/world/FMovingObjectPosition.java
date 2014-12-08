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

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.source.Source;
import me.cybermaxke.elementarrows.common.source.SourceBlock;
import me.cybermaxke.elementarrows.common.source.SourceEntity;
import me.cybermaxke.elementarrows.common.source.SourceUnknown;
import me.cybermaxke.elementarrows.forge.v1800.entity.FEntity;

public class FMovingObjectPosition implements me.cybermaxke.elementarrows.common.world.MovingObjectPosition {
	public final MovingObjectPosition mop;
	public final Source source;
	public final Vector hit;
	public final Type type;

	public FMovingObjectPosition(FWorld world, MovingObjectPosition mop) {
		this.hit = Vectors.of(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
		this.mop = mop;

		if (mop.typeOfHit.equals(MovingObjectType.BLOCK)) {
			this.type = Type.Block;

			EnumFacing facing = mop.sideHit;
			BlockPos bpos = mop.getBlockPos();

			int x = bpos.getX();
			int y = bpos.getY();
			int z = bpos.getZ();

			Vector pos = Vectors.of(x, y, z);
			BlockFace face;

			int rx = facing.getFrontOffsetX();
			int ry = facing.getFrontOffsetY();
			int rz = facing.getFrontOffsetZ();

			if (rx < 0) {
				face = BlockFace.West;
			} else if (rx > 0) {
				face = BlockFace.East;
			} else if (rz < 0) {
				face = BlockFace.North;
			} else if (rz > 0) {
				face = BlockFace.South;
			} else if (ry < 0) {
				face = BlockFace.Down;
			} else if (ry > 0) {
				face = BlockFace.Up;
			} else {
				face = BlockFace.Up;
			}

			this.source = new SourceBlock(world, pos, face);
		} else if (mop.typeOfHit.equals(MovingObjectType.ENTITY)) {
			this.type = Type.Entity;
			this.source = new SourceEntity(FEntity.of(mop.entityHit));
		} else {
			this.type = Type.Miss;
			this.source = new SourceUnknown();
		}
	}

	@Override
	public Source getHit() {
		return this.source;
	}

	@Override
	public Vector getPoint() {
		return this.hit;
	}

	@Override
	public Type getType() {
		return this.type;
	}

}