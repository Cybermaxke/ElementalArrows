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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;
import me.cybermaxke.elementarrows.forge.util.Clones;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowVampiric extends ElementArrow {

	@JsonField("regenerationEffect")
	private PotionEffect regenerationEffect = new PotionEffect(Potion.regeneration.id, 40, 12);

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsVampiric";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowVampiric";
		this.texture = "elementArrows:textures/entity/arrowEntityVampiric.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		Entity shooter = event.arrow.shootingEntity;

		/**
		 * Undead doesn't have actual 'health' to steal.
		 */
		if (event.entity.getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD)) {
			return;
		}

		if (shooter != null && shooter instanceof EntityLivingBase && this.regenerationEffect != null) {
			((EntityLivingBase) shooter).addPotionEffect(Clones.clone(this.regenerationEffect));
		}

		int x = (int) Math.round(event.arrow.posX);
		int y = (int) Math.round(event.arrow.posY);
		int z = (int) Math.round(event.arrow.posZ);

		shooter.worldObj.playAuxSFX(2002, x, y, z, 16396);
	}

}