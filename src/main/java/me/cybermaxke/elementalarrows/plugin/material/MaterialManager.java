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
package me.cybermaxke.elementalarrows.plugin.material;

import me.cybermaxke.elementalarrows.api.entity.ElementalSkeleton;
import me.cybermaxke.elementalarrows.api.material.GenericCustomSpawnEgg;
import me.cybermaxke.elementalarrows.api.material.SpawnEggMaterial;
import me.cybermaxke.elementalarrows.plugin.material.arrow.ArrowManager;

import org.bukkit.plugin.Plugin;

public class MaterialManager {
	public static SpawnEggMaterial EGG_ELEMENTAL_SKELETON;

	public MaterialManager(Plugin plugin) {
		new ArrowManager(plugin);

		EGG_ELEMENTAL_SKELETON = new GenericCustomSpawnEgg(plugin, "Spawn Elemental Skeleton", "https://dl.dropboxusercontent.com/u/104060836/ElementalArrows/Resources/ElementalSkeletonEgg.png", ElementalSkeleton.class);
	}
}