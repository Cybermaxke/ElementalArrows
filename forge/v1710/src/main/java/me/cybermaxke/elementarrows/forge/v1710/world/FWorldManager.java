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
package me.cybermaxke.elementarrows.forge.v1710.world;

import java.util.Map;
import java.util.WeakHashMap;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.world.WorldManager;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class FWorldManager implements WorldManager {
	private final Map<World, FWorld> worlds = new WeakHashMap<World, FWorld>();

	@Override
	public FWorld get(String name) {
		Preconditions.checkNotNull(name);

		return this.of(findWorld(name));
	}

	/**
	 * Gets the forge world for the minecraft world.
	 * 
	 * @param world the minecraft world
	 * @return the world
	 */
	public FWorld of(World world) {
		Preconditions.checkNotNull(world);

		if (this.worlds.containsKey(world)) {
			return this.worlds.get(world);
		}

		FWorld world0 = new FWorld(world);
		this.worlds.put(world, world0);

		return world0;
	}

	public static World findWorld(String name) {
		WorldServer[] worlds = MinecraftServer.getServer().worldServers;

		if (worlds.length > 0) {
			for (WorldServer world : worlds) {
				if (getFixedName(world).equalsIgnoreCase(name)) {
					return world;
				}
			}
		}

		return null;
	}

	public static String getFixedName(World world) {
		String name = world.getWorldInfo().getWorldName();

		if (name == null) {
			if (world.isRemote) {
				return "worldClient";
			} else {
				return "worldServer_" + world.provider.dimensionId;
 			}
		} else {
			return name;
		}
	}

}