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

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import org.getspout.spoutapi.particle.Particle;

public class IceArrow extends CustomArrowItem {

	public IceArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		
		this.addConfigData("EffectDuration", 70);
	}

	@Override
	public void registerRecipes() {

	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (Integer) this.getConfigData("EffectDuration"), 5));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, (Integer) this.getConfigData("EffectDuration"), 5));
	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {
		Arrow a = (Arrow) arrow.getBukkitEntity();
		a.remove();
	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {
		
	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
		Arrow a = (Arrow) arrow.getBukkitEntity();
		new IceParticle(a.getLocation(), 0.17);
	}
	
	public class IceParticle extends Particle {

		public IceParticle(Location location, double duration) { 
			super(ParticleType.SNOWBALLPOOF, location, new Vector(0, 0, 0));
			this.setParticleRed(0);
			this.setParticleGreen(70);
			this.setParticleBlue(255);
			this.setMaxAge((int) (duration * 20));
			this.setAmount(8);
			this.setGravity(0.98F);
			this.setScale(1.4F);
			this.spawn();
		}
	}
}