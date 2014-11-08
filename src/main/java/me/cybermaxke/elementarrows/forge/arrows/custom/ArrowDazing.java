package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDazing extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDazing";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Blocks.red_mushroom, 1, 0),
				new ItemStack(Blocks.brown_mushroom, 1, 0),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDazing";
		this.texture = "elementArrows:textures/entity/arrowEntityDazing.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 75, 12));
	}

}