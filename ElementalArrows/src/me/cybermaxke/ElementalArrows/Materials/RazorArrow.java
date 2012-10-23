package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RazorArrow extends CustomArrowItem {

	public RazorArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		
		this.addConfigData("Knockback", 2);
		this.addConfigData("DamageBonus", 2);
		this.addConfigData("SpeedMultiplier", 2.3);
		
		this.setKnockback((Integer) this.getConfigData("Knockback"));
		this.setDamage((Integer) this.getConfigData("DamageBonus"));
	}
	
	@Override
	public void registerRecipes() {
		
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {

	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {

	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {
		Arrow a = (Arrow) arrow.getBukkitEntity();
		
		a.setVelocity(a.getVelocity().multiply((Double) this.getConfigData("SpeedMultiplier")));
	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
		
	}
}