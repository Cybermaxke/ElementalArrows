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
package me.cybermaxke.elementarrows.forge.v1710.inventory;

import com.google.common.base.Preconditions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import me.cybermaxke.elementarrows.common.inventory.ItemFactory;

public class FItemFactory implements ItemFactory {

	@Override
	public FItemStack of(String type, int quantity, int data) {
		Preconditions.checkNotNull(type);

		Item item = (Item) Item.itemRegistry.getObject(type);

		Preconditions.checkNotNull(item, "Unknown item type! (" + type + ")");
		Preconditions.checkState(quantity > 0, "The quantity has to be greater then zero!");

		return new FItemStack(new ItemStack(item, quantity, data));
	}

	@Override
	public FItemStack of(String type, int quantity) {
		return this.of(type, quantity, 0);
	}

	@Override
	public FItemStack of(String type) {
		return this.of(type, 1);
	}

}