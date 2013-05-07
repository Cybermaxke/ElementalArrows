/**
 * 
 * This software is part of the ElementalArrows
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.plugin.material.arrow;

import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.elementalarrows.api.material.GenericCustomArrow;

public class ArrowRazor extends GenericCustomArrow {

	public ArrowRazor(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onInit() {
		super.onInit();
		this.setSpeedMultiplier(1.7D);
		this.setKnockbackStrengthMultiplier(1.2D);
	}

	@Override
	public Recipe[] getRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);

		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.ironIngot);
		r.setIngredient('B', MaterialData.stick);
		r.setIngredient('C', MaterialData.feather);

		return new Recipe[] { r };
	}
}