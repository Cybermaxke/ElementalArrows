package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public abstract class CustomArrowItem extends GenericCustomItem {

	private int damage = 0;
	private int knockback = 0;
	private int fireticks = 0;

	public CustomArrowItem(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		SpoutManager.getFileManager().addToCache(plugin, texture);
	}
	
	public void setDamage(int amount) {
		this.damage = amount;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setKnockback(int amount) {
		this.knockback = amount;
	}
	
	public int getKnockback() {
		return this.knockback;
	}
	
	public void setFireTicks(int amount) {
		this.fireticks = amount;
	}
	
	public int getFireTicks() {
		return this.fireticks;
	}
	
	public abstract void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow);
	
	public abstract void onHit(Player shooter, ArrowEntity arrow);
	
	public abstract void onShoot(Player shooter, ArrowEntity arrow);
}