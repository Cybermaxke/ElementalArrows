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
package me.cybermaxke.elementalarrows.spout.api.entity.comparator;

import org.spout.api.entity.Entity;
import org.spout.api.geo.discrete.Point;

public class EntityDistanceComparator implements EntityComparator {
	private Entity entity;

	public EntityDistanceComparator(Entity entity) {
		this.entity = entity;
	}

	@Override
	public int compare(Entity entity1, Entity entity2) {
		Point point = this.entity.getPhysics().getPosition();
		Point point1 = entity1.getPhysics().getPosition();
		Point point2 = entity2.getPhysics().getPosition();

		double d1 = point.distance(point1);
		double d2 = point.distance(point2);

		return d1 < d2 ? -1 : 1;
	}
}