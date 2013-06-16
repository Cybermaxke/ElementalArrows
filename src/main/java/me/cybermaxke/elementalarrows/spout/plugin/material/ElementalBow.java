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
package me.cybermaxke.elementalarrows.spout.plugin.material;

import me.cybermaxke.elementalarrows.spout.api.material.ElementalItemMaterial;
import me.cybermaxke.elementalarrows.spout.plugin.entity.ElementArrow;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.Action;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;

import org.spout.vanilla.material.VanillaMaterials;

/**
 * A bow to test the custom arrow.
 */
@SuppressWarnings("unchecked")
public class ElementalBow extends ElementalItemMaterial {

	public ElementalBow(String name) {
		super(name, VanillaMaterials.BOW, 1);
	}

	@Override
	public void onInteract(Entity entity, Block block, Action type, BlockFace clickedface) {
		super.onInteract(entity, block, type, clickedface);
		if (type.equals(Action.RIGHT_CLICK)) {
			this.shootArrow(entity, 0.8F);
		}
	}

	@Override
	public void onInteract(Entity entity, Entity other, Action type) {
		super.onInteract(entity, other, type);
		if (type.equals(Action.RIGHT_CLICK)) {
			this.shootArrow(entity, 0.8F);
		}
	}

	@Override
	public void onInteract(Entity entity, Action type) {
		super.onInteract(entity, type);
		if (type.equals(Action.RIGHT_CLICK)) {
			this.shootArrow(entity, 0.8F);
		}
	}

	protected void shootArrow(Entity entity, float speed) {
		ElementArrow arrow = entity.getWorld().createAndSpawnEntity(entity.getScene().getPosition(), LoadOption.LOAD_ONLY, ElementArrow.class).add(ElementArrow.class);
		arrow.shoot(entity, speed);
		arrow.setCritical(true);
		arrow.setFireTicks(200);
	}
}