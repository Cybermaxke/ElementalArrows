package me.cybermaxke.elementarrows.forge.arrows.custom;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public final class ArrowExplosion extends ElementArrow {

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsExplosion";

		GameRegistry.addShapelessRecipe(
				new ItemStack(event.itemArrow, 1, event.data),
				new ItemStack(Blocks.tnt, 1, 0),
				new ItemStack(event.itemArrow, 1, 0));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowExplosion";
		this.texture = "elementArrows:textures/entity/arrowEntityExplosion.png";
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		boolean griefing = event.entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		event.entity.worldObj.createExplosion(event.entity, x, y, z, 2.5f, griefing);
		event.arrow.canBePickedUp = 2;
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		boolean griefing = event.arrow.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

		double x = event.arrow.posX;
		double y = event.arrow.posY;
		double z = event.arrow.posZ;

		event.arrow.worldObj.createExplosion(event.arrow, x, y, z, 2.5f, griefing);
		event.arrow.setElementData(0);
		event.arrow.canBePickedUp = 2;
	}

}