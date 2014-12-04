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
package me.cybermaxke.elementarrows.common.arrow.custom;

import me.cybermaxke.elementarrows.common.arrow.ElementArrowBase;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitGround;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.block.BlockFace;
import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.block.Blocks;
import me.cybermaxke.elementarrows.common.entity.Entities;
import me.cybermaxke.elementarrows.common.entity.EntityArrow;
import me.cybermaxke.elementarrows.common.entity.EntityItem;
import me.cybermaxke.elementarrows.common.item.Items;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.source.SourceBlock;
import me.cybermaxke.elementarrows.common.world.MovingObjectPosition;
import me.cybermaxke.elementarrows.common.world.World;

public class ArrowTorch extends ElementArrowBase {
	private final BlockType torchType = Blocks.find("minecraft:torch");
	private final BlockType airType = Blocks.find("minecraft:air");

	@Override
	public void handle(EventInitialize event) {
		/**
		 * The unlocalized path.
		 */
		this.unlocalizedName = "elementArrowsTorch";

		/**
		 * Setup client settings.
		 */
		this.icon = "elementArrows:arrowTorch.png";
		this.texture = "elementArrows:arrowEntityTorch.png";
	}

	@Override
	public void handle(EventEntityHitEntity event) {
		event.getDamageSource().getDamaged().setFireTicks(60);
	}

	@Override
	public void handle(EventEntityHitGround event) {
		EntityArrow arrow = event.getEntity();
		World world = arrow.getWorld();
		Vector pos = arrow.getPosition();

		arrow.setElementData(0);

		double x0 = pos.getX();
		double y0 = pos.getY();
		double z0 = pos.getZ();

		int bx0 = pos.getBlockX();
		int by0 = pos.getBlockY();
		int bz0 = pos.getBlockZ();

		boolean place = this.torchType.isPlaceableAt(world, pos);
		boolean flag = true;

		if (place) {
			double x1 = (double) bx0 + 0.5d;
			double y1 = (double) by0 + 0.5d;
			double z1 = (double) bz0 + 0.5d;

			double x2 = x0 - x1;
			double y2 = y0 - y1;
			double z2 = z0 - z1;

			double d = Math.sqrt(x2 * x2 + y2 * y2 + z2 * z2);

			x2 /= d;
			y2 /= d;
			z2 /= d;

			x2 = x1 + x2 * 10d;
			y2 = y1 + y2 * 10d;
			z2 = z1 + z2 * 10d;

			Vector vec0 = Vectors.of(x1, y1, z1);
			Vector vec1 = Vectors.of(x2, y2, z2);

			/**
			 * The temporarily block type and data.
			 */
			BlockType tempBlock = null;
			int tempData = 0;

			/**
			 * We need to temporarily set the block to air to make it replaceable.
			 */
			if (!world.isAir(pos)) {
				tempBlock = world.getBlockType(pos);
				tempData = world.getBlockData(pos);

				world.setBlock(pos, this.airType, 0, World.None);
			}

			MovingObjectPosition mop = world.rayTrace(vec0, vec1);

			/**
			 * Now, lets reset the replaced data.
			 */
			if (tempBlock != null) {
				world.setBlock(pos, tempBlock, tempData, 0);
			}

			if (mop != null && mop.getType().equals(MovingObjectPosition.Type.Block)) {
				SourceBlock source = (SourceBlock) mop.getHit();
				Vector pos1 = source.getPosition();
				
				int bx1 = pos1.getBlockX();
				int by1 = pos1.getBlockY();
				int bz1 = pos1.getBlockZ();

				int dx = bx1 - bx0;
				int dy = by1 - by0;
				int dz = bz1 - bz0;

				int i = 0;
				if (dx != 0) {
					i++;
				}
				if (dy != 0) {
					i++;
				}
				if (dz != 0) {
					i++;
				}

				if (i == 1) {
					BlockFace face = null;

					if (dx < 0) {
						face = BlockFace.North;
					} else if (dx > 0) {
						face = BlockFace.South;
					} else if (dz < 0) {
						face = BlockFace.West;
					} else if (dz > 0) {
						face = BlockFace.East;
					} else if (dy < 0) {
						face = BlockFace.Down;
					}
					
					if (face != null) {
						int data = this.torchType.getDataForRotationAndPosition(world, pos, face.getOpposite());

						if (data > 0) {
							world.setBlock(pos, this.torchType, data, 3);
							flag = false;
						}
					}
				}
			}
		}

		if (flag) {
			EntityItem entity = Entities.create(EntityItem.class, world, pos);
			entity.setItem(Items.of(this.torchType.getItem()));

			world.addEntity(entity);
		}
	}

}