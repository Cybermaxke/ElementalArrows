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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import me.cybermaxke.elementarrows.forge.recipe.Recipe;
import me.cybermaxke.elementarrows.forge.recipe.RecipeShaped;
import me.cybermaxke.elementarrows.forge.recipe.RecipeShapeless;

import net.minecraft.item.ItemStack;

public final class JsonRecipe implements JsonObjectSerializer<Recipe> {

	@Override
	public JsonElement serialize(Recipe recipe, Type type, JsonSerializationContext ctx) {
		JsonObject json = new JsonObject();

		if (recipe instanceof RecipeShaped) {
			RecipeShaped recipe0 = (RecipeShaped) recipe;
			JsonObject ingredients = new JsonObject();

			for (Entry<Character, ItemStack> entry : recipe0.getIngredients().entrySet()) {
				ingredients.add(entry.getKey() + "", ctx.serialize(entry.getValue(), ItemStack.class));
			}

			json.add("result", ctx.serialize(recipe0.getResult(), ItemStack.class));
			json.add("shape", ctx.serialize(recipe0.getShape(), String[].class));
			json.add("ingredients", ingredients);
		} else if (recipe instanceof RecipeShapeless) {
			RecipeShapeless recipe0 = (RecipeShapeless) recipe;
			JsonArray ingredients = new JsonArray();

			for (ItemStack itemStack : recipe0.getIngredients()) {
				ingredients.add(ctx.serialize(itemStack, ItemStack.class));
			}

			json.add("result", ctx.serialize(recipe0.getResult(), ItemStack.class));
			json.add("ingredients", ingredients);
		} else {
			throw new IllegalArgumentException("Unsupported recipe type! (" + recipe.getClass().getName() + ")");
		}

		return json;
	}

	@Override
	public Recipe deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		JsonObject json0 = json.getAsJsonObject();

		if (json0.has("shape")) {
			ItemStack result = ctx.deserialize(json0.get("result"), ItemStack.class);
			String[] shape = ctx.deserialize(json0.get("shape"), String[].class);
			Map<Character, ItemStack> ingredients = new HashMap<Character, ItemStack>();

			for (Entry<String, JsonElement> entry : json0.get("ingredients").getAsJsonObject().entrySet()) {
				ingredients.put(entry.getKey().charAt(0), (ItemStack) ctx.deserialize(entry.getValue(), ItemStack.class));
			}

			return new RecipeShaped(result, shape, ingredients);
		} else {
			ItemStack result = ctx.deserialize(json0.get("result"), ItemStack.class);
			List<ItemStack> ingredients = new ArrayList<ItemStack>();

			JsonArray ingredients0 = json0.get("ingredients").getAsJsonArray();
			for (int i = 0; i < ingredients0.size(); i++) {
				ingredients.add((ItemStack) ctx.deserialize(ingredients0.get(i), ItemStack.class));
			}

			return new RecipeShapeless(result, ingredients);
		}
	}

}