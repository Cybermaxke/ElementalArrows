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
package me.cybermaxke.elementarrows.forge.json;

import java.lang.reflect.Type;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

public final class JsonPotionEffect implements JsonObjectSerializer<PotionEffect> {
	private BiMap<String, Integer> byName = HashBiMap.create();

	public JsonPotionEffect() {
		for (int i = 0; i < Potion.potionTypes.length; i++) {
			Potion potion = Potion.potionTypes[i];

			if (potion != null) {
				this.byName.put(potion.getName().replaceFirst("potion.", "").toLowerCase(), potion.id);
			}
		}
	}

	@Override
	public PotionEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject json0 = json.getAsJsonObject();
		String id = json0.get("effectId").getAsString();

		int id0;

		try {
			id0 = Integer.parseInt(id);

			if (id0 >= Potion.potionTypes.length || Potion.potionTypes[id0] == null) {
				throw new JsonParseException("Unknown effect id! (" + id0 + ")");
			}
		} catch (NumberFormatException e) {
			id = id.toLowerCase();

			if (this.byName.containsKey(id)) {
				id0 = this.byName.get(id);
			} else {
				throw new JsonParseException("Unknown effect id! (" + id + ")");
			}
		}

		int duration = json0.get("duration").getAsInt();
		int amplifier = json0.get("amplifier").getAsInt();
		boolean ambient = json0.get("ambient").getAsBoolean();

		return new PotionEffect(id0, duration, amplifier, ambient);
	}

	@Override
	public JsonElement serialize(PotionEffect src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.addProperty("effectId", this.byName.inverse().get(src.getPotionID()));
		json.addProperty("duration", src.getDuration());
		json.addProperty("amplifier", src.getAmplifier());
		json.addProperty("ambient", src.getIsAmbient());

		return json;
	}

}