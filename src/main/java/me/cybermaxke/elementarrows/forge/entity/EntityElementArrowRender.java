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
package me.cybermaxke.elementarrows.forge.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public final class EntityElementArrowRender extends RenderArrow {
	private final static ResourceLocation[] resources = new ResourceLocation[Short.MAX_VALUE];

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f01, float f02) {
		this.doRender((EntityArrow) entity, x, y, z, f01, f02);
	}

	@Override
	public void doRender(EntityArrow entity, double x, double y, double z, float f01, float f02) {
		super.doRender(entity, x, y, z, f01, f02);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityArrow) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityArrow entity) {
		short data = ((EntityElementArrow) entity).getElementData();

		if (data != 0) {
			if (EntityElementArrowRender.resources[data] == null) {
				ElementArrow arrow0 = EArrowMod.mod.registry.fromData(data);
				if (arrow0 != null && arrow0.texture != null) {
					EntityElementArrowRender.resources[data] = new ResourceLocation(arrow0.texture);
					return EntityElementArrowRender.resources[data];
				} else {
					return super.getEntityTexture(entity);
				}
			} else {
				return EntityElementArrowRender.resources[data];
			}
		} else {
			return super.getEntityTexture(entity);
		}
	}

}