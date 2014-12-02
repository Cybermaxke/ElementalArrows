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

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.MathHelper;

import me.cybermaxke.elementarrows.common.entity.EntityLiving;
import me.cybermaxke.elementarrows.common.math.Vector;
import me.cybermaxke.elementarrows.common.math.Vectors;
import me.cybermaxke.elementarrows.common.source.Source;

public class FEntityProjectile<T extends Entity> extends FEntity<T> implements me.cybermaxke.elementarrows.common.entity.EntityProjectile {

	public FEntityProjectile(T entity) {
		super(entity);
	}

	@Override
	public void setHeading(Vector direction, float speed, float spread) {
		double x = direction.getX();
		double y = direction.getY();
		double z = direction.getZ();

		if (this.entity instanceof IProjectile) {
			((IProjectile) this.entity).setThrowableHeading(x, y, z, speed, spread);
		} else {
			float f = MathHelper.sqrt_double(x * x + y * y + z * z);
		    
			x /= f;
			y /= f;
			z /= f;

			Random rand = this.entity.worldObj.rand;

			x += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.0075d * spread;
			y += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.0075d * spread;
			z += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.0075d * spread;

			x *= speed;
			y *= speed;
			z *= speed;

			this.entity.motionX = x;
			this.entity.motionY = y;
			this.entity.motionZ = z;
		}
	}

	@Override
	public void setHeading(EntityLiving entity, float speed, float spread) {
		Vector pos = entity.getPosition();

		float pi = (float) Math.PI;

		double x = pos.getX();
		double y = pos.getY() + entity.getEyeHeight();
		double z = pos.getZ();

		float yaw = entity.getHeadYaw();
		float pitch = entity.getHeadPitch();

		this.entity.setLocationAndAngles(x, y, z, yaw, pitch);

		x -= MathHelper.cos(yaw / 180f * pi) * 0.16f;
		z -= MathHelper.sin(yaw / 180f * pi) * 0.16f;
		y -= 0.1d;

		this.entity.setPosition(x, y, z);

		double mx = (-MathHelper.sin(yaw / 180f * pi) * MathHelper.cos(pitch / 180f * pi));
		double mz = (MathHelper.cos(yaw / 180f * pi) * MathHelper.cos(pitch / 180f * pi));
		double my = (-MathHelper.sin(pitch / 180f * pi));

		this.setHeading(Vectors.of(mx, my, mz), speed, spread);
	}

	@Override
	public Source getSource() {
		return null;
	}

	@Override
	public void setSource(Source source) {

	}

}