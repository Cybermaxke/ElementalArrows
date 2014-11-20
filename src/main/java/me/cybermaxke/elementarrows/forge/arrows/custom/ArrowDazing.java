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
import me.cybermaxke.elementarrows.forge.json.JsonField;
import me.cybermaxke.elementarrows.forge.recipe.RecipeShapeless;
import me.cybermaxke.elementarrows.forge.util.Clones;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDazing extends ElementArrow {

	@JsonField("confusionEffect")
	public PotionEffect confusionEffect = new PotionEffect(Potion.confusion.id, 75, 12);

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDazing";

		/**
		 * Add the default recipe.
		 */
		event.recipes.addDefault(RecipeShapeless.builder()
				.withResult(new ItemStack(event.itemArrow, 1, event.data))
				.withIngredient(new ItemStack(Blocks.red_mushroom, 1, 0))
				.withIngredient(new ItemStack(Blocks.brown_mushroom, 1, 0))
				.withIngredient(new ItemStack(event.itemArrow, 1, 0))
				.build());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDazing";
		this.texture = "elementArrows:textures/entity/arrowEntityDazing.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		if (this.confusionEffect != null) {
			event.entity.addPotionEffect(Clones.clone(this.confusionEffect));
		}
	}

}