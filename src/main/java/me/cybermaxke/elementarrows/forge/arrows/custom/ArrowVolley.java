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

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowVolley extends ElementArrow {
	private final Random random = new Random();

	@JsonField("amount")
	private int amount = 7;

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsVolley";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowVolley";
		this.effect = true;
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		this.onArrowHit(event.arrow, event.entity);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		this.onArrowHit(event.arrow, event.arrow);
	}

	public void onArrowHit(EntityElementArrow arrow, Entity target) {
		double x = target.posX;
		double y = target.posY;
		double z = target.posZ;

		Vec3 vec = Vec3.createVectorHelper(x, y, z);

		for (int i = 0; i < this.amount; i++) {
			double x0 = x + (this.random.nextFloat() - 0.5f) * 4f;
			double z0 = z + (this.random.nextFloat() - 0.5f) * 4f;
			double y0 = y + 6f;

			double x1 = x + (this.random.nextFloat() - 0.5f) * 2.5f;
			double z1 = z + (this.random.nextFloat() - 0.5f) * 2.5f;
			double y1 = y;

			Vec3 vec0 = Vec3.createVectorHelper(x0, y0, z0);
			MovingObjectPosition mop = arrow.worldObj.rayTraceBlocks(vec, vec0, true);

			if (mop != null && !mop.typeOfHit.equals(MovingObjectType.ENTITY)) {
				if (arrow.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ).getMaterial().isSolid()) {
					x0 = mop.hitVec.xCoord;
					y0 = mop.hitVec.yCoord;
					z0 = mop.hitVec.zCoord;
				}
			}

			double mx = x1 - x0;
			double my = y1 - y0;
			double mz = z1 - z0;

			EntityElementArrow arrow0 = new EntityElementArrow(arrow.worldObj, x0, y0, z0);
			arrow0.setThrowableHeading(mx, my, mz, 1.3f, 1f);
			arrow0.shootingEntity = arrow.shootingEntity;
			arrow0.worldObj.spawnEntityInWorld(arrow0);
		}

		arrow.setElementData(0);
	}

}