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

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.recipe.RecipeShapeless;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowEnderEye extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsEnderEye";

		/**
		 * Add the default recipe.
		 */
		event.recipes.addDefault(RecipeShapeless.builder()
				.withResult(new ItemStack(event.itemArrow, 1, event.data))
				.withIngredient(new ItemStack(Items.ender_eye, 1, 0))
				.withIngredient(new ItemStack(event.itemArrow, 1, 0))
				.build());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowEnderEye";
		this.texture = "elementArrows:textures/entity/arrowEntityEnderEye.png";
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

		EffectRenderer render = Minecraft.getMinecraft().effectRenderer;

		for (int i = 0; i < 4; i++) {
			render.addEffect(new FXArrowEnderEye(arrow.worldObj, x + mx * i / 4d, y + my * i / 4d, z + mz * i / 4d, -mx * 0.8d, -my * 0.8d, -mz * 0.8d));
		}
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		this.onArrowHit(event.arrow);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		this.onArrowHit(event.arrow);
	}

	public void onArrowHit(EntityElementArrow arrow) {
		Entity shooter = arrow.shootingEntity;

		if (shooter == null) {
			return;
		}

		if (shooter.isRiding()) {
			shooter.mountEntity(null);
		}

		shooter.setPosition(arrow.posX, arrow.posY, arrow.posZ);
		shooter.fallDistance = 0f;

		if (shooter instanceof EntityPlayerMP) {
			((EntityPlayerMP) shooter).setPositionAndUpdate(arrow.posX, arrow.posY, arrow.posZ);
		}

		arrow.setElementData(0);

		if (shooter.worldObj.isRemote) {
			Random random = shooter.worldObj.rand;
			AxisAlignedBB aabb = shooter.boundingBox;

			double x = (aabb.maxX + aabb.minX) * 0.5d;
			double y = (aabb.maxY + aabb.minY) * 0.5d;
			double z = (aabb.maxZ + aabb.minZ) * 0.5d;

			for (int i = 0; i < 35; i++) {
				double x0 = x + (random.nextFloat() - 0.5d);
				double y0 = y + (random.nextFloat() * shooter.height - 0.5d);
				double z0 = z + (random.nextFloat() - 0.5d);

				double mx = (random.nextFloat() - 0.5d) * 0.2d;
				double my = (random.nextFloat() - 0.5d) * 0.2d;
				double mz = (random.nextFloat() - 0.5d) * 0.2d;

				shooter.worldObj.spawnParticle("portal", x0, y0, z0, mx, my, mz);
		    }
		}
	}

	@SideOnly(Side.CLIENT)
	public static class FXArrowEnderEye extends EntityCritFX {

		public FXArrowEnderEye(World world, double x, double y, double z, double mx, double my, double mz) {
			super(world, x, y, z, mx, my, mz);

			float f = this.rand.nextFloat() * 0.6f + 0.4f;

			this.particleScale = this.rand.nextFloat() * 0.2f + 0.5f;
			this.particleBlue = f;
			this.particleGreen = f * 0.3f;
			this.particleRed = f * 0.9f;

			this.setParticleTextureIndex((int) (Math.random() * 8d));
		}

	}
}