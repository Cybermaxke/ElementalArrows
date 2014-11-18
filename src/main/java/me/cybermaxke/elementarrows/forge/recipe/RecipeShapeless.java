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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public final class RecipeShapeless implements Recipe {

	/**
	 * The result item stack.
	 */
	protected final ItemStack result;

	/**
	 * The ingredients attached to the characters in the lines.
	 */
	protected final List<ItemStack> ingredients;

	/**
	 * Creates a new shaped recipe.
	 * 
	 * @param result the result item stack
	 * @param ingredients the ingredients
	 */
	public RecipeShapeless(ItemStack result, List<ItemStack> ingredients) {
		this.ingredients = ingredients;
		this.result = result;
	}

	/**
	 * Gets the result item stack of the recipe.
	 * 
	 * @return the result
	 */
	public ItemStack getResult() {
		return this.result;
	}

	/**
	 * Gets the ingredients of the recipe.
	 * 
	 * @return the ingredients
	 */
	public List<ItemStack> getIngredients() {
		return this.ingredients;
	}

	@Override
	public IRecipe convertTo() {
		return null;
	}

	/**
	 * Gets a new shaped recipe builder.
	 * 
	 * @return the builder
	 */
	public static Builder builder() {
		return new Builder();
	} 

	public static class Builder {
		private List<ItemStack> ingredients = new ArrayList<ItemStack>();
		private ItemStack result;

		Builder() {}

		/**
		 * Sets the result item stack.
		 * 
		 * @param result the result
		 * @return the builder
		 */
		public Builder withResult(ItemStack result) {
			this.result = result;
			return this;
		}

		/**
		 * Adds the ingredient.
		 * 
		 * @param ingredient the ingredient
		 * @return the builder
		 */
		public Builder withIngredient(ItemStack ingredient) {
			this.ingredients.add(ingredient);
			return this;
		}

		/**
		 * Adds the ingredient.
		 * 
		 * @param ingredient the ingredient
		 * @return the builder
		 */
		public Builder withIngredient(Item ingredient) {
			this.ingredients.add(new ItemStack(ingredient));
			return this;
		}

		/**
		 * Builds the shaped recipe.
		 * 
		 * @return the recipe
		 */
		public RecipeShapeless build() {
			return new RecipeShapeless(this.result, this.ingredients);
		}

	}
}