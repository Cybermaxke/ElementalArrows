package me.cybermaxke.elementalarrows.plugin.libigot.entity.nms;

import java.lang.reflect.Method;

import org.bukkit.entity.EntityType;

import net.minecraft.server.EntityTypes;

public class EntityManager {

	public EntityManager() {
		try {
			Method m = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
			m.setAccessible(true);

			m.invoke(m, EntityElementalArrow.class, "ElementalArrow", EntityType.ARROW.ordinal());
		} catch (Exception e) {}
	}
}