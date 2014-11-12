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

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.entity.EntityAttribute;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public final class RenderIceHandler {
	private final RenderIce renderIce = new RenderIce();

	/**
	 * Initialize the ice handler. (Ice renderer.)
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRenderPlayerPost(RenderPlayerEvent.Post event) {
		this.onRender(event.entityLiving, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ);
	}

	@SubscribeEvent
	public void onRenderLivingPost(RenderLivingEvent.Post event) {
		this.onRender(event.entity, event.x, event.y, event.z);
	}

	public void onRender(EntityLivingBase entity, double x, double y, double z) {
		IAttributeInstance instance = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

		if (!entity.isDead && instance != null && instance.getModifier(EntityAttribute.FrozenUUID) != null) {
			this.renderIce.setRenderManager(RenderManager.instance);
			this.renderIce.doRender(entity, x, y, z, 0f, 0f);
		}
	}

}