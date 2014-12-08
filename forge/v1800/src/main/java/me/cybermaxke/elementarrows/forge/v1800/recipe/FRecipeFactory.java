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
package me.cybermaxke.elementarrows.forge.v1800.recipe;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import net.minecraft.item.crafting.IRecipe;

import me.cybermaxke.elementarrows.common.recipe.Recipe;
import me.cybermaxke.elementarrows.common.recipe.RecipeFactoryBase;

public class FRecipeFactory extends RecipeFactoryBase {
	private Map<Recipe, IRecipe> recipes = new HashMap<Recipe, IRecipe>();
	private FRecipeHelper helper = new FRecipeHelper();

	@Override
	public void register(Recipe recipe) {
		Preconditions.checkNotNull(recipe);

		if (this.recipes.containsKey(recipe)) {
			return;
		}

		IRecipe recipe0 = this.helper.register(recipe);
		this.recipes.put(recipe, recipe0);
	}

	@Override
	public boolean remove(Recipe recipe) {
		IRecipe recipe0 = this.recipes.remove(recipe);

		if (recipe0 != null) {
			return this.helper.remove(recipe0);
		} else {
			return false;
		}
	}

}