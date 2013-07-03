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
package me.cybermaxke.elementalarrows.bukkit.plugin.entity.nms;

import me.cybermaxke.elementalarrows.bukkit.api.entity.selector.TargetSelector;

import net.minecraft.server.v1_6_R1.Entity;
import net.minecraft.server.v1_6_R1.IEntitySelector;

public class EntityTargetSelector implements IEntitySelector {
	private TargetSelector targetSelector;

	public EntityTargetSelector(TargetSelector selector) {
		this.targetSelector = selector;
	}

	@Override
	public boolean a(Entity entity) {
		return this.targetSelector.isValidTarget(entity.getBukkitEntity());
	}
}