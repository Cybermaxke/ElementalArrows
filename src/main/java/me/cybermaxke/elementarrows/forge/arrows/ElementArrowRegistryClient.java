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
package me.cybermaxke.elementarrows.forge.arrows;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowInitEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementArrowRegistryClient extends ElementArrowRegistry {

	@Override
	public void register(int data, ElementArrow arrow) {
		super.register(data, arrow);

		/**
		 * Initialize on the client.
		 */
		arrow.onClientInit(new ArrowInitEvent(EArrowMod.mod.itemArrow, EArrowMod.mod.itemBow, data));
	}

}