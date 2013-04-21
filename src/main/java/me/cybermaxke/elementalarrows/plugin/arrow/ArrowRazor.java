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
package me.cybermaxke.elementalarrows.plugin.arrow;

import org.bukkit.plugin.Plugin;

import me.cybermaxke.elementalarrows.api.material.GenericCustomArrow;

public class ArrowRazor extends GenericCustomArrow {

	public ArrowRazor(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}

	@Override
	public void onInit() {
		this.setSpeedMultiplier(1.7D);
		this.setKnockbackStrengthMultiplier(1.2D);
	}
}