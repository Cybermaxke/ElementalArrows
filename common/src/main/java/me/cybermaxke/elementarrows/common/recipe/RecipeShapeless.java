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

import java.util.List;

import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;

public interface RecipeShapeless extends Recipe {

	/**
	 * Gets the result item stack of the recipe.
	 * 
	 * @return the result
	 */
	public ItemStack getResult();

	/**
	 * Gets the ingredients of the recipe.
	 * 
	 * @return the ingredients
	 */
	public List<ItemStack> getIngredients();

	/**
	 * The shapeless recipe builder.
	 */
	interface Builder {

		/**
		 * Sets the result item stack.
		 * 
		 * @param result the result
		 * @return the builder
		 */
		Builder withResult(ItemStack result);

		/**
		 * Adds the ingredient.
		 * 
		 * @param ingredient the ingredient
		 * @return the builder
		 */
		Builder withIngredient(ItemStack ingredient);

		/**
		 * Adds the ingredient.
		 * 
		 * @param ingredient the ingredient
		 * @return the builder
		 */
		Builder withIngredient(String ingredient);

		/**
		 * Builds the shaped recipe.
		 * 
		 * @return the recipe
		 */
		RecipeShapeless build();

	}

}