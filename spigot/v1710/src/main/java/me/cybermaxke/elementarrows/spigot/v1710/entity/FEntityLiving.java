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
package me.cybermaxke.elementarrows.spigot.v1710.entity;

import net.minecraft.server.v1_7_R4.EntityLiving;

import me.cybermaxke.elementarrows.common.potion.PotionEffect;
import me.cybermaxke.elementarrows.common.potion.PotionType;

public class FEntityLiving<T extends EntityLiving> extends FEntity<T> implements me.cybermaxke.elementarrows.common.entity.EntityLiving {

	public FEntityLiving(T entity) {
		super(entity);
	}

	@Override
	public float getHealth() {
		return this.entity.getHealth();
	}

	@Override
	public void setHealth(float health) {
		this.entity.setHealth(health);
	}

	@Override
	public float getHeadYaw() {
		return this.entity.getHeadRotation();
	}

	@Override
	public float getHeadPitch() {
		return this.entity.pitch;
	}

	@Override
	public float getPitch() {
		return 0f;
	}

	@Override
	public float getEyeHeight() {
		return this.entity.getHeadHeight();
	}

	@Override
	public boolean hasPotionEffect(PotionType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PotionEffect addPotionEffect(PotionEffect effect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PotionEffect removePotionEffect(PotionType type) {
		// TODO Auto-generated method stub
		return null;
	}

}