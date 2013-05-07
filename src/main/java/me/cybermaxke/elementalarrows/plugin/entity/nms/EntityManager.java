package me.cybermaxke.elementalarrows.plugin.entity.nms;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Biome;

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
			m.invoke(m, EntitySkeleton.class, "Skeleton", 51);
		} catch (Exception e) {}

		for (Biome b : getBiomes(EntitySkeleton.class)) {
			addToBiome(EntityElementalSkeleton.class, b, 10, 4, 4);
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