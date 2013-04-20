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

import java.util.Random;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

public class EnderEyeArrow extends CustomArrowItem {
	private Random r = new Random();

	public EnderEyeArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void registerRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);

		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.eyeOfEnder);
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
		shooter.teleport(a.getLocation());
		a.remove();
	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {

	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
		Arrow a = (Arrow) arrow.getBukkitEntity();
		if (this.r.nextInt(5) <= 1) {
			a.getWorld().playEffect(a.getLocation(), Effect.STEP_SOUND, org.bukkit.Material.PORTAL.getId());
		}
	}
}