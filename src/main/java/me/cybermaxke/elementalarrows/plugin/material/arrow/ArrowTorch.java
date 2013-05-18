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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.elementalarrows.api.ElementalArrows;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.GenericCustomArrow;

public class ArrowTorch extends GenericCustomArrow {

	public ArrowTorch(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setDrop(new ItemStack(Material.ARROW));
	}

	@Override
	public Recipe[] getRecipes() {
		SpoutItemStack i = new SpoutItemStack(this);

		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B");
		r.setIngredient('A', MaterialData.torch);
		r.setIngredient('B', MaterialData.arrow);

		return new Recipe[] { r };
	}

	@Override
	public void onHit(LivingEntity shooter, final ElementalArrow arrow) {
		new BukkitRunnable() {

			@Override
			public void run() {
				Location l = arrow.getLocation();
				Block b = l.getBlock();

				if (b.isEmpty() || ElementalArrows.getAPI().isReplaceable(b.getType())) {
					b.setType(Material.TORCH);
				} else {
					b.getWorld().dropItem(l, new ItemStack(Material.TORCH));
				}
			}

		}.runTaskLater(this.getPlugin(), 6);
	}
}