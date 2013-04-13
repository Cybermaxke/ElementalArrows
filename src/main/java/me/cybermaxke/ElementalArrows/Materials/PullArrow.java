/**
 * 
 * This software is part of the ElementalArrows
 * 
 * This plugins adds custom arrows to the game like they from the
 * ElemantalArrows mod but ported to spoutplugin and bukkit.
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
package me.cybermaxke.ElementalArrows.Materials;

import me.cybermaxke.ElementalArrows.ArrowEntity;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PullArrow extends CustomArrowItem {

	public PullArrow(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void registerRecipes() {

	}

	@Override
	public void onHit(Player shooter, LivingEntity entity, ArrowEntity arrow) {
		Location l = shooter.getTargetBlock(null, 2).getLocation();
		entity.setVelocity(l.subtract(entity.getLocation()).toVector().normalize().multiply(0.6F));
	}

	@Override
	public void onHit(Player shooter, ArrowEntity arrow) {

	}

	@Override
	public void onShoot(Player shooter, ArrowEntity arrow) {

	}

	@Override
	public void onTick(Player shooter, ArrowEntity arrow) {

	}
}