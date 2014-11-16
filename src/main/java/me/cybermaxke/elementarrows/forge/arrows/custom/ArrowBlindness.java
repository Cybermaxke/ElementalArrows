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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;
import me.cybermaxke.elementarrows.forge.util.Clones;

public final class ArrowBlindness extends ElementArrow {

	@JsonField("blindnessEffect")
	public PotionEffect blindnessEffect = new PotionEffect(Potion.blindness.id, 75, 12);

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsBlindness";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Items.dye, 1, 0),
				new ItemStack(Items.dye, 1, 0),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowBlindness";
		this.texture = "elementArrows:textures/entity/arrowEntityBlindness.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		if (this.blindnessEffect != null) {
			event.entity.addPotionEffect(Clones.clone(this.blindnessEffect));
		}
	}

}