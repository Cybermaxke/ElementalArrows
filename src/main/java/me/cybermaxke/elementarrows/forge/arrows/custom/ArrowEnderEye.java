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
package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowEnderEye extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsEnderEye";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowEnderEye";
		this.texture = "elementArrows:textures/entity/arrowEntityEnderEye.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		this.onArrowHit(event.arrow);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		this.onArrowHit(event.arrow);
	}

	public void onArrowHit(EntityElementArrow arrow) {
		Entity shooter = arrow.shootingEntity;

		if (shooter == null) {
			return;
		}

		if (shooter.isRiding()) {
			shooter.mountEntity(null);
		}

		shooter.setPosition(arrow.posX, arrow.posY, arrow.posZ);
		shooter.fallDistance = 0f;

		if (shooter instanceof EntityPlayerMP) {
			((EntityPlayerMP) shooter).setPositionAndUpdate(arrow.posX, arrow.posY, arrow.posZ);
		}

		arrow.setElementData(0);
	}

}