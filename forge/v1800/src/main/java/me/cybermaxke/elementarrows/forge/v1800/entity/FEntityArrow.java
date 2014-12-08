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

import java.lang.reflect.Field;

import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import net.minecraft.entity.projectile.EntityArrow;

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
		return this.entity.getIsCritical();
	}

	@Override
	public void setCritical(boolean critical) {
		this.entity.setIsCritical(critical);
	}

	@Override
	public double getDamage() {
		return this.entity.getDamage();
	}

	@Override
	public void setDamage(double damage) {
		this.entity.setDamage(damage);
	}

	@Override
	public int getKnockbackPower() {
		int power = 0;

		try {
			if (knockback == null) {
				knockback = Fields.findField(EntityArrow.class, int.class, 0, true);
			}

			knockback.setAccessible(true);
			power = knockback.getInt(this.entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return power;
	}

	@Override
	public void setKnockbackPower(int power) {
		this.entity.setKnockbackStrength(power);
	}

	@Override
	public PickupMode getPickupMode() {
		int mode = this.entity.canBePickedUp;

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

		this.entity.canBePickedUp = mode0;
	}

	@Override
	public boolean isInGround() {
		if (this.entity instanceof EntityElementArrow) {
			return ((EntityElementArrow) this.entity).inGround;
		}

		boolean inground = this.entity.onGround;

		try {
			if (inGround == null) {
				inGround = Fields.findField(EntityArrow.class, boolean.class, 0, false);
			}

			inGround.setAccessible(true);
			inground = inGround.getBoolean(this.entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return inground;
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