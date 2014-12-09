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
package me.cybermaxke.elementarrows.forge.v1800.render;

import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.opengl.GL11;

import me.cybermaxke.elementarrows.forge.v1800.entity.FEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityIce implements RenderCallback {
	private final ResourceLocation iceTexture = new ResourceLocation("minecraft:textures/blocks/ice.png");
	private final ModelGeneratorResized generator = new ModelGeneratorResized(2f);
	private final Map<Entity, EntityInfo> cache = new WeakHashMap<Entity, EntityInfo>();

	@Override
	public void renderEntity(Render render, Entity entity, double x, double y, double z, float arg1, float arg2) {

	}

	@Override
	public void renderModel(Render render, Entity entity, ModelBase model, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6) {
		FEntity entity0 = FEntity.of(entity);

		if (!entity0.properties.get(me.cybermaxke.elementarrows.common.entity.Entity.Frozen)) {
			return;
		}

		EntityInfo info = new EntityInfo();
		info.model = model;
		info.arg1 = arg1;
		info.arg2 = arg2;
		info.arg3 = arg3;
		info.arg4 = arg4;
		info.arg5 = arg5;
		info.arg6 = arg6;

		this.cache.put(entity, info);
	}

	@Override
	public void renderLayers(Render render, Entity entity, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7) {
		EntityInfo info = this.cache.remove(entity);

		if (info == null) {
			return;
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(this.iceTexture);
		ModelBase model0 = this.generator.get(info.model);

		/**
		 * Blend the alpha values of the ice.
		 */
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		/**
		 * Render the model.
		 */
		model0.render(entity, info.arg1, info.arg2, info.arg3, info.arg4, info.arg5, info.arg6);

		GL11.glDisable(GL11.GL_BLEND);
	}

	static class EntityInfo {
		private ModelBase model;

		private float arg1;
		private float arg2;
		private float arg3;
		private float arg4;
		private float arg5;
		private float arg6;
	}

}