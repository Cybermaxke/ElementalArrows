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
package me.cybermaxke.elementalarrows.bukkit.plugin.material.arrow;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.bukkit.api.material.GenericCustomArrow;

public class ArrowFire extends GenericCustomArrow {

	public ArrowFire(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onInit() {
		super.onInit();
		this.setFireTicks(100);
	}

	@Override
	public Recipe[] getRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);

		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.coal);
		r.setIngredient('B', MaterialData.stick);
		r.setIngredient('C', MaterialData.feather);

		SpoutShapedRecipe r2 = new SpoutShapedRecipe(i);
		r2.shape("A", "B", "C");
		r2.setIngredient('A', MaterialData.charcoal);
		r2.setIngredient('B', MaterialData.stick);
		r2.setIngredient('C', MaterialData.feather);

		return new Recipe[] { r };
	}

	@Override
	public void onHit(LivingEntity shooter, ElementalArrow arrow) {
		arrow.remove();
	}
}