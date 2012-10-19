package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EggArrow extends CustomArrowItem {

	public EggArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {
		
	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {
		Arrow a = (Arrow) arrow.getBukkitEntity();
		Chicken c = (Chicken) a.getWorld().spawnEntity(a.getLocation(), EntityType.CHICKEN);
		
		c.setBaby();
	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {
		
	}
}