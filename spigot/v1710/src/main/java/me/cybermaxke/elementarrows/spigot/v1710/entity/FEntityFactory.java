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
package me.cybermaxke.elementarrows.spigot.v1710.entity;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityAgeable;
import net.minecraft.server.v1_7_R4.EntityArrow;
import net.minecraft.server.v1_7_R4.EntityChicken;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntityZombie;
import net.minecraft.server.v1_7_R4.IProjectile;
import net.minecraft.server.v1_7_R4.World;

import com.google.common.base.Preconditions;
import me.cybermaxke.elementarrows.common.entity.EntityFactory;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.spigot.v1710.world.FWorld;

public class FEntityFactory implements EntityFactory {
	private final Map<Entity, FEntity> wrappers = new WeakHashMap<Entity, FEntity>();

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

		if (this.wrappers.containsKey(entity)) {
			return (V) this.wrappers.get(entity);
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
			entity0 = new FEntityAgeable((EntityLiving) entity);
		} else if (entity instanceof EntityLiving) {
			entity0 = new FEntityLiving((EntityLiving) entity);
		} else {
			entity0 = new FEntity(entity);
		}

		/**
		 * Cache the wrapper.
		 */
		this.wrappers.put(entity, entity0);

		return (V) entity0;
	}

}