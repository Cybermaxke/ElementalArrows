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
package me.cybermaxke.elementarrows.common.recipe;

public class Recipes {
	private static RecipeFactory factory;

	/**
	 * Gets the builder for a shapeless recipe.
	 * 
	 * @return the builder
	 */
	public static RecipeShapeless.Builder shapelessBuilder() {
		return factory.shapelessBuilder();
	}

	/**
	 * Gets the builder for a shaped recipe.
	 * 
	 * @return the builder
	 */
	public static RecipeShaped.Builder shapedBuilder() {
		return factory.shapedBuilder();
	}

	/**
	 * Registers the recipe.
	 * 
	 * @param recipe the recipe
	 */
	public static void register(Recipe recipe) {
		factory.register(recipe);
	}

	/**
	 * Removes the recipe.
	 * 
	 * @param recipe the recipe
	 * @return whether it was removed
	 */
	public static boolean remove(Recipe recipe) {
		return factory.remove(recipe);
	}

}