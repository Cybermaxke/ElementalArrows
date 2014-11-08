package me.cybermaxke.elementarrows.forge.entity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrowRegistry;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowHitEntityEvent;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public final class EntityElementArrowListener {
	private final ElementArrowRegistry registry;

	public EntityElementArrowListener(ElementArrowRegistry registry) {
		this.registry = registry;
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		DamageSource source = event.source;

		if (source.isProjectile()) {
			Entity entity = source.getSourceOfDamage();
			if (entity instanceof EntityElementArrow) {
				EntityElementArrow entity0 = (EntityElementArrow) entity;

				short data = entity0.getElementData();
				if (data != 0) {
					ElementArrow arrow = this.registry.fromData(data);
					if (arrow != null) {
						arrow.onArrowHitEntity(new ArrowHitEntityEvent(entity0, entity0.source, event.entityLiving, source));
					}
				}
			}
		}
	}

}