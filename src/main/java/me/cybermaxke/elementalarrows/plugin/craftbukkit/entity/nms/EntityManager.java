package me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms;

import java.lang.reflect.Method;

import net.minecraft.server.v1_5_R2.EntityTypes;

import org.bukkit.entity.EntityType;

public class EntityManager {

	public EntityManager() {
		try {
			Method m = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			m.setAccessible(true);

			m.invoke(m, EntityElementalArrow.class, "ElementalArrow", EntityType.ARROW.ordinal());
		} catch (Exception e) {}
	}
}