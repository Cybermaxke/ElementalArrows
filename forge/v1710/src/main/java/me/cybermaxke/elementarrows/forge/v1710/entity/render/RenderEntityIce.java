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
package me.cybermaxke.elementarrows.forge.v1710.entity.render;

import java.util.HashMap;
import java.util.Map;

import me.cybermaxke.elementarrows.common.entity.Entity;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntity;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityLiving;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityIce {
	private Map<TextureManager, TextureManager> callbacks = new HashMap<TextureManager, TextureManager>();

	/**
	 * Initialize the ice renderer.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
		if (event.isCanceled()) {
			return;
		}
		this.onPreRender(event.renderer, event.entityLiving, event.entity.posX, event.entity.posY, event.entity.posZ);
	}

	@SubscribeEvent
	public void onRenderLivingPre(RenderLivingEvent.Pre event) {
		if (event.isCanceled()) {
			return;
		}
		this.onPreRender(event.renderer, event.entity, event.x, event.y, event.z);
	}

	@SubscribeEvent
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
		this.onPreRender(event.renderer, event.entityLiving, event.entity.posX, event.entity.posY, event.entity.posZ);
	}

	@SubscribeEvent
	public void onRenderLivingPost(RenderLivingEvent.Post event) {
		this.onPreRender(event.renderer, event.entity, event.x, event.y, event.z);
	}

	void onPreRender(Render render, EntityLivingBase entity, double x, double y, double z) {
		FEntityLiving<?> entity0 = FEntity.of(entity);

		if (entity0.properties.get(Entity.Frozen)) {
			RenderManager manager = RenderManager.instance;
			TextureManager textures = manager.renderEngine;

			if (this.callbacks.containsKey(textures)) {
				textures = this.callbacks.get(textures);
			} else {
				textures = new TextureManagerIce(textures);
				this.callbacks.put(manager.renderEngine, textures);
			}

			manager.renderEngine = textures;
			render.setRenderManager(manager);
		}
	}

	void onPostRender(Render render, EntityLivingBase entity, double x, double y, double z) {
		FEntityLiving<?> entity0 = FEntity.of(entity);

		if (entity0.properties.get(Entity.Frozen)) {
			RenderManager manager = RenderManager.instance;
			TextureManager textures = manager.renderEngine;

			if (textures instanceof TextureManagerIce) {
				textures = ((TextureManagerIce) textures).callback;
			}

			manager.renderEngine = textures;
			render.setRenderManager(manager);
		}
	}

	static class TextureManagerIce extends TextureManagerCallback {
		private static final ResourceLocation iceTexture = new ResourceLocation("elementarrows:textures/texturesIce.png");

		public TextureManagerIce(TextureManager callback) {
			super(callback);
		}

		@Override
		public void bindTexture(ResourceLocation texture) {
			if (texture.toString().equals("minecraft:textures/misc/shadow.png")) {
				super.bindTexture(texture);
			} else {
				super.bindTexture(iceTexture);
			}
		}

	}

}