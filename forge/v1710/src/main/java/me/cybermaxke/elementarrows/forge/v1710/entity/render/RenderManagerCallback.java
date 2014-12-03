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

import java.lang.reflect.Constructor;

import com.google.common.base.Preconditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;

@SideOnly(Side.CLIENT)
public class RenderManagerCallback {
	public final RenderManager renderManager;

	/**
	 * The texture manager.
	 */
	public TextureManager manager = RenderManager.instance.renderEngine;

	public RenderManagerCallback() {
		this.renderManager = newManager();
	}

	public void update() {
		this.update(RenderManager.instance);
	}

	public void update(RenderManager manager) {
		Preconditions.checkNotNull(manager);

		this.renderManager.entityRenderMap = manager.entityRenderMap;
		this.renderManager.itemRenderer = manager.itemRenderer;
		this.renderManager.livingPlayer = manager.livingPlayer;
		this.renderManager.field_147941_i = manager.field_147941_i;
		this.renderManager.options = manager.options;
		this.renderManager.playerViewX = manager.playerViewX;
		this.renderManager.playerViewY = manager.playerViewY;
		this.renderManager.renderEngine = this.manager;
		this.renderManager.viewerPosX = manager.viewerPosX;
		this.renderManager.viewerPosY = manager.viewerPosY;
		this.renderManager.viewerPosZ = manager.viewerPosZ;
		this.renderManager.worldObj = manager.worldObj;
	}

	static RenderManager newManager() {
		RenderManager manager = null;

		try {
			Constructor<?> constr = RenderManager.class.getDeclaredConstructor();
			constr.setAccessible(true);

			manager = (RenderManager) constr.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return manager;
	}

}