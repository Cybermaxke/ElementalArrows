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
package me.cybermaxke.elementarrows.forge.v1800.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;

public class FEntityAgeable<T extends EntityLivingBase> extends FEntityLiving<T> implements me.cybermaxke.elementarrows.common.entity.EntityAgeable {

	public FEntityAgeable(T entity) {
		super(entity);
	}

	@Override
	public boolean isBaby() {
		if (this.entity instanceof EntityAgeable) {
			return ((EntityAgeable) this.entity).getGrowingAge() < 0;
		} else if (this.entity instanceof EntityZombie) {
			return ((EntityZombie) this.entity).isChild();
		}

		return false;
	}

	@Override
	public void setBaby(boolean baby) {
		if (this.entity instanceof EntityAgeable) {
			((EntityAgeable) this.entity).setGrowingAge(baby ? -24000 : 0);
		} else if (this.entity instanceof EntityZombie) {
			((EntityZombie) this.entity).setChild(baby);
		}
	}

}