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

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.recipe.RecipeShapeless;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowEgg extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsEgg";

		/**
		 * Add the default recipe.
		 */
		event.recipes.addDefault(RecipeShapeless.builder()
				.withResult(new ItemStack(Items.arrow, 1, event.data))
				.withIngredient(new ItemStack(Items.egg, 1, 0))
				.withIngredient(new ItemStack(Items.arrow, 1, 0))
				.build());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowEgg";
		this.texture = "elementArrows:textures/entity/arrowEntityEgg.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.arrow.setElementData(0);

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		this.spawnChickenAt(event.arrow.worldObj, x, y, z);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		event.arrow.setElementData(0);

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		this.spawnChickenAt(event.arrow.worldObj, x, y, z);
	}

	public void spawnChickenAt(World world, double x, double y, double z) {
		EntityChicken chicken = new EntityChicken(world);
		chicken.setPositionAndRotation(x, y, z, world.rand.nextFloat() * 360f, 0);
		chicken.setGrowingAge(-24000);

		world.spawnEntityInWorld(chicken);
	}

}