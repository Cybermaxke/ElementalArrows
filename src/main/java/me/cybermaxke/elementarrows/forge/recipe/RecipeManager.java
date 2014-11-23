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
package me.cybermaxke.elementarrows.forge.recipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.forge.json.JsonFactory;

public final class RecipeManager {
	public final RecipeHelper helper = new RecipeHelper();
	public final JsonFactory factory;

	/**
	 * The default recipes which will be saved if the file couldn't be found.
	 */
	private final List<Recipe> defaults = new ArrayList<Recipe>();

	public RecipeManager(JsonFactory factory) {
		this.factory = factory;
	}

	/**
	 * Adds a default recipe to the list.
	 * 
	 * @param recipe the recipe
	 */
	public void addDefault(Recipe recipe) {
		Preconditions.checkNotNull(recipe);

		this.defaults.add(recipe);
	}

	/**
	 * Load the recipes from the json file. The format must be
	 * an array of recipes.
	 * 
	 * @param file the file
	 * @throws IOException 
	 */
	public void load(File file) throws IOException {
		Preconditions.checkNotNull(file);

		Recipe[] defaults = this.defaults.toArray(new Recipe[] {});
		Recipe[] recipes = this.factory.fromJsonFile(file, Recipe[].class, defaults);

		for (int i = 0; i < recipes.length; i++) {
			this.helper.register(recipes[i]);
		}
	}
}