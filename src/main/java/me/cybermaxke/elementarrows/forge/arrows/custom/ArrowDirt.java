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

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDirt extends ElementArrow {

	@JsonField("damageMultiplier")
	private double damageMultiplier = 1.3f;

	@JsonField("knockbackStrength")
	private int knockbackStrength = 2;

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDirt";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDirt";
		this.texture = "elementArrows:textures/entity/arrowEntityDirt.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onArrowClientTick(ArrowTickEvent event) {
		EntityElementArrow arrow = event.arrow;

		double mx = arrow.lastMotX;
		double my = arrow.lastMotY;
		double mz = arrow.lastMotZ;

		double x = arrow.prevPosX;
		double y = arrow.prevPosY;
		double z = arrow.prevPosZ;

		String name = "blockdust_" + Block.getIdFromBlock(Blocks.dirt) + "_0";

		for (int i = 0; i < 4; i++) {
			arrow.worldObj.spawnParticle(name, x + mx * i / 4d, y + my * i / 4d, z + mz * i / 4d, -mx * 0.8d, -my * 0.8d, -mz * 0.8d);
		}
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setDamage(event.arrow.getDamage() * this.damageMultiplier);
		event.arrow.setKnockbackStrength(this.knockbackStrength);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		this.onHit(event.arrow);
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		this.onHit(event.arrow);
	}

	public void onHit(EntityElementArrow arrow) {
		arrow.setElementData(0);

		if (arrow.worldObj.isRemote) {
			Random random = arrow.worldObj.rand;

			double x = arrow.posX;
			double y = arrow.posY;
			double z = arrow.posZ;

			String name = "blockdust_" + Block.getIdFromBlock(Blocks.dirt) + "_0";

			for (int i = 0; i < 15; i++) {
				double mx = (random.nextFloat() - 0.5d) * 0.5d;
				double my = (Math.abs(random.nextFloat() - 0.5d)) * 0.5d;
				double mz = (random.nextFloat() - 0.5d) * 0.5d;

				arrow.worldObj.spawnParticle(name, x, y, z, mx, my, mz);
			}
		}
	}

}