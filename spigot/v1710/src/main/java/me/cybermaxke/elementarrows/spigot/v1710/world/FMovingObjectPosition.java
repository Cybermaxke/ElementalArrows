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
package me.cybermaxke.elementarrows.spigot.v1710.world;

import net.minecraft.server.v1_7_R4.EnumMovingObjectType;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;

import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.source.Source;
import me.cybermaxke.elementarrows.common.source.SourceBlock;
import me.cybermaxke.elementarrows.common.source.SourceEntity;
import me.cybermaxke.elementarrows.common.source.SourceUnknown;
import me.cybermaxke.elementarrows.spigot.v1710.entity.FEntity;

public class FMovingObjectPosition implements me.cybermaxke.elementarrows.common.world.MovingObjectPosition {
	public final MovingObjectPosition mop;
	public final Source source;
	public final Vector hit;
	public final Type type;

	public FMovingObjectPosition(FWorld world, MovingObjectPosition mop) {
		this.hit = Vectors.of(mop.pos.a, mop.pos.b, mop.pos.c);
		this.mop = mop;

		if (mop.type.equals(EnumMovingObjectType.BLOCK)) {
			this.type = Type.Block;

			int x = mop.b;
			int y = mop.c;
			int z = mop.d;

			Vector pos = Vectors.of(x, y, z);
			BlockFace face;

			if (mop.face == 5) {
				face = BlockFace.West;
			} else if (mop.face == 4) {
				face = BlockFace.East;
			} else if (mop.face == 3) {
				face = BlockFace.North;
			} else if (mop.face == 2) {
				face = BlockFace.South;
			} else if (mop.face == 1) {
				face = BlockFace.Down;
			} else if (mop.face == 0) {
				face = BlockFace.Up;
			} else {
				face = BlockFace.Up;
			}

			this.source = new SourceBlock(world, pos, face);
		} else if (mop.type.equals(EnumMovingObjectType.ENTITY)) {
			this.type = Type.Entity;
			this.source = new SourceEntity(FEntity.of(mop.entity));
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