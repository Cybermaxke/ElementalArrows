package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

public final class ArrowBlindness extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsBlindness";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Items.dye, 1, 0),
				new ItemStack(Items.dye, 1, 0),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowBlindness";
		this.texture = "elementArrows:textures/entity/arrowEntityBlindness.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 75, 12));
	}

}