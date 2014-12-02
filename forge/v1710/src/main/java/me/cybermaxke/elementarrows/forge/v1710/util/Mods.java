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
package me.cybermaxke.elementarrows.forge.v1710.util;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.Mod;

public final class Mods {
	private final static Map<Class<?>, Mod> mods = new HashMap<Class<?>, Mod>();

	private Mods() {}

	/**
	 * Gets the version for the mod of the type.
	 * 
	 * @param type the mod type
	 * @return the version
	 */
	public static String getVersionFor(Class<?> type) {
		return Mods.getModInfoFor(type).version();
	}

	/**
	 * Gets the id for the mod of the type.
	 * 
	 * @param type the mod type
	 * @return the id
	 */
	public static String getIdFor(Class<?> type) {
		return Mods.getModInfoFor(type).modid();
	}

	/**
	 * Gets the name for the mod of the type.
	 * 
	 * @param type the mod type
	 * @return the name
	 */
	public static String getNameFor(Class<?> type) {
		return Mods.getModInfoFor(type).name();
	}

	private static Mod getModInfoFor(Class<?> type) {
		if (Mods.mods.containsKey(type)) {
			return Mods.mods.get(type);
		}

		Mod mod = type.getAnnotation(Mod.class);
		if (mod == null) {
			throw new IllegalArgumentException("Invalid mod type! (" + type.getName() + ")");
		}

		return mod;
	}
}