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

import java.util.Map;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.enchant.Enchant;
import me.cybermaxke.elementarrows.forge.v1800.FProxyCommon;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class FItemStack implements me.cybermaxke.elementarrows.common.item.inventory.ItemStack {
	public final ItemStack itemStack;

	protected FItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	@Override
	public FItemType getType() {
		return FProxyCommon.items.of(this.itemStack.getItem());
	}

	@Override
	public int getQuantity() {
		return this.itemStack.stackSize;
	}

	@Override
	public int addQuantity(int quantity) {
		return this.itemStack.stackSize += quantity;
	}

	@Override
	public void setQuantity(int quantity) {
		this.itemStack.stackSize = quantity;
	}

	@Override
	public int getData() {
		return this.itemStack.getItemDamage();
	}

	@Override
	public void setData(int data) {
		this.itemStack.setItemDamage(data);
	}

	@Override
	public boolean hasEnchant(Enchant enchantment) {
		Preconditions.checkNotNull(enchantment);
		return EnchantmentHelper.getEnchantmentLevel(enchantment.getInternalId(), this.itemStack) > 0;
	}

	@Override
	public int getEnchantLevel(Enchant enchantment) {
		Preconditions.checkNotNull(enchantment);
		return EnchantmentHelper.getEnchantmentLevel(enchantment.getInternalId(), this.itemStack);
	}

	@Override
	public void addEnchant(Enchant enchantment, int level) {
		Preconditions.checkNotNull(enchantment);
		Preconditions.checkNotNull(level > 0);

		Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(this.itemStack);
		map.put(enchantment.getInternalId(), level);
		EnchantmentHelper.setEnchantments(map, this.itemStack);
	}

	@Override
	public void removeEnchant(Enchant enchantment) {
		Preconditions.checkNotNull(enchantment);

		Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(this.itemStack);
		map.remove(enchantment.getInternalId());
		EnchantmentHelper.setEnchantments(map, this.itemStack);
	}

	/**
	 * Gets the forge item stack for the minecraft item stack.
	 * 
	 * @param itemStack the minecraft item stack
	 * @return the item stack
	 */
	public static FItemStack of(ItemStack itemStack) {
		return itemStack == null ? null : new FItemStack(itemStack);
	}

}