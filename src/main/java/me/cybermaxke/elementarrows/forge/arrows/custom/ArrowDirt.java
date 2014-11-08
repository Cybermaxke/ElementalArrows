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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDirt extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDirt";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDirt";
		this.texture = "elementArrows:textures/entity/arrowEntityDirt.png";
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setDamage(event.arrow.getDamage() * 1.3d);
		event.arrow.setKnockbackStrength(2);
	}

}