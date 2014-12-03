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

import java.util.Collection;

import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;

import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.util.com.google.common.base.Preconditions;
import me.cybermaxke.elementarrows.common.item.inventory.Inventory;

public class FInventory implements Inventory {
	public final IInventory inventory;

	public FInventory(IInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public int getSize() {
		return this.inventory.getSize();
	}

	@Override
	public FItemStack getAt(int index) {
		return FItemStack.of(this.inventory.getItem(index));
	}

	@Override
	public void setAt(int index, me.cybermaxke.elementarrows.common.item.inventory.ItemStack itemStack) {
		ItemStack itemStack0 = null;

		if (itemStack != null) {
			itemStack0 = ((FItemStack) itemStack).itemStack;
		}

		this.inventory.setItem(index, itemStack0);
	}

	@Override
	public me.cybermaxke.elementarrows.common.item.inventory.ItemStack[] getContents() {
		me.cybermaxke.elementarrows.common.item.inventory.ItemStack[] itemStacks = new me.cybermaxke.elementarrows.common.item.inventory.ItemStack[this.inventory.getSize()];

		for (int i = 0; i < itemStacks.length; i++) {
			itemStacks[i] = this.getAt(i);
		}

		return itemStacks;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.getSize(); i++) {
			this.inventory.setItem(i, null);
		}
	}

	@Override
	public me.cybermaxke.elementarrows.common.item.inventory.ItemStack add(me.cybermaxke.elementarrows.common.item.inventory.ItemStack itemStack) {
		Preconditions.checkNotNull(itemStack);
		ItemStack itemStack0 = ((FItemStack) itemStack).itemStack;

		org.bukkit.inventory.Inventory inventory = this.inventory.getOwner().getInventory();
		org.bukkit.inventory.ItemStack itemStack1 = CraftItemStack.asCraftMirror(itemStack0);

		Collection<org.bukkit.inventory.ItemStack> rest = inventory.addItem(itemStack1).values();

		if (!rest.isEmpty()) {
			return FItemStack.of(CraftItemStack.asNMSCopy(rest.iterator().next()));
		} else {
			return null;
		}
	}

	public int getFirstEmptySlotIndex() {
		for (int i = 0; i < this.inventory.getSize(); i++) {
			if (this.inventory.getItem(i) == null) {
				return i;
			}
		}

		return -1;
	}

}