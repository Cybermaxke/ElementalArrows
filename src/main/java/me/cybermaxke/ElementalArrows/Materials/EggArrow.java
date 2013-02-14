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

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

public class EggArrow extends CustomArrowItem {

	public EggArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		
		this.addConfigData("BabyChicken", true);
	}
	
	@Override
	public void registerRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 1);
		
		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.egg);
		r.setIngredient('B', MaterialData.stick);
		r.setIngredient('C', MaterialData.feather);
		
		SpoutManager.getMaterialManager().registerSpoutRecipe(r);
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {
		
	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {
		Arrow a = (Arrow) arrow.getBukkitEntity();
		Chicken c = (Chicken) a.getWorld().spawnEntity(a.getLocation(), EntityType.CHICKEN);
		
		if ((Boolean) this.getConfigData("BabyChicken")) {
			c.setBaby();
		}
		
		a.remove();
	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {
		
	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
	
	}
}