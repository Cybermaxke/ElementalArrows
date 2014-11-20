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

import java.util.HashMap;
import java.util.Map;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public final class RecipeShaped implements Recipe {

	/**
	 * The result item stack.
	 */
	protected final ItemStack result;

	/**
	 * The shape of the recipe. Contains 3 lines with 3 chars in each line. (Can be less, depending on recipe bounds.)
	 */
	protected final String[] shape;

	/**
	 * The ingredients attached to the characters in the lines.
	 */
	protected final Map<Character, ItemStack> ingredients;

	/**
	 * Creates a new shaped recipe.
	 * 
	 * @param result the result item stack
	 * @param shape the shape of the recipe
	 * @param ingredients the ingredients
	 */
	public RecipeShaped(ItemStack result, String[] shape, Map<Character, ItemStack> ingredients) {
		this.ingredients = ingredients;
		this.result = result;
		this.shape = shape;
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
	 * Gets the shape of the recipe.
	 * 
	 * @return the shape
	 */
	public String[] getShape() {
		return this.shape;
	}

	/**
	 * Gets the ingredients of the recipe.
	 * 
	 * @return the ingredients
	 */
	public Map<Character, ItemStack> getIngredients() {
		return this.ingredients;
	}

	@Override
	public IRecipe convertTo() {
		return EArrowMod.mod.recipeManager.helper.convertTo(this);
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
		private Map<Character, ItemStack> ingredients = new HashMap<Character, ItemStack>();
		private ItemStack result;
		private String[] shape;

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
		 * Sets the shape lines.
		 * 
		 * @param shape the shape
		 * @return the builder
		 */
		public Builder withShape(String... lines) {
			this.shape = lines;
			return this;
		}

		/**
		 * Sets the ingredient bound to the character.
		 * 
		 * @param character the character
		 * @param ingredient the ingredient
		 * @return the builder
		 */
		public Builder withIngredient(char character, ItemStack ingredient) {
			this.ingredients.put(character, ingredient);
			return this;
		}

		/**
		 * Sets the ingredient bound to the character.
		 * 
		 * @param character the character
		 * @param ingredient the ingredient
		 * @return the builder
		 */
		public Builder withIngredient(char character, Item ingredient) {
			this.ingredients.put(character, new ItemStack(ingredient));
			return this;
		}

		/**
		 * Builds the shaped recipe.
		 * 
		 * @return the recipe
		 */
		public RecipeShaped build() {
			return new RecipeShaped(this.result, this.shape, this.ingredients);
		}

	}

}