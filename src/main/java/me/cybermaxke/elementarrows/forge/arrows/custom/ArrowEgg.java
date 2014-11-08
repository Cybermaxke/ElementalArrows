package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowEgg extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsEgg";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowEgg";
		this.texture = "elementArrows:textures/entity/arrowEntityEgg.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		event.arrow.setElementData(0);

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		this.spawnChickenAt(event.arrow.worldObj, x, y, z);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		event.arrow.setElementData(0);

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		this.spawnChickenAt(event.arrow.worldObj, x, y, z);
	}

	public void spawnChickenAt(World world, double x, double y, double z) {
		EntityChicken chicken = new EntityChicken(world);
		chicken.setPositionAndRotation(x, y, z, world.rand.nextFloat() * 360f, 0);
		chicken.setGrowingAge(-24000);

		world.spawnEntityInWorld(chicken);
	}

}