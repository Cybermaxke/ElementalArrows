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
package me.cybermaxke.elementarrows.common.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import me.cybermaxke.elementarrows.common.block.BlockType;
import me.cybermaxke.elementarrows.common.enchant.Enchant;
import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.item.type.ItemType;
import me.cybermaxke.elementarrows.common.json.serial.JsonBlockType;
import me.cybermaxke.elementarrows.common.json.serial.JsonEnchant;
import me.cybermaxke.elementarrows.common.json.serial.JsonItemStack;
import me.cybermaxke.elementarrows.common.json.serial.JsonItemType;
import me.cybermaxke.elementarrows.common.json.serial.JsonPotionEffect;
import me.cybermaxke.elementarrows.common.json.serial.JsonPotionType;
import me.cybermaxke.elementarrows.common.json.serial.JsonRecipe;
import me.cybermaxke.elementarrows.common.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.recipe.Recipe;

import com.google.common.base.Preconditions;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonFactoryBase implements JsonFactory {
	private Gson gson = this.newGson().create();

	/**
	 * Gets a new gson instance.
	 * 
	 * @return the gson builder
	 */
	protected GsonBuilder newGson() {
		return new GsonBuilder()
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
				.registerTypeAdapter(ItemType.class, new JsonItemType())
				.registerTypeAdapter(ItemStack.class, new JsonItemStack())
				.registerTypeAdapter(PotionType.class, new JsonPotionType())
				.registerTypeAdapter(PotionEffect.class, new JsonPotionEffect())
				.registerTypeAdapter(BlockType.class, new JsonBlockType())
				.registerTypeAdapter(Enchant.class, new JsonEnchant())
				.registerTypeAdapter(Recipe.class, new JsonRecipe());
	}

	@Override
	public Gson getGson() {
		return this.gson;
	}

	@Override
	public <T> T of(File file, Class<T> type) throws IOException {
		return this.of(file, type, null);
	}

	@Override
	public <T, V extends T> T of(File file, Class<T> type, V defaultValue) throws IOException {
		Preconditions.checkNotNull(file);
		Preconditions.checkNotNull(type);

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
				if (defaultValue == null) {
					if (type.isArray()) {
						defaultValue = (V) Array.newInstance(type, 0);
					} else {
						defaultValue = (V) type.newInstance();
					}
				}

				FileOutputStream fos = new FileOutputStream(file);
				OutputStreamWriter osr = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osr);

				this.gson.toJson(defaultValue, bw);

				bw.flush();
				bw.close();

				return defaultValue;
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