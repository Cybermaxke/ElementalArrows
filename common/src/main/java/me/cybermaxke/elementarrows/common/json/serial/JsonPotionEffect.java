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
import me.cybermaxke.elementarrows.common.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.potion.Potions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

public class JsonPotionEffect implements JsonSerial<PotionEffect> {

	@Override
	public JsonElement serialize(PotionEffect src, Type type, JsonSerializationContext ctx) {
		JsonObject json = new JsonObject();

		json.addProperty("effectId", src.getType().getId());
		json.addProperty("duration", src.getDuration());
		json.addProperty("amplifier", src.getAmplifier());
		json.addProperty("ambient", src.isAmbient());
		json.addProperty("particles", src.hasParticles());

		return json;
	}

	@Override
	public PotionEffect deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		JsonObject json0 = json.getAsJsonObject();

		PotionType potion = ctx.deserialize(json0.get("effectId"), PotionType.class);
		if (potion == null) {
			return null;
		}

		int duration = json0.get("duration").getAsInt();
		int amplifier = json0.get("amplifier").getAsInt();
		boolean ambient = json0.get("ambient").getAsBoolean();
		boolean particles = json0.get("particles").getAsBoolean();

		return Potions.of(potion, duration, amplifier, ambient, particles);
	}

}