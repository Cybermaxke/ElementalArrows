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
package me.cybermaxke.elementarrows.spigot.v1710.world;

import java.util.Random;

import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.World;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.spigot.v1710.FElementArrows;
import me.cybermaxke.elementarrows.spigot.v1710.entity.FEntity;

public class FWorld implements me.cybermaxke.elementarrows.common.world.World {
	public final FWorldLightning lightning = new FWorldLightning();

	/**
	 * Weak reference to track if the world is unloaded.
	 */
	public World world;

	/**
	 * The name of the world.
	 */
	public String name;

	public FWorld(World world) {
		this.world = world;
		this.name = world.getWorld().getName();
	}

	@Override
	public void spawnLightning(Vector position, boolean placeFire) {
		Preconditions.checkNotNull(position);

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();

		this.lightning.spawnLightningAt(this.world, x, y, z, placeFire);
	}

	@Override
	public void spawnLightning(Vector position) {
		this.spawnLightning(position, true);
	}

	@Override
	public boolean isAir(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return this.world.isEmpty(x, y, z);
	}

	@Override
	public String getBlockType(Vector position) {
		Preconditions.checkNotNull(position);

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		return Block.REGISTRY.c(this.world.getType(x, y, z));
	}

	@Override
	public void setBlock(Vector position, String type, int data, int flags) {
		Preconditions.checkNotNull(position);
		Preconditions.checkNotNull(type);

		Block block = (Block) Block.REGISTRY.get(type);

		if (block ==  null) {
			throw new IllegalArgumentException("Unknown block type! (" + type + ")");
		}

		int x = position.getBlockX();
		int y = position.getBlockY();
		int z = position.getBlockZ();

		this.world.setTypeAndData(x, y, z, block, data, flags);
	}

	@Override
	public void setBlock(Vector position, String type, int data) {
		this.setBlock(position, type, data, me.cybermaxke.elementarrows.common.world.World.All);
	}

	@Override
	public void addEntity(me.cybermaxke.elementarrows.common.entity.Entity entity) {
		Preconditions.checkNotNull(entity);

		this.world.addEntity(((FEntity<?>) entity).entity);
	}

	@Override
	public String getName() {
		return this.world.getWorld().getName();
	}

	@Override
	public boolean isRemote() {
		return this.world.isStatic;
	}

	@Override
	public Random getRandom() {
		return this.world.random;
	}

	@Override
	public void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks, me.cybermaxke.elementarrows.common.entity.Entity source) {
		Preconditions.checkNotNull(position);
		Entity source0 = source == null ? null : ((FEntity) source).entity;

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();

		this.world.createExplosion(source0, x, y, z, power, placeFire, destroyBlocks);
	}

	@Override
	public void createExplosion(Vector position, float power, boolean placeFire, boolean destroyBlocks) {
		this.createExplosion(position, power, placeFire, destroyBlocks, null);
	}

	/**
	 * Gets the forge world for the minecraft world.
	 * 
	 * @param world the minecraft world
	 * @return the world
	 */
	public static FWorld of(World world) {
		return FElementArrows.worlds.of(world);
	}

}