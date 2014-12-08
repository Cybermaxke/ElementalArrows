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
package me.cybermaxke.elementarrows.forge.v1800.entity.render;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class RenderEntityIce {
	//private static final ResourceLocation iceTexture = new ResourceLocation("elementarrows:textures/texturesIce.png");
	//private static Field layersField;

	//private Map<TextureManager, TextureManager> callbacks = new WeakHashMap<TextureManager, TextureManager>();
	//private RenderManagerCallback manager = new RenderManagerCallback();

	/**
	 * The last renderer.
	 */
	//private LayerHeldItem lastRender;
	//private Class<?> lastKey;

	/**
	 * Initialize the ice renderer.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	/**
	class RenderPlayerArm extends LayerHeldItem {

		public RenderPlayerArm(RendererLivingEntity render) {
			super(render);
		}

		@Override
		public void doRenderLayer(EntityLivingBase entity, float f0, float f1, float f2, float f3, float f4, float f5, float f6) {
			Minecraft mc = Minecraft.getMinecraft();

			mc.getTextureManager().bindTexture(iceTexture);
			mc.getRenderManager().entityRenderMap.put(lastKey, lastRender);

			if (lastRender instanceof LayerHeldItem) {
				lastRender.doRenderLayer(entity, f0, f1, f2, f3, f4, f5, f6);
			}

			/**
			 * Clear the cached renderer.
			 *//**
			lastRender = null;
			lastKey = null;
		}

	}

	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event) {
		if (event.isCanceled()) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();

		Entity entity = mc.pointedEntity;
		RenderManager manager = mc.getRenderManager();
		Render render = manager.getEntityRenderObject(entity);
		FEntityLiving<?> entity0 = FEntity.of(entity);

		if (render instanceof RenderPlayer && entity0.properties.get(me.cybermaxke.elementarrows.common.entity.Entity.Frozen)) {
			RenderPlayerArm render0 = new RenderPlayerArm((RendererLivingEntity) render);
			Class<?> key = entity.getClass();

			if (render0 instanceof RenderPlayerArm) {
				return;
			}

			/**
			 * Cache the renderer to reset it after it is done.
			 *//**
			this.lastRender = render;
			this.lastKey = key;
		} else if (this.lastRender != null) {
			manager.entityRenderMap.put(this.lastKey, this.lastRender);

			/**
			 * Clear the cached renderer.
			 *//**
			this.lastRender = null;
			this.lastKey = null;
		}/*
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

		if (entity0.properties.get(me.cybermaxke.elementarrows.common.entity.Entity.Frozen)) {
			TextureManager textures = Minecraft.getMinecraft().renderEngine;

			if (this.callbacks.containsKey(textures)) {
				textures = this.callbacks.get(textures);
			} else {
				textures = new TextureManagerIce(textures);
				this.callbacks.put(Minecraft.getMinecraft().renderEngine, textures);
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

	}*/

}