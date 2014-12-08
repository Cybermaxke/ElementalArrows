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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.recipe.Recipe;
import me.cybermaxke.elementarrows.common.recipe.RecipeShaped;
import me.cybermaxke.elementarrows.common.recipe.RecipeShapeless;
import me.cybermaxke.elementarrows.forge.v1800.inventory.FItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

public class FRecipeHelper {
	private final CraftingManager manager = CraftingManager.getInstance();

	/**
	 * Removes a recipe from the registry.
	 * 
	 * @param recipe the recipe
	 * @return whether it was removed
	 */
	public boolean remove(IRecipe recipe) {
		return this.manager.getRecipeList().remove(recipe);
	}

	/**
	 * Convert the recipe and register it.
	 * 
	 * @param recipe the recipe
	 * @return the converted recipe
	 */
	public IRecipe register(Recipe recipe) {
		Preconditions.checkNotNull(recipe);

		if (recipe instanceof RecipeShaped) {
			return this.register((RecipeShaped) recipe);
		} else if (recipe instanceof RecipeShapeless) {
			return this.register((RecipeShapeless) recipe);
		} else {
			throw new IllegalArgumentException("Unsupported recipe type! (" + recipe.getClass().getName() + ")");
		}
	}

	/**
	 * Convert the recipe and register it.
	 * 
	 * @param recipe the recipe
	 * @return the converted recipe
	 */
	public ShapedRecipes register(RecipeShaped recipe) {
		return this.convertTo(recipe, true);
	}

	/**
	 * Convert the recipe and register it.
	 * 
	 * @param recipe the recipe
	 * @return the converted recipe
	 */
	public ShapelessRecipes register(RecipeShapeless recipe) {
		return this.convertTo(recipe, true);
	}

	/**
	 * Convert the recipe.
	 * 
	 * @param recipe the recipe
	 * @return the converted recipe
	 */
	public IRecipe convertTo(Recipe recipe) {
		Preconditions.checkNotNull(recipe);

		if (recipe instanceof RecipeShaped) {
			return this.convertTo((RecipeShaped) recipe);
		} else if (recipe instanceof RecipeShapeless) {
			return this.convertTo((RecipeShapeless) recipe);
		} else {
			throw new IllegalArgumentException("Unsupported recipe type! (" + recipe.getClass().getName() + ")");
		}
	}

	/**
	 * Convert the recipe.
	 * 
	 * @param recipe the recipe
	 * @return the converted recipe
	 */
	public ShapedRecipes convertTo(RecipeShaped recipe) {
		return this.convertTo(recipe, false);
	}

	/**
	 * Convert the recipe.
	 * 
	 * @param recipe the recipe
	 * @return the converted recipe
	 */
	public ShapelessRecipes convertTo(RecipeShapeless recipe) {
		return this.convertTo(recipe, false);
	}

	ShapelessRecipes convertTo(RecipeShapeless recipe, boolean register) {
		List<ItemStack> ingredients = recipe.getIngredients();
		Object[] args = new Object[ingredients.size()];

		for (int index = 0; index < args.length; index++) {
			args[index] = ((FItemStack) ingredients.get(index)).itemStack;
		}

		/**
		 * Add the shapeless recipe.
		 */
		this.manager.addShapelessRecipe(((FItemStack) recipe.getResult()).itemStack, args);

		/**
		 * The converted recipe.
		 */
		ShapelessRecipes recipe0;

		/**
		 * Get all the registered recipes, the new one will be at the last index.
		 */
		List<IRecipe> recipes = this.manager.getRecipeList();

		if (!register) {
			recipe0 = (ShapelessRecipes) recipes.remove(recipes.size() - 1);
		} else {
			recipe0 = (ShapelessRecipes) recipes.get(recipes.size() - 1);
		}

		return recipe0;
	}

	ShapedRecipes convertTo(RecipeShaped recipe, boolean register) {
		Map<Character, ItemStack> ingredients = recipe.getIngredients();
		String[] shape = recipe.getShape();

		Object[] args = new Object[shape.length + ingredients.size() * 2];

		/**
		 * The current index.
		 */
		int index = 0;

		for (; index < shape.length; index++) {
			args[index] = shape[index];
		}

		for (Entry<Character, ItemStack> entry : ingredients.entrySet()) {
			args[index++] = entry.getKey();
			args[index++] = ((FItemStack) entry.getValue()).itemStack;
		}

		ShapedRecipes recipe0 = this.manager.addRecipe(((FItemStack) recipe.getResult()).itemStack, args);

		if (!register) {
			this.manager.getRecipeList().remove(recipe0);
		}

		return recipe0;
	}

}