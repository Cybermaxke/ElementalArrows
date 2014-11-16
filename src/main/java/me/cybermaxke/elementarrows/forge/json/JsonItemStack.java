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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class JsonItemStack implements JsonObjectSerializer<ItemStack> {

	@Override
	public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject json = new JsonObject();

		json.addProperty("id", Item.itemRegistry.getNameForObject(src.getItem()));
		json.addProperty("amount", src.stackSize);
		json.addProperty("data", src.getItemDamage());

		return json;
	}

	@Override
	public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject json0 = json.getAsJsonObject();
		String id = json0.get("id").getAsString();

		Item item = (Item) Item.itemRegistry.getObject(id);

		if (item == null) {
			try {
				item = Item.getItemById(Integer.parseInt(id));
			} catch (NumberFormatException e) {}

			if (item == null) {
				throw new JsonParseException("Unknown effect id! (" + id + ")");
			}
		}

		int amount = json0.get("amount").getAsInt();
		int data = json0.get("data").getAsInt();

		if (data < 0) {
			data = 0;
		}

		return new ItemStack(item, amount, data);
	}

}