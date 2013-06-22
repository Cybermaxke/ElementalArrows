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
package me.cybermaxke.elementalarrows.bukkit.plugin.material.arrow;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.bukkit.api.material.GenericCustomArrow;

public class ArrowPull extends GenericCustomArrow {

	public ArrowPull(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onHit(LivingEntity shooter, LivingEntity entity, ElementalArrow arrow) {
		Location l = shooter.getTargetBlock(null, 2).getLocation();
		entity.setVelocity(l.subtract(entity.getLocation()).toVector().normalize().multiply(0.6F));
	}
}