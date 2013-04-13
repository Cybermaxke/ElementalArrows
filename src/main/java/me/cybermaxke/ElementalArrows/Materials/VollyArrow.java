/**
 * 
 * This software is part of the ElementalArrows
 * 
 * This plugins adds custom arrows to the game like they from the
 * ElemantalArrows mod but ported to spoutplugin and bukkit.
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
package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;
import me.cybermaxke.ElementalArrows.ElementalArrows;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapelessRecipe;

public class VollyArrow extends CustomArrowItem {

	public VollyArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setMultiplePerShot(10);
		this.setArrowDrop(new ItemStack(Material.ARROW));
	}

	@Override
	public void registerRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);

		SpoutShapelessRecipe r = new SpoutShapelessRecipe(i);
		for (int j = 0; j < 5; j++) {
			r.addIngredient(ElementalArrows.DUAL_ARROW);
		}

		SpoutManager.getMaterialManager().registerSpoutRecipe(r);
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {

	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {
		
	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {
		
	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
		
	}
}