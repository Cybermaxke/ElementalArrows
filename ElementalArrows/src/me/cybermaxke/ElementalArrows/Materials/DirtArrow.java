package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import org.getspout.spoutapi.particle.Particle;

public class DirtArrow extends CustomArrowItem {

	public DirtArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setKnockback(5);
		this.setDamage(4);
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {
		new DirtParticle(entity.getLocation(), 1.5);
	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {

	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {

	}
	
	public class DirtParticle extends Particle {

		public DirtParticle(Location location, double duration) {
			super(ParticleType.DRIPLAVA, location, new Vector(0.5, 3, 0.5));
			this.setMaxAge((int) (duration * 20));
			this.setAmount(20);
			this.setGravity(0.3F);
			this.setScale(1.6F);
			this.spawn();
		}	
	}
}