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

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;
import me.cybermaxke.elementarrows.forge.recipe.RecipeShaped;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDiamond extends ElementArrow {

	@JsonField("powerMultiplier")
	private float powerMultiplier = 1.45f;

	@JsonField("damageMultiplier")
	private float damageMultiplier = 1.45f;

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDiamond";

		/**
		 * Add the default recipe.
		 */
		event.recipes.addDefault(RecipeShaped.builder()
				.withResult(new ItemStack(event.itemArrow, 1, event.data))
				.withShape("x", "y", "z")
				.withIngredient('x', new ItemStack(Items.diamond, 1, 0))
				.withIngredient('y', new ItemStack(Items.stick, 1, 0))
				.withIngredient('z', new ItemStack(Items.feather, 1, 0))
				.build());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDiamond";
		this.texture = "elementArrows:textures/entity/arrowEntityDiamond.png";
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
		event.arrow.setIsCritical(false);
		event.arrow.setDamage(event.arrow.getDamage() * this.damageMultiplier);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onArrowClientTick(ArrowTickEvent event) {
		EntityElementArrow arrow = event.arrow;

		if (arrow.inGround) {
			return;
		}

		double mx = arrow.motionX;
		double my = arrow.motionY;
		double mz = arrow.motionZ;

		double x = arrow.posX;
		double y = arrow.posY;
		double z = arrow.posZ;

		for (int i = 0; i < 4; i++) {
			arrow.worldObj.spawnParticle("magicCrit", x + mx * i / 4d, y + my * i / 4d, z + mz * i / 4d, -mx, -my + 0.2d, -mz);
		}
	}

}