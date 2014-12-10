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
package me.cybermaxke.elementarrows.spigot.v1800.item;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;

import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.ItemStack;

public class ItemArrow extends Item {

	public ItemArrow() {
		this.a(true);
		this.c("arrow");
	}

	@Override
	public String a(ItemStack itemStack) {
		int data = itemStack.getData();

		if (data != 0) {
			ElementArrow arrow = Arrows.find(data);
			if (arrow != null) {
				return "item." + arrow.getUnlocalizedName();
			}
		}

		return super.a(itemStack);
	}

}