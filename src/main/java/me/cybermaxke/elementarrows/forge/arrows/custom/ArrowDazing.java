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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDazing extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDazing";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Blocks.red_mushroom, 1, 0),
				new ItemStack(Blocks.brown_mushroom, 1, 0),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDazing";
		this.texture = "elementArrows:textures/entity/arrowEntityDazing.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 75, 12));
	}

}