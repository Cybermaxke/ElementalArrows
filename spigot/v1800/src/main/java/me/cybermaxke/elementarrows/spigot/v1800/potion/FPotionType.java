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
package me.cybermaxke.elementarrows.spigot.v1800.potion;

import net.minecraft.server.v1_8_R1.MobEffectList;

import me.cybermaxke.elementarrows.common.potion.PotionType;

public class FPotionType implements PotionType {
	public final MobEffectList type;
	public final String id;

	public FPotionType(String name, MobEffectList type) {
		this.type = type;
		this.id = name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int getInternalId() {
		return this.type.id;
	}

}