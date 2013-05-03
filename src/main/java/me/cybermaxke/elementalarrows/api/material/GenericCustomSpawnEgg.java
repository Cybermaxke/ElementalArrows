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
package me.cybermaxke.elementalarrows.api.material;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.plugin.Plugin;

public class GenericCustomSpawnEgg extends GenericCustomItem implements SpawnEggMaterial {
	private Class<? extends Entity> entity;

	public GenericCustomSpawnEgg(Plugin plugin, String name, String texture, Class<? extends Entity> entity) {
		super(plugin, name, texture);
		this.setId(Material.MONSTER_EGG.getId());
		this.entity = entity;
	}

	public GenericCustomSpawnEgg(Plugin plugin, String name, String texture) {
		this(plugin, name, texture, Pig.class);
	}

	@Override
	public Class<? extends Entity> getEntity() {
		return this.entity;
	}

	@Override
	public void setEntity(Class<? extends Entity> entity) {
		this.entity = entity;
	}
}