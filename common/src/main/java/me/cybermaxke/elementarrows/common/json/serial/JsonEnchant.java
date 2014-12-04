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

import me.cybermaxke.elementarrows.common.enchant.Enchant;
import me.cybermaxke.elementarrows.common.enchant.Enchants;
import me.cybermaxke.elementarrows.common.json.JsonSerial;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

public class JsonEnchant implements JsonSerial<Enchant> {

	@Override
	public JsonElement serialize(Enchant src, Type type, JsonSerializationContext ctx) {
		return new JsonPrimitive(src.getId());
	}

	@Override
	public Enchant deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		String id = json.getAsString();
		Enchant type0 = Enchants.typeById(id);

		if (type0 != null) {
			return type0;
		}

		int id0 = 0;

		try {
			id0 = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return null;
		}

		return Enchants.typeById(id0);
	}

}