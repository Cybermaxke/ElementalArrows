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

import java.io.File;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.bukkit.api.material.GenericCustomArrow;

public class ArrowIce extends GenericCustomArrow {
	private Random random = new Random();
	private int duration;

	public ArrowIce(Plugin plugin, String name, File texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onInit() {
		super.onInit();
		this.duration = 70;
	}

	@Override
	public void onLoad(YamlConfiguration config) {
		super.onLoad(config);
		if (config.contains("EffectDuration")) {
			this.duration = config.getInt("EffectDuration");
		}
	}

	@Override
	public void onSave(YamlConfiguration config) {
		super.onSave(config);
		if (!config.contains("EffectDuration")) {
			config.set("EffectDuration", this.duration);
		}
	}

	@Override
	public Recipe[] getRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);

		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.ice);
		r.setIngredient('B', MaterialData.stick);
		r.setIngredient('C', MaterialData.feather);

		return new Recipe[] { r };
	}

	@Override
	public void onHit(LivingEntity shooter, LivingEntity entity, ElementalArrow arrow) {
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, this.duration, 6));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, this.duration, 6));
	}

	@Override
	public void onHit(LivingEntity shooter, ElementalArrow arrow) {
		arrow.remove();
	}

	@Override
	public void onTick(LivingEntity shooter, ElementalArrow arrow) {
		if (this.random.nextInt(5) <= 1) {
			arrow.getWorld().playEffect(arrow.getLocation(), Effect.STEP_SOUND, Material.ICE.getId());
		}
	}
}