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
package me.cybermaxke.elementarrows.forge.v1710.dispenser;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.source.SourceBlock;
import me.cybermaxke.elementarrows.common.source.SourceBlock.Face;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityArrow;
import me.cybermaxke.elementarrows.forge.v1710.world.FWorld;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DispenseElementArrow extends BehaviorProjectileDispense {

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack itemStack) {
		World world = source.getWorld();
		FWorld wrapper0 = FWorld.of(world);

		EntityElementArrow arrow = null;
		FEntityArrow wrapper1 = null;

		ElementArrow arrow0 = Arrows.find(itemStack.getItemDamage());

		if (arrow0 != null) {
			IPosition pos = BlockDispenser.func_149939_a(source);
			EnumFacing face = BlockDispenser.func_149937_b(source.getBlockMetadata());

			Vector pos0 = Vectors.of(pos.getX(), pos.getY(), pos.getZ());
			Face face0 = this.convert(face);

			SourceBlock source0 = new SourceBlock(wrapper0, pos0, face0);

			EventEntityBuild event0 = new EventEntityBuild(source0, 1f, 0.55f);
			arrow0.handle(event0);

			wrapper1 = (FEntityArrow) event0.getEntity();

			if (wrapper1 == null) {
				return itemStack;
			} else {
				arrow = (EntityElementArrow) wrapper1.entity;
				arrow.setElementData(itemStack.getItemDamage());
				arrow.source = source0;
			}
		} else {
			IPosition pos = BlockDispenser.func_149939_a(source);
			EnumFacing face = BlockDispenser.func_149937_b(source.getBlockMetadata());

			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();

			double tx = face.getFrontOffsetX();
			double ty = face.getFrontOffsetY();
			double tz = face.getFrontOffsetZ();

			arrow = new EntityElementArrow(world, x, y, z);
			arrow.setThrowableHeading(tx, ty, tz, 1.1f, 6f);
		}

		world.spawnEntityInWorld(arrow);
		itemStack.splitStack(1);

		if (arrow0 != null) {
			EventEntityShot event0 = new EventEntityShot(wrapper1, arrow.source, 1f, 0.55f);
			arrow0.handle(event0);
		}

		return itemStack;
	}

	@Override
	protected IProjectile getProjectileEntity(World world, IPosition position) {
		return null;
	}

	Face convert(EnumFacing face) {
		switch (face) {
			case DOWN:
				return Face.Down;
			case EAST:
				return Face.East;
			case NORTH:
				return Face.North;
			case SOUTH:
				return Face.South;
			case UP:
				return Face.Up;
			case WEST:
				return Face.West;
			default:
				return null;
		}
	}

}