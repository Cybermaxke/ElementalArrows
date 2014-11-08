package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowRazor extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsRazor";

		GameRegistry.addShapedRecipe(new ItemStack(event.itemArrow, 1, event.data), "x", "y", "z",
				'x', new ItemStack(Items.iron_ingot),
				'y', new ItemStack(Items.stick),
				'z', new ItemStack(Items.feather));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowRazor";
		this.texture = "elementArrows:textures/entity/arrowEntityRazor.png";
	}

	@Override
	public void onArrowBuild(ArrowBuildEvent event) {
		/**
		 * Modify the power (speed)
		 */
		event.power *= 1.7f;

		/**
		 * Let the underlying method build the arrow.
		 */
		super.onArrowBuild(event);
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setKnockbackStrength(2);
	}

}