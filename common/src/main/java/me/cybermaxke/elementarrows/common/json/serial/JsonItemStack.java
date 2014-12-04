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

import me.cybermaxke.elementarrows.common.item.Items;
import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.item.type.ItemType;
import me.cybermaxke.elementarrows.common.json.JsonSerial;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

public class JsonItemStack implements JsonSerial<ItemStack> {

	@Override
	public JsonElement serialize(ItemStack src, Type type, JsonSerializationContext ctx) {
		JsonObject json = new JsonObject();

		json.add("id", ctx.serialize(src.getType()));
		json.addProperty("amount", src.getQuantity());
		json.addProperty("data", src.getData());

		return json;
	}

	@Override
	public ItemStack deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		JsonObject json0 = json.getAsJsonObject();

		ItemType item = ctx.deserialize(json0.get("id"), ItemType.class);
		if (item == null) {
			return null;
		}

		int amount = json0.get("amount").getAsInt();
		int data = json0.get("data").getAsInt();

		return Items.of(item, amount, data);
	}

}