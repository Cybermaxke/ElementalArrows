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
package me.cybermaxke.elementarrows.forge.v1710.entity;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.entity.EntityLiving;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.forge.v1710.potion.FPotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FEntityLiving<T extends EntityLivingBase> extends FEntity<T> implements EntityLiving {

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
		return this.entity.rotationYawHead;
	}

	@Override
	public float getHeadPitch() {
		return this.entity.rotationPitch;
	}

	@Override
	public float getPitch() {
		return 0f;
	}

	@Override
	public float getEyeHeight() {
		return this.entity.getEyeHeight();
	}

	@Override
	public boolean hasPotionEffect(PotionType type) {
		Preconditions.checkNotNull(type);
		return this.entity.isPotionActive(type.getInternalId());
	}

	@Override
	public FPotionEffect addPotionEffect(me.cybermaxke.elementarrows.common.potion.PotionEffect effect) {
		Preconditions.checkNotNull(effect);

		FPotionEffect effect0 = (FPotionEffect) effect;
		Potion potion = Potion.potionTypes[effect0.effect.getPotionID()];

		PotionEffect effect1 = null;

		if (this.entity.isPotionActive(potion)) {
			effect1 = this.entity.getActivePotionEffect(potion);
		}

		this.entity.addPotionEffect(effect0.effect);

		if (effect1 != null) {
			return new FPotionEffect(effect1);
		} else {
			return null;
		}
	}

	@Override
	public FPotionEffect removePotionEffect(PotionType type) {
		Preconditions.checkNotNull(type);

		Potion potion = Potion.potionTypes[type.getInternalId()];
		PotionEffect effect = null;

		if (this.entity.isPotionActive(potion)) {
			effect = this.entity.getActivePotionEffect(potion);
		}

		this.entity.removePotionEffect(potion.id);

		if (effect != null) {
			return new FPotionEffect(effect);
		} else {
			return null;
		}
	}

	@Override
	public Attribute getCreatureAttribute() {
		EnumCreatureAttribute attribute = this.entity.getCreatureAttribute();

		if (attribute == EnumCreatureAttribute.ARTHROPOD) {
			return Attribute.Arthropod;
		} else if (attribute == EnumCreatureAttribute.UNDEAD) {
			return Attribute.Undead;
		} else {
			return Attribute.Undefined;
		}
	}

}