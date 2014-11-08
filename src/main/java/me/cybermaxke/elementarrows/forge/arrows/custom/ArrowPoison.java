package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowPoison extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsPoison";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Items.spider_eye),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowPoison";
		this.texture = "elementArrows:textures/entity/arrowEntityPoison.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.entity.addPotionEffect(new PotionEffect(Potion.poison.id, 70, 9));
	}

}