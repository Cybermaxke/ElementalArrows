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
package me.cybermaxke.elementalarrows.spout.api.entity.component;

import me.cybermaxke.elementalarrows.spout.api.entity.ElementalArrow;

import org.spout.api.component.entity.EntityComponent;
import org.spout.api.entity.Entity;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.discrete.Point;

import org.spout.vanilla.component.entity.substance.Substance;

public class ArrowComponent extends EntityComponent {
	private ElementalArrow arrow;

	@Override
	public void onAttached() {
		this.arrow = (ElementalArrow) this.getOwner().get(Substance.class);
	}

	/**
	 * Gets the elemental arrow this component is to attached.
	 * @return arrow
	 */
	public ElementalArrow getArrow() {
		return this.arrow;
	}

	/**
	 * Called every tick.
	 */
	public void onTick() {

	}

	/**
	 * Called when the arrow is shot.
	 */
	public void onShoot() {

	}

	/**
	 * Called when the arrow hits the entity at the point.
	 * @param point
	 * @param entity
	 */
	public void onHit(Point point, Entity entity) {

	}

	/**
	 * Called when the arrow hits the block at the point.
	 * @param point
	 * @param block
	 */
	public void onHit(Point point, Block block) {

	}
}