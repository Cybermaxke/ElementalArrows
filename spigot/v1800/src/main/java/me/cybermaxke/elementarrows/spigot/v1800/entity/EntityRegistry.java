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
package me.cybermaxke.elementarrows.spigot.v1800.entity;

import java.util.Map;

import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import net.minecraft.server.v1_8_R1.EntityTypes;

public class EntityRegistry {

	/**
	 * Register or override a entity type with a specific entity id and protocol id.
	 * 
	 * @param entity the entity type
	 * @param id the id (can be overridden)
	 * @param protocolId the protocol id
	 */
	public void register(Class<?> entity, String id, int protocolId) {
		Object[] maps = Fields.findFieldsAndGet(EntityTypes.class, Map.class, null);

		((Map<Object, Object>) maps[0]).put(id, entity);
		((Map<Object, Object>) maps[1]).put(entity, id);
		((Map<Object, Object>) maps[2]).put(protocolId, entity);
		((Map<Object, Object>) maps[3]).put(entity, protocolId);
		((Map<Object, Object>) maps[4]).put(id, protocolId);
	}

}