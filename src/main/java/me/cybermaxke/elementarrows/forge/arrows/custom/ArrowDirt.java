package me.cybermaxke.elementarrows.forge.arrows.custom;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowDirt extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsDirt";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowDirt";
		this.texture = "elementArrows:textures/entity/arrowEntityDirt.png";
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setDamage(event.arrow.getDamage() * 1.3d);
		event.arrow.setKnockbackStrength(2);
	}

}