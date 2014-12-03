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

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.item.Items;
import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;

public class RecipeShapelessBase implements RecipeShapeless {
	private final ItemStack result;
	private final List<ItemStack> ingredients;

	protected RecipeShapelessBase(ItemStack result, List<ItemStack> ingredients) {
		Preconditions.checkNotNull(ingredients);
		Preconditions.checkNotNull(result);
		Preconditions.checkState(!ingredients.isEmpty());

		this.ingredients = ingredients;
		this.result = result;
	}

	@Override
	public ItemStack getResult() {
		return this.result;
	}

	@Override
	public List<ItemStack> getIngredients() {
		return this.ingredients;
	}

	protected static class BuilderBase implements Builder {
		private List<ItemStack> ingredients = new ArrayList<ItemStack>();
		private ItemStack result;

		protected BuilderBase() {}

		@Override
		public BuilderBase withResult(ItemStack result) {
			this.result = result;
			return this;
		}

		@Override
		public BuilderBase withIngredient(ItemStack ingredient) {
			this.ingredients.add(ingredient);
			return this;
		}

		@Override
		public BuilderBase withIngredient(String ingredient) {
			this.ingredients.add(Items.of(ingredient));
			return this;
		}

		@Override
		public RecipeShapelessBase build() {
			return new RecipeShapelessBase(this.result, this.ingredients);
		}

	}

}