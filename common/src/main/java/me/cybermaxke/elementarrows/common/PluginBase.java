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

import java.io.File;
import java.io.IOException;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowBlindness;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowDazing;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowDiamond;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowDirt;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowEgg;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowEnderEye;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowExplosion;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowFire;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowIce;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowLightning;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowPoison;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowRazor;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowTorch;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowVampiric;
import me.cybermaxke.elementarrows.common.arrow.custom.ArrowVolley;
import me.cybermaxke.elementarrows.common.json.Json;

public class PluginBase implements Plugin {

	@Override
	public void onPreInit(File file) {

	}

	@Override
	public void onInit(File file) {
		File folder = new File(file, "plugins" + File.separator + "elementarrows" + File.separator + "arrows");

		if (!folder.exists()) {
			folder.mkdirs();
		}

		try {
			Arrows.register(1, Json.of(new File(folder, "arrowBlindness.json"), ArrowBlindness.class));
			Arrows.register(2, Json.of(new File(folder, "arrowDazing.json"), ArrowDazing.class));
			Arrows.register(3, Json.of(new File(folder, "arrowDirt.json"), ArrowDirt.class));
			Arrows.register(4, Json.of(new File(folder, "arrowEgg.json"), ArrowEgg.class));
			Arrows.register(5, Json.of(new File(folder, "arrowEnderEye.json"), ArrowEnderEye.class));
			Arrows.register(6, Json.of(new File(folder, "arrowExplosion.json"), ArrowExplosion.class));
			Arrows.register(7, Json.of(new File(folder, "arrowFire.json"), ArrowFire.class));
			Arrows.register(8, Json.of(new File(folder, "arrowLightning.json"), ArrowLightning.class));
			Arrows.register(9, Json.of(new File(folder, "arrowPoison.json"), ArrowPoison.class));
			Arrows.register(10, Json.of(new File(folder, "arrowRazor.json"), ArrowRazor.class));
			Arrows.register(11, Json.of(new File(folder, "arrowVampiric.json"), ArrowVampiric.class));
			Arrows.register(12, Json.of(new File(folder, "arrowVolley.json"), ArrowVolley.class));
			Arrows.register(13, Json.of(new File(folder, "arrowIce.json"), ArrowIce.class));
			Arrows.register(14, Json.of(new File(folder, "arrowTorch.json"), ArrowTorch.class));
			Arrows.register(15, Json.of(new File(folder, "arrowDiamond.json"), ArrowDiamond.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPostInit(File file) {

	}

}