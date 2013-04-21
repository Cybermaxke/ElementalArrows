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

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.GenericCustomArrow;

public class ArrowVampire extends GenericCustomArrow {
	private int duration;

	public ArrowVampire(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onInit() {
		this.duration = 40;
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
	public void onHit(LivingEntity shooter, LivingEntity entity, ElementalArrow arrow) {
		if (shooter != null) {
			shooter.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, this.duration, 12));
		}
		arrow.getWorld().playEffect(arrow.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK.getId());
	}
}