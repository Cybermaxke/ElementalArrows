package me.cybermaxke.elementarrows.forge.arrows.custom;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowFire extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsFire";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowFire";
		this.texture = "elementArrows:textures/entity/arrowEntityFire.png";
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setFire(100000);
		event.arrow.canBePickedUp = 2;
	}

}