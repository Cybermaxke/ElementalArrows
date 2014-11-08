package me.cybermaxke.elementarrows.forge.arrows.custom;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowLightning extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsLightning";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowLightning";
		this.texture = "elementArrows:textures/entity/arrowEntityLightning.png";
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
		World world = arrow.worldObj;

		double x = arrow.posX;
		double y = arrow.posY;
		double z = arrow.posZ;

		world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
		arrow.setElementData(0);
	}

}