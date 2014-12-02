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
package me.cybermaxke.elementarrows.spigot.v1710.dispenser;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

import net.minecraft.server.v1_7_R4.BlockDispenser;
import net.minecraft.server.v1_7_R4.DispenseBehaviorProjectile;
import net.minecraft.server.v1_7_R4.EnumFacing;
import net.minecraft.server.v1_7_R4.IDispenseBehavior;
import net.minecraft.server.v1_7_R4.IPosition;
import net.minecraft.server.v1_7_R4.IProjectile;
import net.minecraft.server.v1_7_R4.ISourceBlock;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.World;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.source.SourceBlock;
import me.cybermaxke.elementarrows.common.source.SourceBlock.Face;
import me.cybermaxke.elementarrows.spigot.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.spigot.v1710.entity.FEntityArrow;
import me.cybermaxke.elementarrows.spigot.v1710.world.FWorld;

public class DispenseElementArrow extends DispenseBehaviorProjectile {

	@Override
	public ItemStack b(ISourceBlock source, ItemStack itemStack) {
		World world = source.getTileEntity().getWorld();
		FWorld wrapper0 = FWorld.of(world);

		EntityElementArrow arrow = null;
		FEntityArrow wrapper1 = null;

		ElementArrow arrow0 = Arrows.find(itemStack.getData());

		itemStack.a(1);

		if (arrow0 != null) {
			IPosition pos = BlockDispenser.a(source);
			EnumFacing face = BlockDispenser.b(source.h());

			me.cybermaxke.elementarrows.common.math.Vector pos0 = Vectors.of(pos.getX(), pos.getY(), pos.getZ());
			Face face0 = this.convert(face);

			SourceBlock source0 = new SourceBlock(wrapper0, pos0, face0);

			EventEntityBuild event0 = new EventEntityBuild(source0, 1f, 0.55f);
			arrow0.handle(event0);

			wrapper1 = (FEntityArrow) event0.getEntity();

			if (wrapper1 == null) {
				return itemStack;
			} else {
				arrow = (EntityElementArrow) wrapper1.entity;
				arrow.setElementData(itemStack.getData());
				arrow.source = source0;
			}
		} else {
			IPosition pos = BlockDispenser.a(source);
			EnumFacing face = BlockDispenser.b(source.h());

			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();

			double tx = face.getAdjacentX();
			double ty = face.getAdjacentY() + 0.1f;
			double tz = face.getAdjacentZ();

			arrow = new EntityElementArrow(world, x, y, z);
			arrow.shoot(tx, ty, tz, 1.1f, 6f);
		}

		CraftItemStack itemStack0 = CraftItemStack.asCraftMirror(itemStack).clone();
	    Block block = world.getWorld().getBlockAt(source.getBlockX(), source.getBlockY(), source.getBlockZ());
		BlockDispenseEvent event = new BlockDispenseEvent(block, itemStack0, new Vector(arrow.motX, arrow.motY, arrow.motZ));

		if (!BlockDispenser.eventFired) {
			world.getServer().getPluginManager().callEvent(event);
		}

		if (event.isCancelled()) {
			itemStack.count += 1;
			return itemStack;
		}

		if (!event.getItem().equals(itemStack)) {
			itemStack.count += 1;
	      
			ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
			IDispenseBehavior behavior = (IDispenseBehavior) BlockDispenser.a.get(eventStack.getItem());

			if (behavior != IDispenseBehavior.a && behavior != this) {
				behavior.a(source, eventStack);
				return itemStack;
			}
		}

		Vector motion = event.getVelocity();

		double mx = motion.getX();
		double my = motion.getY();
		double mz = motion.getZ();

		if (mx != arrow.motY || mx != arrow.motX || mx != arrow.motZ) {
			arrow.shoot(mx, my, mz, 1.1f, 6f);
		}

		world.addEntity(arrow);

		if (arrow0 != null) {
			EventEntityShot event0 = new EventEntityShot(wrapper1, arrow.source, 1f, 0.55f);
			arrow0.handle(event0);
		}
	    
		return itemStack;
	}

	@Override
	protected IProjectile a(World world, IPosition position) {
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