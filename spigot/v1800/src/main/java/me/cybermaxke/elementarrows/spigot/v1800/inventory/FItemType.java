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
package me.cybermaxke.elementarrows.spigot.v1800.inventory;

import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.MinecraftKey;
import me.cybermaxke.elementarrows.common.item.type.ItemType;

public class FItemType implements ItemType {
	public final Item item;
	public final String id;
	public final int internalId;

	public FItemType(Item item) {
		this.internalId = Item.REGISTRY.b(this.item);
		this.id = ((MinecraftKey) Item.REGISTRY.c(this.item)).toString();
		this.item = item;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getInternalId() {
		return this.internalId;
	}

	@Override
	public boolean hasDurability() {
		return this.item.usesDurability();
	}

	@Override
	public int getMaxDurability() {
		return this.item.getMaxDurability();
	}

	@Override
	public int getMaxStackSize() {
		return this.item.getMaxStackSize();
	}

}