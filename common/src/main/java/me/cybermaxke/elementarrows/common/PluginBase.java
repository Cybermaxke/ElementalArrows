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
package me.cybermaxke.elementarrows.common;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowBlindness;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowDazing;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowDiamond;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowDirt;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowEgg;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowEnderEye;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowExplosion;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowLightning;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowPoison;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowRazor;

public class PluginBase implements Plugin {

	@Override
	public void onPreInit() {

	}

	@Override
	public void onInit() {
		Arrows.register(1, new ArrowBlindness());
		Arrows.register(2, new ArrowDazing());
		Arrows.register(3, new ArrowDirt());
		Arrows.register(4, new ArrowEgg());
		Arrows.register(5, new ArrowEnderEye());
		Arrows.register(6, new ArrowExplosion());
		Arrows.register(8, new ArrowLightning());
		Arrows.register(9, new ArrowPoison());
		Arrows.register(10, new ArrowRazor());
		Arrows.register(15, new ArrowDiamond());
	}

	@Override
	public void onPostInit() {

	}

}