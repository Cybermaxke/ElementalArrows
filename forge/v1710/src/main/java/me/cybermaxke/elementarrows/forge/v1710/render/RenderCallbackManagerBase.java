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
package me.cybermaxke.elementarrows.forge.v1710.render;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class RenderCallbackManagerBase implements RenderCallbackManager {
	private static Set<RenderCallback> callbacks = new HashSet<RenderCallback>();

	@Override
	public void addCallback(RenderCallback callback) {
		callbacks.add(callback);
	}

	/**
	 * DO NOT RENAME THE METHODS BELOW, UNLESS YOU KNOWN WHAT YOU ARE DOING.
	 */

	public static void call0(Render render, Entity entity, double x, double y, double z, float arg1, float arg2) {
		for (RenderCallback callback : callbacks) {
			callback.renderEntity(render, entity, x, y, z, arg1, arg2);
		}
	}

	public static void call1(Render render, Entity entity, ModelBase model, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6) {
		for (RenderCallback callback : callbacks) {
			callback.renderModel(render, entity, model, arg1, arg2, arg3, arg4, arg5, arg6);
		}
	}

	public static void call2(Render render, Entity entity, float arg1) {
		for (RenderCallback callback : callbacks) {
			callback.renderEquipment(render, entity, arg1);
		}
	}

}