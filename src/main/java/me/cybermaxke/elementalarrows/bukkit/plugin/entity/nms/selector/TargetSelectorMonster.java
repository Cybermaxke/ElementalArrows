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
package me.cybermaxke.elementalarrows.bukkit.plugin.entity.nms.selector;

import net.minecraft.server.v1_5_R3.IMonster;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

import me.cybermaxke.elementalarrows.bukkit.api.entity.selector.TargetSelector;

public class TargetSelectorMonster implements TargetSelector {

	@Override
	public boolean isValidTarget(Entity entity) {
		return ((CraftEntity) entity).getHandle() instanceof IMonster;
	}
}