/**
 * 
 * This software is part of the ElementalArrows
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.plugin.entity.nms;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Biome;

import me.cybermaxke.elementalarrows.plugin.config.ElementalConfigFile;
import net.minecraft.server.v1_5_R3.EntitySkeleton;
import net.minecraft.server.v1_5_R3.BiomeBase;
import net.minecraft.server.v1_5_R3.BiomeMeta;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityTypes;

public class EntityManager {

	public EntityManager() {
		try {
			Method m = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			m.setAccessible(true);

			m.invoke(m, EntityElementalArrow.class, "ElementalArrow", 10);
			m.invoke(m, EntityElementalSkeleton.class, "ElementalSkeleton", 51);
			m.invoke(m, EntityElementalTurret.class, "ElementalTurret", 200);
			m.invoke(m, EntitySkeleton.class, "Skeleton", 51);
		} catch (Exception e) {}

		if (ElementalConfigFile.ENABLE_ELEMENTAL_SKELETON_SPAWNING) {
			for (Biome b : getBiomes(EntitySkeleton.class)) {
				addToBiome(EntityElementalSkeleton.class, b, 10, 4, 4);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> List<Biome> getBiomes(Class<T> entity) {
		List<Biome> biomes = new ArrayList<Biome>();
		for (Biome b : Biome.values()) {
			try {
				BiomeBase bio = BiomeBase.biomes[b.ordinal()];
				Field f = BiomeBase.class.getDeclaredField("J");
				f.setAccessible(true);
				List<BiomeMeta> l = new ArrayList<BiomeMeta>();
				l.addAll((List<BiomeMeta>) f.get(bio));
				for (BiomeMeta m : l) {
					if (m.b.equals(entity)) {
						biomes.add(b);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return biomes;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Entity> void addToBiome(Class<T> entity, Biome biome, int weight, int minGroupCount, int maxGroupCount) {
		try {
			BiomeBase b = BiomeBase.biomes[biome.ordinal()];
			Field f = BiomeBase.class.getDeclaredField("J");
			f.setAccessible(true);
			((List<BiomeMeta>) f.get(b)).add(new BiomeMeta(entity, weight, minGroupCount, maxGroupCount));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}