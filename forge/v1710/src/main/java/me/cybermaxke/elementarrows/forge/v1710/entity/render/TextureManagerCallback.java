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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class TextureManagerCallback extends TextureManager {
	public final TextureManager callback;

	public TextureManagerCallback(TextureManager callback) {
		super(null);

		/**
		 * The callback.
		 */
		this.callback = callback;
	}

	@Override
	public void bindTexture(ResourceLocation texture) {
		this.callback.bindTexture(texture);
	}

	@Override
	public ResourceLocation getResourceLocation(int location) {
		return this.callback.getResourceLocation(location);
	}

	@Override
	public boolean loadTextureMap(ResourceLocation texture, TextureMap map) {
		return this.callback.loadTextureMap(texture, map);
	}

	@Override
	public boolean loadTickableTexture(ResourceLocation texture, ITickableTextureObject object) {
		return this.callback.loadTickableTexture(texture, object);
	}

	@Override
	public boolean loadTexture(ResourceLocation texture, ITextureObject object) {
		return this.callback.loadTexture(texture, object);
	}

	@Override
	public ITextureObject getTexture(ResourceLocation texture) {
		return this.callback.getTexture(texture);
	}

	@Override
	public ResourceLocation getDynamicTextureLocation(String location, DynamicTexture texture) {
		return this.callback.getDynamicTextureLocation(location, texture);
	}

	@Override
	public void tick() {
		this.callback.tick();
	}

	@Override
	public void deleteTexture(ResourceLocation texture) {
		this.callback.deleteTexture(texture);
	}

	@Override
	public void onResourceManagerReload(IResourceManager manager) {
		this.callback.onResourceManagerReload(manager);
	}

}