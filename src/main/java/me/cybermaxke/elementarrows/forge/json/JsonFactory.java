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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

import me.cybermaxke.elementarrows.forge.recipe.Recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonFactory {
	private final Gson gson = new GsonBuilder()
			/**
			 * Setup default settings.
			 */
			.addDeserializationExclusionStrategy(new JsonExclusionStrategy())
			.addSerializationExclusionStrategy(new JsonExclusionStrategy())
			.setFieldNamingStrategy(new JsonFieldNamingStrategy())
			.setPrettyPrinting()
			.serializeNulls()
			/**
			 * Register custom type adapters.
			 */
			.registerTypeAdapter(ItemStack.class, new JsonItemStack())
			.registerTypeAdapter(PotionEffect.class, new JsonPotionEffect())
			.registerTypeAdapter(Recipe.class, new JsonRecipe())
			/**
			 * Create the gson.
			 */
			.create();

	public <T> T fromJsonFile(File file, Class<T> type) throws IOException {
		/**
		 * Try to read the object from the json file.
		 */
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);

			return this.gson.fromJson(br, type);
		/**
		 * Otherwise try to create a new instance and saving the defaults.
		 */
		} else {
			/**
			 * Be sure that the directories exist.
			 */
			file.getParentFile().mkdirs();

			try {
				T object = type.newInstance();

				FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osr = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osr);

				this.gson.toJson(object, bw);

				bw.flush();
				bw.close();

				return object;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	static class JsonFieldNamingStrategy implements FieldNamingStrategy {

		@Override
		public String translateName(Field f) {
			return f.getAnnotation(JsonField.class).value();
		}

	}

	static class JsonExclusionStrategy implements ExclusionStrategy {

		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			return f.getAnnotation(JsonField.class) == null;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}

	}

}