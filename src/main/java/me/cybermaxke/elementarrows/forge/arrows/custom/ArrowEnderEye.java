package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowEnderEye extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsEnderEye";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowEnderEye";
		this.texture = "elementArrows:textures/entity/arrowEntityEnderEye.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		this.onArrowHit(event.arrow);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		this.onArrowHit(event.arrow);
	}

	public void onArrowHit(EntityElementArrow arrow) {
		Entity shooter = arrow.shootingEntity;

		if (shooter == null) {
			return;
		}

		if (shooter.isRiding()) {
			shooter.mountEntity(null);
		}

		shooter.setPosition(arrow.posX, arrow.posY, arrow.posZ);
		shooter.fallDistance = 0f;

		if (shooter instanceof EntityPlayerMP) {
			((EntityPlayerMP) shooter).setPositionAndUpdate(arrow.posX, arrow.posY, arrow.posZ);
		}

		arrow.setElementData(0);
	}

}