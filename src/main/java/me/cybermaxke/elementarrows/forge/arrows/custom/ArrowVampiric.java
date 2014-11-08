package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowVampiric extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsVampiric";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowVampiric";
		this.texture = "elementArrows:textures/entity/arrowEntityVampiric.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		Entity shooter = event.arrow.shootingEntity;

		if (shooter != null && shooter instanceof EntityLivingBase) {
			((EntityLivingBase) shooter).addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 12));
		}

		int x = (int) Math.round(event.arrow.posX);
		int y = (int) Math.round(event.arrow.posY);
		int z = (int) Math.round(event.arrow.posZ);

		shooter.worldObj.playAuxSFX(2002, x, y, z, 16396);
	}

}