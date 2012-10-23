package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.particle.Particle;

public class DirtArrow extends CustomArrowItem {

	public DirtArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		
		this.addConfigData("Knockback", 5);
		this.addConfigData("BonusDamage", 1);
		
		this.setKnockback((Integer) this.getConfigData("Knockback"));
		this.setDamage((Integer) this.getConfigData("BonusDamage"));
	}
	
	@Override
	public void registerRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);
		
		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.dirt);
		r.setIngredient('B', MaterialData.stick);
		r.setIngredient('C', MaterialData.feather);
		
		SpoutManager.getMaterialManager().registerSpoutRecipe(r);
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

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
	
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