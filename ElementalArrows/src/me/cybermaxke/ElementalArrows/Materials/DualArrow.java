package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapelessRecipe;
import org.getspout.spoutapi.material.MaterialData;

public class DualArrow extends CustomArrowItem {

	public DualArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		this.setMultiplePerShot(2);
		this.setArrowDrop(new ItemStack(Material.ARROW));
	}
	
	@Override
	public void registerRecipes() {
		SpoutItemStack i = new SpoutItemStack(this, 4);
		
		SpoutShapelessRecipe r = new SpoutShapelessRecipe(i);
		r.addIngredient(2, MaterialData.arrow);
		
		SpoutManager.getMaterialManager().registerSpoutRecipe(r);
	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {

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
}