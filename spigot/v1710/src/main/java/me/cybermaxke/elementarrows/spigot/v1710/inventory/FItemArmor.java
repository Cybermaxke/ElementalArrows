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

import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemArmor;

public class FItemArmor extends FItemType implements me.cybermaxke.elementarrows.common.item.type.ItemArmor {

	public FItemArmor(Item item) {
		super(item);
	}

	@Override
	public Type getType() {
		ItemArmor item0 = (ItemArmor) this.item;

		if (item0.b == 0) {
			return Type.Helmet;
		} else if (item0.b == 1) {
			return Type.Chestplate;
		} else if (item0.b == 2) {
			return Type.Leggings;
		} else if (item0.b == 3) {
			return Type.Boots;
		}

		return null;
	}

}