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
package me.cybermaxke.elementalarrows.plugin.entity;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftSkeleton;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.EntitySkinType;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.cybermaxke.elementalarrows.api.entity.ElementalSkeleton;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalSkeleton;

public class CraftElementalSkeleton extends CraftSkeleton implements ElementalSkeleton {

	public CraftElementalSkeleton(EntityElementalSkeleton entity) {
		super(entity.world.getServer(), entity);
	}

	@Override
	public EntityElementalSkeleton getHandle() {
		return (EntityElementalSkeleton) this.entity;
	}

	@Override
	public ArrowMaterial getArrowMaterial() {
		return this.getHandle().arrow;
	}

	@Override
	public void setArrowMaterial(ArrowMaterial material) {
		this.getHandle().arrow = material;
		if (material != null && material.getSkeletonSkin() != null) {
			for (SpoutPlayer p : SpoutManager.getOnlinePlayers()) {
				p.setEntitySkin(this, material.getSkeletonSkin(), EntitySkinType.DEFAULT);
			}
		} else {
			for (SpoutPlayer p : SpoutManager.getOnlinePlayers()) {
				p.resetEntitySkin(this);
			}
		}
	}
}