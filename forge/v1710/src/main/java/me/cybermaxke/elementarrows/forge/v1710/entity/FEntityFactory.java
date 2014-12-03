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
package me.cybermaxke.elementarrows.forge.v1710.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Preconditions;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import me.cybermaxke.elementarrows.common.entity.EntityFactory;
import me.cybermaxke.elementarrows.common.entity.EntityTickHandler;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.forge.v1710.world.FWorld;

public class FEntityFactory implements EntityFactory {
	private final UUID frozenUUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private final AttributeModifier frozenModifier = new AttributeModifier(this.frozenUUID, "Frozen", -1d, 2);

	private final Map<Entity, FEntity<?>> wrappers = new HashMap<Entity, FEntity<?>>();
	private final Set<EntityTickHandler> handlers = new HashSet<EntityTickHandler>();

	/**
	 * Initializes the entity factory.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}

	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		FEntity entity = FEntity.of(event.entity);

		if (entity.properties.get(me.cybermaxke.elementarrows.common.entity.Entity.FrozenMovement)) {
			event.entity.motionY = Math.min(event.entity.motionY, 0f);
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		World world = event.world;

		for (EntityTickHandler handler : this.handlers) {
			for (Entity entity : (List<Entity>) world.loadedEntityList) {
				FEntity entity0 = FEntity.of(entity);
				handler.handle(entity0);

				boolean flag = entity0.properties.get(me.cybermaxke.elementarrows.common.entity.Entity.FrozenMovement);

				if (!entity.worldObj.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 3 == 0) {
					IAttributeInstance instance = ((EntityLivingBase) entity).getEntityAttribute(SharedMonsterAttributes.movementSpeed);

					if (!flag) {
						instance.removeModifier(this.frozenModifier);
					} else if (instance.getModifier(this.frozenUUID) == null) {
						instance.applyModifier(this.frozenModifier);
					}
				}

				if (entity0.properties.get(me.cybermaxke.elementarrows.common.entity.Entity.FrozenMovement)) {
					entity.posX = entity.lastTickPosX;
					entity.posY = entity.lastTickPosY;
					entity.posZ = entity.lastTickPosZ;
					entity.rotationYaw = entity.prevRotationYaw;
					entity.rotationPitch = entity.prevRotationPitch;
					entity.motionX = 0d;
					entity.motionY = Math.min(entity.motionY, 0f);
					entity.motionZ = 0d;
				}
			}
		}
	}

	@Override
	public <T extends me.cybermaxke.elementarrows.common.entity.Entity> T create(Class<T> type, me.cybermaxke.elementarrows.common.world.World world, Vector position) {
		Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(world);
		Preconditions.checkNotNull(position);

		T entity = this.create(type, world);
		entity.setPosition(position);

		return entity;
	}

	@Override
	public <T extends me.cybermaxke.elementarrows.common.entity.Entity> T create(Class<T> type, me.cybermaxke.elementarrows.common.world.World world) {
		Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(world);

		World world0 = ((FWorld) world).world;
		Entity entity;

		if (type.isAssignableFrom(me.cybermaxke.elementarrows.common.entity.EntityChicken.class)) {
			entity = new EntityChicken(world0);
		} else if (type.isAssignableFrom(me.cybermaxke.elementarrows.common.entity.EntityArrow.class)) {
			entity = new EntityElementArrow(world0);
		} else {
			throw new IllegalArgumentException("Unsupported entity type! (" + type.getName() + ")");
		}

		return (T) this.of(entity);
	}

	@Override
	public void addTickHandler(EntityTickHandler handler) {
		Preconditions.checkNotNull(handler);
		this.handlers.add(handler);
	}

	@Override
	public boolean removeTickHandler(EntityTickHandler handler) {
		Preconditions.checkNotNull(handler);
		return this.handlers.remove(handler);
	}

	/**
	 * Gets the wrapper for the minecraft entity.
	 * 
	 * @param entity the minecraft entity
	 * @return the wrapper
	 */
	public <V extends FEntity> V of(Entity entity) {
		if (entity == null) {
			return null;
		}

		Map<Entity, FEntity<?>> map = this.getLookupFor(entity);
		if (map.containsKey(entity)) {
			return (V) map.get(entity);
		}

		FEntity entity0;

		if (entity instanceof EntityArrow) {
			entity0 = new FEntityArrow((EntityArrow) entity);
		} else if (entity instanceof IProjectile) {
			entity0 = new FEntityProjectile((Entity) entity);
		} else if (entity instanceof EntityPlayer) {
			entity0 = new FEntityPlayer((EntityPlayer) entity);
		} else if (entity instanceof EntityChicken) {
			entity0 = new FEntityChicken((EntityChicken) entity);
		} else if (entity instanceof EntityAgeable || entity instanceof EntityZombie) {
			entity0 = new FEntityAgeable((EntityLivingBase) entity);
		} else if (entity instanceof EntityLivingBase) {
			entity0 = new FEntityLiving((EntityLivingBase) entity);
		} else {
			entity0 = new FEntity(entity);
		}

		/**
		 * Cache the wrapper.
		 */
		map.put(entity, entity0);

		return (V) entity0;
	}

	/**
	 * Gets the lookup map for the entity.
	 * 
	 * @param entity the entity
	 * @return the lookup map
	 */
	protected Map<Entity, FEntity<?>> getLookupFor(Entity entity) {
		return this.wrappers;
	}

}