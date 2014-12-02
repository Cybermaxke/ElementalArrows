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
package me.cybermaxke.elementarrows.forge.v1710.entity;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.Entity;

/**
 * This is a work around to store client and server entities. Because the
 * hashCode returns the entity id and that caused trouble.
 */
@SideOnly(Side.CLIENT)
public class FEntityFactoryClient extends FEntityFactory {
	private final Map<Entity, FEntity<?>> wrappersClient = new HashMap<Entity, FEntity<?>>();

	@Override
	protected Map<Entity, FEntity<?>> getLookupFor(Entity entity) {
		return entity.worldObj.isRemote ? this.wrappersClient : super.getLookupFor(entity);
	}

}