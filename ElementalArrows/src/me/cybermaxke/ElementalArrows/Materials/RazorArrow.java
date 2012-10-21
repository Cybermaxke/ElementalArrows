package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RazorArrow extends CustomArrowItem {

	public RazorArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setKnockback(2);
		this.setDamage(2);
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
		
		a.setVelocity(a.getVelocity().multiply(2.3));
	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {
		
	}
}