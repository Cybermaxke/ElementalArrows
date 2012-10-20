package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

public class DazingArrow extends CustomArrowItem {

	public DazingArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setKnockback(5);
		this.setDamage(1);
		this.setCanPickup(false);
	}
	
	@Override
	public void registerRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 16);
		
		SpoutShapedRecipe r = new SpoutShapedRecipe(i);
		r.shape("A", "B", "C");
		r.setIngredient('A', MaterialData.redMushroom);
		r.setIngredient('B', MaterialData.brownMushroom);
		r.setIngredient('C', MaterialData.arrow);
		
		SpoutManager.getMaterialManager().registerSpoutRecipe(r);
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {
		entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 75, 12));
	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {
		
	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {
		
	}

}
