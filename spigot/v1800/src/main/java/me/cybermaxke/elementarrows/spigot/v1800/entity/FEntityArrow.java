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
package me.cybermaxke.elementarrows.spigot.v1800.entity;

import java.lang.reflect.Field;

import net.minecraft.server.v1_8_R1.EntityArrow;

public class FEntityArrow extends FEntityProjectile<EntityArrow> implements me.cybermaxke.elementarrows.common.entity.EntityArrow {
	
	/**
	 * Cached knockback field.
	 */
	protected static Field knockback;

	/**
	 * The cached in ground field.
	 */
	protected static Field inGround;

	public FEntityArrow(EntityArrow entity) {
		super(entity);
	}

	@Override
	public boolean isCiritical() {
		return this.entity.isCritical();
	}

	@Override
	public void setCritical(boolean critical) {
		this.entity.setCritical(critical);
	}

	@Override
	public double getDamage() {
		return this.entity.j();
	}

	@Override
	public void setDamage(double damage) {
		this.entity.b(damage);
	}

	@Override
	public int getKnockbackPower() {
		return this.entity.knockbackStrength;
	}

	@Override
	public void setKnockbackPower(int power) {
		this.entity.setKnockbackStrength(power);
	}

	@Override
	public PickupMode getPickupMode() {
		int mode = this.entity.fromPlayer;

		if (mode == 1) {
			return PickupMode.True;
		} else if (mode == 2) {
			return PickupMode.Creative;
		} else {
			return PickupMode.False;
		}
	}

	@Override
	public void setPickupMode(PickupMode mode) {
		int mode0;

		if (mode == PickupMode.True) {
			mode0 = 1;
		} else if (mode == PickupMode.Creative) {
			mode0 = 2;
		} else {
			mode0 = 0;
		}

		this.entity.fromPlayer = mode0;
	}

	@Override
	public boolean isInGround() {
		return this.entity.inGround;
	}

	@Override
	public int getElementData() {
		if (this.entity instanceof EntityElementArrow) {
			return ((EntityElementArrow) this.entity).getElementData();
		}

		return 0;
	}

	@Override
	public void setElementData(int data) {
		if (this.entity instanceof EntityElementArrow) {
			((EntityElementArrow) this.entity).setElementData(data);
		}
	}

}