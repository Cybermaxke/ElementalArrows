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
package me.cybermaxke.elementarrows.spigot.v1710.potion;

import net.minecraft.server.v1_7_R4.MobEffect;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.potion.PotionType;
import me.cybermaxke.elementarrows.common.potion.Potions;

public class FPotionEffect implements me.cybermaxke.elementarrows.common.potion.PotionEffect {
	public MobEffect effect;

	public FPotionEffect(MobEffect effect) {
		this.effect = effect;
	}

	@Override
	public PotionType getType() {
		return Potions.typeById(this.effect.getEffectId());
	}

	@Override
	public int getDuration() {
		return this.effect.getDuration();
	}

	@Override
	public int getAmplifier() {
		return this.effect.getAmplifier();
	}

	@Override
	public boolean isAmbient() {
		return this.effect.isAmbient();
	}

	@Override
	public boolean hasParticles() {
		return true;
	}

	@Override
	public FPotionEffect clone() {
		int id = this.effect.getEffectId();
		int duration = this.effect.getDuration();
		int amplifier = this.effect.getAmplifier();

		boolean ambient = this.effect.isAmbient();

		return new FPotionEffect(new MobEffect(id, duration, amplifier, ambient));
	}

	@Override
	public FPotionEffect combineWith(me.cybermaxke.elementarrows.common.potion.PotionEffect effect) {
		Preconditions.checkNotNull(effect);

		FPotionEffect clone = this.clone();
		clone.effect.a(((FPotionEffect) effect).effect);
		return clone;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof FPotionEffect)) {
			return false;
		}

		return ((FPotionEffect) other).effect.equals(this.effect);
	}

}