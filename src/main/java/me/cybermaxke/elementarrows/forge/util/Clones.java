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
package me.cybermaxke.elementarrows.forge.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;

public final class Clones {

	private Clones() {}

	/**
	 * Clones the item stack value.
	 * 
	 * @param value the value
	 * @return the cloned value
	 */
	public static ItemStack clone(ItemStack value) {
		if (value != null) {
			ItemStack clone = new ItemStack(value.getItem(), value.stackSize, value.getItemDamage());
			if (value.hasTagCompound()) {
				clone.setTagCompound((NBTTagCompound) value.getTagCompound().copy());
			}
			return value;
		} else {
			return null;
		}
	}

	/**
	 * Clones the potion effect value.
	 * 
	 * @param value the value
	 * @return the cloned value
	 */
	public static PotionEffect clone(PotionEffect value) {
		if (value != null) {
			int id = value.getPotionID();
			int duration = value.getDuration();
			int amplifier = value.getAmplifier();
			boolean ambient = value.getIsAmbient();

			return new PotionEffect(id, duration, amplifier, ambient);
		} else {
			return null;
		}
	}
}