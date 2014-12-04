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
package me.cybermaxke.elementarrows.common.json.serial;

import java.lang.reflect.Type;

import me.cybermaxke.elementarrows.common.json.JsonSerial;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.potion.Potions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class JsonPotionType implements JsonSerial<PotionType> {

	@Override
	public JsonElement serialize(PotionType src, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(src.getId());
	}

	@Override
	public PotionType deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		String id = json.getAsString();
		PotionType type0 = Potions.typeById(id);

		if (type0 != null) {
			return type0;
		}

		int id0 = 0;

		try {
			id0 = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return null;
		}

		return Potions.typeById(id0);
	}

}