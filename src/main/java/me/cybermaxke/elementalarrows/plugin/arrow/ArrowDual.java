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
package me.cybermaxke.elementalarrows.plugin.arrow;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapelessRecipe;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.api.material.GenericCustomArrow;
import me.cybermaxke.elementalarrows.plugin.ElementalArrowsPlugin;

public class ArrowDual extends GenericCustomArrow {

	public ArrowDual(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setDrop(new ItemStack(Material.ARROW));
	}

	@Override
	public Recipe[] getRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 1);

		SpoutShapelessRecipe r = new SpoutShapelessRecipe(i);
		r.addIngredient(2, MaterialData.arrow);

		return new Recipe[] { r };
	}

	@Override
	public void onShoot(LivingEntity shooter, final ElementalArrow arrow, ItemStack bow) {
		if (shooter == null || !(shooter instanceof Player)) {
			return;
		}

		final ElementalPlayer ep = ElementalArrowsPlugin.getInstance().getAPI().getPlayer((Player) shooter);
		new BukkitRunnable() {

			@Override
			public void run() {
				ElementalArrow a = ep.shootElementalArrow(arrow.getSpeed());
				a.setShooter(ep);
				a.setDamage(arrow.getDamage());
				a.setMaterial(arrow.getMaterial());
				a.setCritical(arrow.isCritical());
				a.setFireTicks(arrow.getFireTicks());
				a.setPickupable(arrow.isPickupable());
				a.setKnockbackStrength(arrow.getKnockbackStrength());
			}

		}.runTaskLater(ElementalArrowsPlugin.getInstance(), 14);
	}
}