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

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

import net.minecraft.server.v1_7_R4.World;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.world.WorldManager;

public class FWorldManager implements WorldManager {
	private Map<World, FWorld> worlds = new WeakHashMap<World, FWorld>();

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

	@Override
	public FWorld get(String name) {
		Preconditions.checkNotNull(name);

		/**
		 * We will only use lower case names.
		 */
		name = name.toLowerCase();
		World world = findWorld(name);

		if (world != null) {
			return this.get(name);
		} else {
			return null;
		}
	}

	public static World findWorld(String name) {
		org.bukkit.World world0 = Bukkit.getWorld(name);

		if (world0 != null) {
			return ((CraftWorld) world0).getHandle();
		}

		return null;
	}
}