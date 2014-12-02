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

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.inventory.ItemStacks;

public final class RecipeShapedBase implements RecipeShaped {
	private final Map<Character, ItemStack> ingredients;
	private final ItemStack result;
	private final String[] shape;

	public RecipeShapedBase(ItemStack result, String[] shape, Map<Character, ItemStack> ingredients) {
		Preconditions.checkNotNull(ingredients);
		Preconditions.checkNotNull(result);
		Preconditions.checkNotNull(shape);

		this.ingredients = ingredients;
		this.result = result;
		this.shape = shape;
	}

	@Override
	public ItemStack getResult() {
		return this.result;
	}

	@Override
	public String[] getShape() {
		return this.shape;
	}

	@Override
	public Map<Character, ItemStack> getIngredients() {
		return this.ingredients;
	}

	protected static class BuilderBase implements Builder {
		private Map<Character, ItemStack> ingredients = new HashMap<Character, ItemStack>();
		private ItemStack result;
		private String[] shape;

		protected BuilderBase() {}

		@Override
		public Builder withResult(ItemStack result) {
			this.result = result;
			return this;
		}

		@Override
		public Builder withShape(String... lines) {
			this.shape = lines;
			return this;
		}

		@Override
		public Builder withIngredient(char character, ItemStack ingredient) {
			this.ingredients.put(character, ingredient);
			return this;
		}

		@Override
		public Builder withIngredient(char character, String ingredient) {
			this.ingredients.put(character, ItemStacks.of(ingredient));
			return this;
		}

		@Override
		public RecipeShapedBase build() {
			return new RecipeShapedBase(this.result, this.shape, this.ingredients);
		}

	}

}