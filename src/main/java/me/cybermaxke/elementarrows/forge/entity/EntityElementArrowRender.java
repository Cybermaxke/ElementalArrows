package me.cybermaxke.elementarrows.forge.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public final class EntityElementArrowRender extends RenderArrow {
	private final static ResourceLocation[] resources = new ResourceLocation[Short.MAX_VALUE];

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f01, float f02) {
		this.doRender((EntityArrow) entity, x, y, z, f01, f02);
	}

	@Override
	public void doRender(EntityArrow entity, double x, double y, double z, float f01, float f02) {
		super.doRender(entity, x, y, z, f01, f02);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityArrow) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityArrow entity) {
		short data = ((EntityElementArrow) entity).getElementData();

		if (data != 0) {
			if (EntityElementArrowRender.resources[data] == null) {
				ElementArrow arrow0 = EArrowMod.mod.registry.fromData(data);
				if (arrow0 != null && arrow0.texture != null) {
					EntityElementArrowRender.resources[data] = new ResourceLocation(arrow0.texture);
					return EntityElementArrowRender.resources[data];
				} else {
					return super.getEntityTexture(entity);
				}
			} else {
				return EntityElementArrowRender.resources[data];
			}
		} else {
			return super.getEntityTexture(entity);
		}
	}

}