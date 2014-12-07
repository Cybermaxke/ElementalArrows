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
package me.cybermaxke.elementarrows.spigot.v1710.json;

import com.google.gson.GsonBuilder;

import me.cybermaxke.elementarrows.common.json.JsonFactoryBase;
import me.cybermaxke.elementarrows.common.json.serial.JsonBlockType;
import me.cybermaxke.elementarrows.common.json.serial.JsonEnchant;
import me.cybermaxke.elementarrows.common.json.serial.JsonItemType;
import me.cybermaxke.elementarrows.common.json.serial.JsonPotionType;
import me.cybermaxke.elementarrows.spigot.v1710.block.FBlockType;
import me.cybermaxke.elementarrows.spigot.v1710.enchant.FEnchant;
import me.cybermaxke.elementarrows.spigot.v1710.inventory.FItemType;
import me.cybermaxke.elementarrows.spigot.v1710.potion.FPotionType;

public class FJsonFactory extends JsonFactoryBase {

	@Override
	protected GsonBuilder newGson() {
		return super.newGson()
				.registerTypeAdapter(FPotionType.class, new JsonPotionType())
				.registerTypeAdapter(FItemType.class, new JsonItemType())
				.registerTypeAdapter(FBlockType.class, new JsonBlockType())
				.registerTypeAdapter(FEnchant.class, new JsonEnchant());
	}

}