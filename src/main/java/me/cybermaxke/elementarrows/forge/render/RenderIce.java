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
package me.cybermaxke.elementarrows.forge.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public final class RenderIce extends Render {
	private final RenderBlocks renderBlocks = new RenderBlocks();

	@Override
	public void doRender(Entity arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {
		AxisAlignedBB aabb = arg0.boundingBox;

		float sizeX = (float) (aabb.maxX - aabb.minX) + 0.6f;
		float sizeY = (float) (aabb.maxY - aabb.minY) + 0.6f;
		float sizeZ = (float) (aabb.maxZ - aabb.minZ) + 0.6f;

		arg2 += sizeY / 2;

		int i = MathHelper.floor_double(arg0.posX);
		int j = MathHelper.floor_double(arg0.posY);
		int k = MathHelper.floor_double(arg0.posZ);

		GL11.glPushMatrix();
		GL11.glTranslatef((float) arg1, (float) arg2, (float) arg3);
		GL11.glScalef(sizeX, sizeY, sizeZ);

		this.bindEntityTexture(arg0);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		this.renderBlocks.setRenderBounds(0d, 0d, 0d, 1d, 1d, 1d);
		this.renderBlocks.renderBlockSandFalling(Blocks.ice, arg0.worldObj, i, j, k, 0);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity arg0) {
		return TextureMap.locationBlocksTexture;
	}

}