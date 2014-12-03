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

import java.util.Map;
import java.util.WeakHashMap;

import me.cybermaxke.elementarrows.common.entity.Entity;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntity;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityLiving;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityIce {
	private static final ResourceLocation iceTexture = new ResourceLocation("elementarrows:textures/texturesIce.png");

	private Map<TextureManager, TextureManager> callbacks = new WeakHashMap<TextureManager, TextureManager>();
	private RenderManagerCallback manager = new RenderManagerCallback();

	/**
	 * The last renderer.
	 */
	private Render lastRender;
	private Class<?> lastKey;

	/**
	 * Initialize the ice renderer.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	class RenderPlayerArm extends RenderPlayer {

		@Override
		public void renderFirstPersonArm(EntityPlayer player) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(iceTexture);
			RenderManager.instance.entityRenderMap.put(lastKey, lastRender);

			if (lastRender instanceof RenderPlayer) {
				((RenderPlayer) lastRender).renderFirstPersonArm(player);
			}

			/**
			 * Clear the cached renderer.
			 */
			lastRender = null;
			lastKey = null;
		}

	}

	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event) {
		if (event.isCanceled()) {
			return;
		}

		EntityLivingBase entity = Minecraft.getMinecraft().renderViewEntity;
		Render render = RenderManager.instance.getEntityRenderObject(entity);
		FEntityLiving<?> entity0 = FEntity.of(entity);

		if (render instanceof RenderPlayer && entity0.properties.get(Entity.Frozen)) {
			RenderPlayerArm render0 = new RenderPlayerArm();
			render0.setRenderManager(RenderManager.instance);
			Class<?> key = entity.getClass();

			RenderManager.instance.entityRenderMap.put(key, render0);

			if (render instanceof RenderPlayerArm) {
				return;
			}

			/**
			 * Cache the renderer to reset it after it is done.
			 */
			this.lastRender = render;
			this.lastKey = key;
		} else if (this.lastRender != null) {
			RenderManager.instance.entityRenderMap.put(this.lastKey, this.lastRender);

			/**
			 * Clear the cached renderer.
			 */
			this.lastRender = null;
			this.lastKey = null;
		}
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
			TextureManager textures = RenderManager.instance.renderEngine;

			if (this.callbacks.containsKey(textures)) {
				textures = this.callbacks.get(textures);
			} else {
				textures = new TextureManagerIce(textures);
				this.callbacks.put(RenderManager.instance.renderEngine, textures);
			}

			this.manager.manager = textures;
			this.manager.update();

			render.setRenderManager(this.manager.renderManager);
		} else {
			render.setRenderManager(RenderManager.instance);
		}
	}

	void onPostRender(Render render, EntityLivingBase entity, double x, double y, double z) {
		render.setRenderManager(RenderManager.instance);
	}

	static class TextureManagerIce extends TextureManagerCallback {

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