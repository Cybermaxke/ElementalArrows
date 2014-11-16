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

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowRazor extends ElementArrow {

	@JsonField("powerMultiplier")
	private float powerMultiplier = 1.45f;

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsRazor";

		GameRegistry.addShapedRecipe(new ItemStack(event.itemArrow, 1, event.data), "x", "y", "z",
				'x', new ItemStack(Items.iron_ingot),
				'y', new ItemStack(Items.stick),
				'z', new ItemStack(Items.feather));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowRazor";
		this.texture = "elementArrows:textures/entity/arrowEntityRazor.png";
	}

	@Override
	public void onArrowBuild(ArrowBuildEvent event) {
		/**
		 * Modify the power (speed)
		 */
		event.power *= this.powerMultiplier;

		/**
		 * Let the underlying method build the arrow.
		 */
		super.onArrowBuild(event);
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setKnockbackStrength(2);
	}

}