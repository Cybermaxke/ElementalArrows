/**
 * 
 * This software is part of the ElementalArrows
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.plugin.material.arrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.GenericCustomArrow;

public class ArrowVolley extends GenericCustomArrow {
	private Random random = new Random();
	private int amount;

	public ArrowVolley(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onInit() {
		super.onInit();
		this.amount = 18;
	}

	@Override
	public void onLoad(YamlConfiguration config) {
		super.onLoad(config);
		if (config.contains("Amount")) {
			this.amount = config.getInt("Amount");
		}
	}

	@Override
	public void onSave(YamlConfiguration config) {
		super.onSave(config);
		if (!config.contains("Amount")) {
			config.set("Amount", this.amount);
		}
	}

	@Override
	public void onHit(LivingEntity shooter, ElementalArrow arrow) {
		Location l = arrow.getLocation();
		for (int i = 0; i < this.amount; i++) {
			int x = this.random.nextInt(4);
			int z = this.random.nextInt(4);
			x *= this.random.nextBoolean() ? 1 : -1;
			z *= this.random.nextBoolean() ? 1 : -1;
			Location l2 = l.clone().add(x, 7, z);

			int x2 = this.random.nextInt(2);
			int z2 = this.random.nextInt(2);
			x2 *= this.random.nextBoolean() ? 1 : -1;
			z2 *= this.random.nextBoolean() ? 1 : -1;
			Location l3 = l.add(x2, 0, z2);

			double dX = l2.getX() - l3.getX();
			double dY = l2.getY() - l3.getY();
			double dZ = l2.getZ() - l3.getZ();

			double yaw = Math.atan2(dZ, dX);
			double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

			double X = Math.sin(pitch) * Math.cos(yaw);
			double Y = Math.sin(pitch) * Math.sin(yaw);
			double Z = Math.cos(pitch);

			Vector v = new Vector(X, Z, Y);
			Arrow a = arrow.getWorld().spawnArrow(l2, v, 3.0F, 0.0F);
			a.setShooter(shooter);
		}
	}
}