package me.cybermaxke.elementarrows.forge.entity;

import java.util.Map;

import me.cybermaxke.elementarrows.forge.util.UtilFields;

import net.minecraft.entity.EntityList;

@SuppressWarnings("unchecked")
public final class EntityRegistry {

	/**
	 * Register or override a entity type with a specific entity id and protocol id.
	 * 
	 * @param entity the entity type
	 * @param id the id (can be overridden)
	 * @param protocolId the protocol id
	 */
	
	public void register(Class<?> entity, String id, int protocolId) {
		Object[] maps = UtilFields.findFieldsAndGet(EntityList.class, Map.class, null);

		((Map<Object, Object>) maps[0]).put(id, entity);
		((Map<Object, Object>) maps[1]).put(entity, id);
		((Map<Object, Object>) maps[2]).put(protocolId, entity);
		((Map<Object, Object>) maps[3]).put(entity, protocolId);
		((Map<Object, Object>) maps[4]).put(id, protocolId);
	}

}