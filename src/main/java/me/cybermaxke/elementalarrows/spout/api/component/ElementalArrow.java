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
package me.cybermaxke.elementalarrows.spout.api.component;

import me.cybermaxke.elementalarrows.spout.api.data.ElementalData;
import me.cybermaxke.elementalarrows.spout.api.protocol.ElementalArrowProtocol;

import org.spout.api.entity.Entity;
import org.spout.api.geo.discrete.Point;
import org.spout.api.util.Parameter;

import org.spout.vanilla.VanillaPlugin;
import org.spout.vanilla.component.entity.misc.Health;
import org.spout.vanilla.component.entity.substance.Substance;
import org.spout.vanilla.component.entity.substance.projectile.Projectile;

public class ElementalArrow extends Substance implements Projectile {
	private Entity shooter;

	@Override
	public void onAttached() {
		this.getOwner().getNetwork().setEntityProtocol(VanillaPlugin.VANILLA_PROTOCOL_ID, new ElementalArrowProtocol());
		super.onAttached();
	}

	@Override
	public void onDetached() {
		super.onDetached();
	}

	@Override
	public void onCollided(Point point, Entity entity) {
		if (entity.get(Health.class) != null) {
			//TODO: Damaging the entity.
		}
	}

	@Override
	public Entity getShooter() {
		return this.shooter;
	}

	@Override
	public void setShooter(Entity shooter) {
		this.shooter = shooter;
	}

	public boolean isCritical() {
		return this.getDatatable().get(ElementalData.CRITICAL);
	}

	public void setCritical(boolean critical) {
		this.getDatatable().put(ElementalData.CRITICAL, critical);
		this.setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, (byte) (0 | (critical ? 1 : 0) << 1)));
	}
}