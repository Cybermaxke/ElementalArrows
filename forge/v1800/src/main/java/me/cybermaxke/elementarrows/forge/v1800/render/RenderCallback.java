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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface RenderCallback {

	/**
	 * Called when the entity is rendered at the position.
	 * 
	 * @param render the render
	 * @param entity the entity
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @param arg1
	 * @param arg2
	 */
	void renderEntity(Render render, Entity entity, double x, double y, double z, float arg1, float arg2);

	/**
	 * Called when the model of the entity is rendered.
	 * 
	 * @param render the render
	 * @param entity the entity
	 * @param model the model of the entity
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 * @param arg6
	 */
	void renderModel(Render render, Entity entity, ModelBase model, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6);

	/**
	 * Called when the entity layers are rendered.
	 * 
	 * @param render the render
	 * @param entity the entity
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @param arg5
	 * @param arg6
	 * @param arg7
	 */
	void renderLayers(Render render, Entity entity, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7);

}