/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.forge.arrows.custom;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.entity.EntityAttribute;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.json.JsonField;
import me.cybermaxke.elementarrows.forge.util.Clones;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The frozen attributes are automatically synchronized between client <-> server.
 */
public final class ArrowIce extends ElementArrow {
	/**
	 * The times of how long the entities will be frozen.
	 */
	private Map<UUID, Integer> frozen;

	@JsonField("frozenChance")
	private float frozenChance = 0.25f;

	@JsonField("frozenPotionEffect")
	private PotionEffect frozenPotionEffect = new PotionEffect(Potion.moveSlowdown.id, 100, 6);

	@JsonField("slownPotionEffect")
	private PotionEffect slownPotionEffect = new PotionEffect(Potion.moveSlowdown.id, 80, 0);

	@Override
	public void onInit(ArrowInitEvent event) {
		this.unlocalizedName = "elementArrowsIce";
		this.frozen = new HashMap<UUID, Integer>();

		/**
		 * Register the update event.
		 */
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {
		this.icon = "elementArrows:arrowIce";
		this.texture = "elementArrows:textures/entity/arrowEntityIce.png";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onArrowClientTick(ArrowTickEvent event) {
		EntityElementArrow arrow = event.arrow;

		double mx = arrow.lastMotX;
		double my = arrow.lastMotY;
		double mz = arrow.lastMotZ;

		double x = arrow.prevPosX;
		double y = arrow.prevPosY;
		double z = arrow.prevPosZ;

		String name = "blockdust_" + Block.getIdFromBlock(Blocks.ice) + "_0";

		for (int i = 0; i < 4; i++) {
			arrow.worldObj.spawnParticle(name, x + mx * i / 4d, y + my * i / 4d, z + mz * i / 4d, -mx * 0.8d, -my * 0.8d, -mz * 0.8d);
		}
	}

	@Override
	public void onArrowShot(ArrowShotEvent event) {
		event.arrow.setDamage(event.arrow.getDamage() * 0.85d);
	}

	@Override
	public void onArrowHitGround(ArrowHitGroundEvent event) {
		this.onHit(event.arrow);
	}

	@Override
	public void onArrowHitEntity(ArrowHitEntityEvent event) {
		this.onHit(event.arrow);

		/**
		 * Only call the hit event on the server side.
		 */
		if (event.entity.worldObj.isRemote) {
			return;
		}

		/**
		 * Only a random chance of actual freezing the entity, to prevent the overpowered arrow.
		 */
		if (event.arrow.worldObj.rand.nextFloat() < this.frozenChance) {
			IAttributeInstance instance = event.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

			if (instance != null) {
				instance.removeModifier(EntityAttribute.FrozenModifier);
				instance.applyModifier(EntityAttribute.FrozenModifier);
			}

			/**
			 * Put the entity in the map.
			 */
			this.frozen.put(event.entity.getUniqueID(), event.entity.ticksExisted);

			/**
			 * Apply the normal slow effect.
			 */
			event.entity.addPotionEffect(Clones.clone(this.frozenPotionEffect));
		} else {
			event.entity.addPotionEffect(Clones.clone(this.slownPotionEffect));
		}
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		/**
		 * This will be called both server/client side.
		 */
		if (event.entity instanceof EntityPlayer) {
			IAttributeInstance instance = event.entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			if (instance.getModifier(EntityAttribute.FrozenUUID) != null) {
				event.entity.motionY = Math.min(event.entity.motionY, 0f);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		UUID uuid = event.entityLiving.getUniqueID();

		/**
		 * The client will only prevent movement, so not handling the frozen time and so on.
		 */
		if (event.entityLiving.worldObj.isRemote) {
			IAttributeInstance instance = event.entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

			if (instance != null && instance.getModifier(EntityAttribute.FrozenUUID) != null) {
				if (!(event.entity instanceof EntityPlayer)) {
					event.entity.posX = event.entity.lastTickPosX;
					event.entity.posY = event.entity.lastTickPosY;
					event.entity.posZ = event.entity.lastTickPosZ;
					event.entity.motionX = 0d;
					event.entity.motionY = 0d;
					event.entity.motionZ = 0d;
				}
			}
		} else {
			if (this.frozen.containsKey(uuid)) {
				/**
				 * Check whether we will update, make sure that this method is called once a tick.
				 */
				int ticks = event.entity.ticksExisted - this.frozen.get(uuid);

				if (ticks >= this.frozenPotionEffect.getDuration()) {
					/**
				 	 * No longer frozen.
				 	 */
					this.frozen.remove(uuid);

					IAttributeInstance instance = event.entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
					if (instance != null) {
						instance.removeModifier(EntityAttribute.FrozenModifier);
					}
				} else {
					/**
				 	 * There isn't another way. The attribute doesn't work for all the entities.
				 	 */
					if (!(event.entity instanceof EntityPlayer)) {
						event.entity.posX = event.entity.lastTickPosX;
						event.entity.posY = event.entity.lastTickPosY;
						event.entity.posZ = event.entity.lastTickPosZ;
						event.entity.motionX = 0d;
						event.entity.motionY = 0d;
						event.entity.motionZ = 0d;
					}
				}
			} else if (event.entity.ticksExisted % 3 == 0){
				/**
			 	 * Why is this even possible? ...
			 	 */
				IAttributeInstance instance = event.entityLiving.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
				if (instance != null && instance.getModifier(EntityAttribute.FrozenUUID) != null) {
					instance.removeModifier(EntityAttribute.FrozenModifier);
				}
			}
		}
	}

	public void onHit(EntityElementArrow arrow) {
		arrow.setElementData(0);

		if (arrow.worldObj.isRemote) {
			Random random = arrow.worldObj.rand;

			double x = arrow.posX;
			double y = arrow.posY;
			double z = arrow.posZ;

			String name = "blockdust_" + Block.getIdFromBlock(Blocks.ice) + "_0";

			for (int i = 0; i < 15; i++) {
				double mx = (random.nextFloat() - 0.5d) * 0.5d;
				double my = (Math.abs(random.nextFloat() - 0.5d)) * 0.5d;
				double mz = (random.nextFloat() - 0.5d) * 0.5d;

				arrow.worldObj.spawnParticle(name, x, y, z, mx, my, mz);
			}
		}
	}

}