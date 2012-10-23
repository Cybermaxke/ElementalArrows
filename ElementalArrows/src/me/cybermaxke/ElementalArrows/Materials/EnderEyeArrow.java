package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.particle.Particle;

public class EnderEyeArrow extends CustomArrowItem {

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
		
		new EnderParticle(a.getLocation(), 0.18);
	}
	
	public class EnderParticle extends Particle {

		public EnderParticle(Location location, double duration) { 
			super(ParticleType.PORTAL, location, new Vector(0, 0, 0));
			this.setMaxAge((int) (duration * 20));
			this.setAmount(10);
			this.setGravity(0.98F);
			this.setScale(1.4F);
			this.spawn();
		}
	}
}