package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public abstract class CustomArrowItem extends GenericCustomItem {

	private int damage = 0;
	private int knockback = 0;
	private int fireticks = 0;
	private boolean canpickup = true;
	private int amountpershot = 1;
	private ItemStack drop;

	public CustomArrowItem(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);	
		SpoutManager.getFileManager().addToCache(plugin, texture);
		this.registerRecipes();
		
		this.drop = new SpoutItemStack(this);
	}
	
	public void setMultiplePerShot(int amount) {
		if (amount > 0)
			this.amountpershot = amount;
		else 
			throw new IllegalArgumentException("The amount can't be 0!");
	}
	
	public int getMultiplePerShot() {
		return this.amountpershot;
	}
	
	public boolean canPickup() {
		return this.canpickup;
	}
	
	public void setCanPickup(boolean pickup) {
		this.canpickup = pickup;
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
	
	public void setArrowDrop(ItemStack itemstack) {
		if (itemstack != null)
			this.drop = itemstack;
		else
			throw new NullPointerException("The arrow drop can't be null!");
	}
	
	public ItemStack getArrowDrop() {
		return this.drop;
	}
	
	public abstract void registerRecipes();
	
	public abstract void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow);
	
	public abstract void onHit(Player shooter, ArrowEntity arrow);
	
	public abstract void onShoot(Player shooter, ArrowEntity arrow);
	
	public abstract void onTick(Player shooter, ArrowEntity arrow);
}