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
package me.cybermaxke.elementarrows.spigot.v1800.world;

import net.minecraft.server.v1_8_R1.EntityLightning;
import net.minecraft.server.v1_8_R1.GameRules;
import net.minecraft.server.v1_8_R1.World;

public class FWorldLightning {

	/**
	 * Spawns a lightning entity at the coordinates in the world.
	 * 
	 * @param world the world
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public void spawnLightningAt(World world, double x, double y, double z) {
		this.spawnLightningAt(world, x, y, z, true);
	}

	/**
	 * Spawns a lightning entity at the coordinates in the world. Gives you
	 * also the possibility to turn off the placed fire.
	 * 
	 * @param world the world
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @param placeFire whether you want to place fire
	 */
	public void spawnLightningAt(World world, double x, double y, double z, boolean placeFire) {
		GameRules rules = world.getGameRules();

		if (!placeFire) {
			boolean value0 = rules.getBoolean("doFireTick");
			rules.set("doFireTick", "false");

			/**
		 	* Create the entity without the parameter doFireTick.
		 	*/
			EntityLightningBoltFixed entity = new EntityLightningBoltFixed(world, x, y, z, placeFire);
			world.strikeLightning(entity);

			/**
			 * Reset the value.
			 */
			rules.set("doFireTick", value0 + "");
		} else {
			EntityLightningBoltFixed entity = new EntityLightningBoltFixed(world, x, y, z, placeFire);
			world.strikeLightning(entity);
		}
	}

	static class EntityLightningBoltFixed extends EntityLightning {
		private final boolean fire;

		public EntityLightningBoltFixed(World world, double x, double y, double z, boolean fire) {
			super(world, x, y, z);

			/**
			 * Whether we will place fire.
			 */
			this.fire = fire;
		}

		@Override
		public void h() {
			if (!this.fire) {
				GameRules rules = this.world.getGameRules();

				boolean value0 = rules.getBoolean("doFireTick");
				rules.set("doFireTick", "false");
				super.h();
				rules.set("doFireTick", value0 + "");
			} else {
				super.h();
			}
		}

	}

}