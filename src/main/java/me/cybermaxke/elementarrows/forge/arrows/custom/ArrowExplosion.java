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

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowExplosion extends ElementArrow {

	@JsonField("explosionStrength")
	private float explosionStrength = 2.5f;

	/**
	 * This will also require the mob griefing to be turned off.
	 */
	@JsonField("destroyBlocks")
	private boolean destroyBlocks = true;

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsExplosion";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Blocks.tnt, 1, 0),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowExplosion";
		this.texture = "elementArrows:textures/entity/arrowEntityExplosion.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		boolean griefing = event.entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

		if (griefing && !this.destroyBlocks) {
			griefing = false;
		}

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		event.entity.worldObj.createExplosion(event.entity, x, y, z, this.explosionStrength, griefing);
		event.arrow.canBePickedUp = 2;
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		boolean griefing = event.arrow.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

		if (griefing && !this.destroyBlocks) {
			griefing = false;
		}

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		event.arrow.worldObj.createExplosion(event.arrow, x, y, z, this.explosionStrength, griefing);
		event.arrow.setElementData(0);
		event.arrow.canBePickedUp = 2;
	}

}