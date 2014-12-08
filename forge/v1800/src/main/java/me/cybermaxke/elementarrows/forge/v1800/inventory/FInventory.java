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
package me.cybermaxke.elementarrows.forge.v1800.inventory;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.item.inventory.Inventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class FInventory implements Inventory {
	public final IInventory inventory;

	public FInventory(IInventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public int getSize() {
		return this.inventory.getSizeInventory();
	}

	@Override
	public FItemStack getAt(int index) {
		return FItemStack.of(this.inventory.getStackInSlot(index));
	}

	@Override
	public void setAt(int index, me.cybermaxke.elementarrows.common.item.inventory.ItemStack itemStack) {
		ItemStack itemStack0 = null;

		if (itemStack != null) {
			itemStack0 = ((FItemStack) itemStack).itemStack;
		}

		this.inventory.setInventorySlotContents(index, itemStack0);
	}

	@Override
	public me.cybermaxke.elementarrows.common.item.inventory.ItemStack[] getContents() {
		me.cybermaxke.elementarrows.common.item.inventory.ItemStack[] itemStacks = new me.cybermaxke.elementarrows.common.item.inventory.ItemStack[this.inventory.getSizeInventory()];

		for (int i = 0; i < itemStacks.length; i++) {
			itemStacks[i] = this.getAt(i);
		}

		return itemStacks;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
			this.inventory.setInventorySlotContents(i, null);
		}
	}

	@Override
	public me.cybermaxke.elementarrows.common.item.inventory.ItemStack add(me.cybermaxke.elementarrows.common.item.inventory.ItemStack itemStack) {
		Preconditions.checkNotNull(itemStack);
		ItemStack itemStack0 = ((FItemStack) itemStack).itemStack;

		int limit = this.inventory.getInventoryStackLimit();

		while (true) {
			int firstPartial = this.firstPartial(itemStack0);

			if (firstPartial == -1) {
				int firstFree = this.firstEmpty();

				if (firstFree == -1) {
					return itemStack;
				}

				if (itemStack0.stackSize > limit) {
					ItemStack itemStack1 = itemStack0.copy();
					itemStack1.stackSize = limit;

					this.inventory.setInventorySlotContents(firstFree, itemStack1);

					itemStack0.stackSize -= limit;
				} else {
					this.inventory.setInventorySlotContents(firstFree, itemStack0);
					return null;
				}
			} else {
				ItemStack itemStack1 = this.inventory.getStackInSlot(firstPartial);
	          
				int amount = itemStack0.stackSize;
				int partialAmount = itemStack1.stackSize;
				int maxAmount = itemStack1.getMaxStackSize();
	     
				if (amount + partialAmount <= maxAmount) {
					itemStack1.stackSize += amount + partialAmount;
					return null;
				}

				itemStack1.stackSize = maxAmount;
				itemStack0.stackSize += partialAmount - maxAmount;
			}
		}
	}

	int firstEmpty() {
		for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
			ItemStack item0 = this.inventory.getStackInSlot(i);

			if (item0 == null || item0.stackSize == 0 || item0.getItem() == null) {
				return i;
			}
		}

		return -1;
	}

	int firstPartial(ItemStack item) {
		if (item == null) {
			return -1;
		}

		for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
			ItemStack item0 = this.inventory.getStackInSlot(i);

			if (item0 != null && item0.stackSize < item0.getMaxStackSize() && this.isSimilar(item, item0)) {
				return i;
			}
		}

		return -1;
	}

	boolean isSimilar(ItemStack item0, ItemStack item1) {
		if (item0 == item1) {
			return true;
		}
		if (item0 == null || item1 == null) {
			return false;
		}
		if (item0.getItem() != item1.getItem()) {
			return false;
		}
		if (item0.getItemDamage() != item1.getItemDamage()) {
			return false;
		}
		if (item0.hasTagCompound() != item0.hasTagCompound()) {
			return false;
		}
		if (item0.hasTagCompound() && item0.getTagCompound().equals(item1.getTagCompound())) {
			return false;
		}
		return true;
	}

}