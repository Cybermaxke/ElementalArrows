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
package me.cybermaxke.elementarrows.forge.dispenser;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.SourceDispenser;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrowRegistry;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowBuildEvent;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class DispenseElementArrow extends BehaviorProjectileDispense {
	private final ElementArrowRegistry registry;

	public DispenseElementArrow(ElementArrowRegistry registry) {
		this.registry = registry;
	}

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack itemStack) {
		World world = source.getWorld();

		EntityElementArrow arrow = null;
		ElementArrow arrow0 = this.registry.fromData(itemStack.getItemDamage());

		if (arrow0 != null) {
			SourceDispenser source0 = new SourceDispenser(source);

			ArrowBuildEvent event0 = new ArrowBuildEvent(source0, world, null, 0.55f, 72000);
			arrow0.onArrowBuild(event0);
			arrow = event0.arrow;

			if (arrow == null) {
				return itemStack;
			} else {
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
	    
		return itemStack;
	}

	@Override
	protected IProjectile getProjectileEntity(World world, IPosition position) {
		return null;
	}

}