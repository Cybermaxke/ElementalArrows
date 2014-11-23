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
package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowTorch extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsTorch";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowTorch";
		this.texture = "elementArrows:textures/entity/arrowEntityTorch.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.entity.setFire(100);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		EntityElementArrow arrow = event.arrow;
		World world = arrow.worldObj;

		if (world.isRemote) {
			return;
		}

		arrow.setElementData(0);

		double x0 = arrow.posX;
		double y0 = arrow.posY;
		double z0 = arrow.posZ;

		int bx0 = MathHelper.floor_double(x0);
		int by0 = MathHelper.floor_double(y0);
		int bz0 = MathHelper.floor_double(z0);

		boolean place = Blocks.torch.canPlaceBlockAt(world, bx0, by0, bz0);
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

			Vec3 vec0 = Vec3.createVectorHelper(x1, y1, z1);
			Vec3 vec1 = Vec3.createVectorHelper(x2, y2, z2);

			/**
			 * The temporarily block type and data.
			 */
			Block tempBlock = null;
			int tempData = 0;

			/**
			 * We need to temporarily set the block to air to make it replaceable.
			 */
			if (!world.isAirBlock(bx0, by0, bz0)) {
				tempBlock = world.getBlock(bx0, by0, bz0);
				tempData = world.getBlockMetadata(bx0, by0, bz0);

				world.setBlock(bx0, by0, bz0, Blocks.air, 0, 0);
			}

			MovingObjectPosition mop = world.rayTraceBlocks(vec0, vec1);

			/**
			 * Now, lets reset the replaced data.
			 */
			if (tempBlock != null) {
				world.setBlock(bx0, by0, bz0, tempBlock, tempData, 0);
			}

			if (mop != null && mop.typeOfHit.equals(MovingObjectType.BLOCK)) {
				int bx1 = mop.blockX;
				int by1 = mop.blockY;
				int bz1 = mop.blockZ;

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
					int face = 0;

					if (dx < 0) {
						face = 5;
					} else if (dx > 0) {
						face = 4;
					} else if (dz < 0) {
						face = 3;
					} else if (dz > 0) {
						face = 2;
					} else if (dy < 0) {
						face = 1;
					}

					if (face > 0) {
						int data = Blocks.torch.onBlockPlaced(world, bx0, by0, bz0, face, 0f, 0f, 0f, 0);

						if (data > 0) {
							world.setBlock(bx0, by0, bz0, Blocks.torch, data, 3);
							flag = false;

							if (tempBlock != null) {
								world.playAuxSFX(2001, bx0, by0, bz0, Block.getIdFromBlock(tempBlock) + tempData << 12);
							}
						}
					}
				}
			}
		}

		if (flag) {
			world.spawnEntityInWorld(new EntityItem(world, arrow.posX, arrow.posY, arrow.posZ, new ItemStack(Blocks.torch)));
		}
	}

}