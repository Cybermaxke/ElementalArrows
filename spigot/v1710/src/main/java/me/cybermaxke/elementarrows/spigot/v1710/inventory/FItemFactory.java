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
package me.cybermaxke.elementarrows.spigot.v1710.inventory;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemArmor;
import net.minecraft.server.v1_7_R4.ItemBow;
import net.minecraft.server.v1_7_R4.ItemFishingRod;
import net.minecraft.server.v1_7_R4.ItemHoe;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.ItemSword;
import net.minecraft.server.v1_7_R4.ItemTool;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.item.ItemFactory;
import me.cybermaxke.elementarrows.common.item.type.ItemType;

public class FItemFactory implements ItemFactory {
	private final Map<Item, FItemType> items = new WeakHashMap<Item, FItemType>();

	@Override
	public ItemType typeById(String id) {
		Preconditions.checkNotNull(id);
		Item item = (Item) Item.REGISTRY.get(id);
		Preconditions.checkNotNull(item, "Unknown item type! (" + id + ")");

		return this.of(item);
	}

	@Override
	public ItemType typeById(int internalId) {
		Item item = (Item) Item.getById(internalId);
		Preconditions.checkNotNull(item, "Unknown item type! (" + internalId + ")");

		return this.of(item);
	}

	@Override
	public FItemStack of(ItemType type, int quantity, int data) {
		Preconditions.checkNotNull(type);
		Preconditions.checkState(quantity > 0, "The quantity has to be greater then zero!");

		return new FItemStack(new ItemStack(((FItemType) type).item, quantity, data));
	}

	@Override
	public FItemStack of(ItemType type, int quantity) {
		return this.of(type, quantity, 0);
	}

	@Override
	public FItemStack of(ItemType type) {
		return this.of(type, 1);
	}

	public FItemType of(Item item) {
		if (this.items.containsKey(item)) {
			return this.items.get(item);
		}

		FItemType type;

		if (item instanceof ItemArmor) {
			type = new FItemArmor(item);
		} else if (item instanceof ItemBow) {
			type = new FItemBow(item);
		} else if (item instanceof ItemFishingRod) {
			type = new FItemFishingRod(item);
		} else if (item instanceof ItemSword) {
			type = new FItemSword(item);
		} else if (item instanceof ItemTool) {
			type = new FItemDigging(item);
		} else if (item instanceof ItemHoe) {
			type = new FItemTool(item);
		} else {
			type = new FItemType(item);
		}

		this.items.put(item, type);
		return type;
	}

}