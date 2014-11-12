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
package me.cybermaxke.elementarrows.forge.entity;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.AttributeModifier;

public final class EntityAttribute {
	/**
	 * The unique id of the frozen attribute modifier.
	 */
	public static final UUID FrozenUUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	/**
	 * The frozen attribute modifier.
	 */
	public static final AttributeModifier FrozenModifier = new AttributeModifier(EntityAttribute.FrozenUUID, "Frozen", -1d, 2);

	private EntityAttribute() {}
}