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

import com.google.common.base.Preconditions;

import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EnumMonsterType;
import net.minecraft.server.v1_8_R1.MobEffect;
import net.minecraft.server.v1_8_R1.MobEffectList;
import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.spigot.v1800.inventory.FItemStack;
import me.cybermaxke.elementarrows.spigot.v1800.potion.FPotionEffect;

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
		Preconditions.checkNotNull(type);
		return this.entity.hasEffect(type.getInternalId());
	}

	@Override
	public FPotionEffect addPotionEffect(me.cybermaxke.elementarrows.common.potion.PotionEffect effect) {
		Preconditions.checkNotNull(effect);

		FPotionEffect effect0 = (FPotionEffect) effect;
		MobEffectList potion = MobEffectList.byId[effect0.effect.getEffectId()];

		MobEffect effect1 = null;

		if (this.entity.hasEffect(potion)) {
			effect1 = this.entity.getEffect(potion);
		}

		this.entity.addEffect(effect0.effect);

		if (effect1 != null) {
			return new FPotionEffect(effect1);
		} else {
			return null;
		}
	}

	@Override
	public FPotionEffect removePotionEffect(PotionType type) {
		Preconditions.checkNotNull(type);

		MobEffectList potion = MobEffectList.byId[type.getInternalId()];
		MobEffect effect = null;

		if (this.entity.hasEffect(potion)) {
			effect = this.entity.getEffect(potion);
		}

		this.entity.removeEffect(potion.id);

		if (effect != null) {
			return new FPotionEffect(effect);
		} else {
			return null;
		}
	}

	@Override
	public Attribute getCreatureAttribute() {
		EnumMonsterType attribute = this.entity.getMonsterType();

		if (attribute == EnumMonsterType.ARTHROPOD) {
			return Attribute.Arthropod;
		} else if (attribute == EnumMonsterType.UNDEAD) {
			return Attribute.Undead;
		} else {
			return Attribute.Undefined;
		}
	}

	@Override
	public ItemStack getHeldItem() {
		return FItemStack.of(this.entity.getEquipment(0));
	}

}