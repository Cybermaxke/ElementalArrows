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
package me.cybermaxke.elementalarrows.spout.plugin.entity;

import java.util.Collections;
import java.util.List;

import me.cybermaxke.elementalarrows.spout.api.ElementalArrows;
import me.cybermaxke.elementalarrows.spout.api.entity.ElementalTurret;
import me.cybermaxke.elementalarrows.spout.api.entity.comparator.EntityDistanceComparator;
import me.cybermaxke.elementalarrows.spout.api.entity.selector.EntitySelector;
import me.cybermaxke.elementalarrows.spout.api.entity.selector.EntitySelectorMonster;
import me.cybermaxke.elementalarrows.spout.api.inventory.TurretInventory;
import me.cybermaxke.elementalarrows.spout.plugin.data.ElementalData;

import org.spout.api.entity.Entity;

import org.spout.vanilla.VanillaPlugin;
import org.spout.vanilla.component.entity.misc.Health;
import org.spout.vanilla.component.entity.substance.Substance;
import org.spout.vanilla.protocol.entity.object.ObjectEntityProtocol;
import org.spout.vanilla.protocol.entity.object.ObjectType;

public class ElementTurret extends Substance implements ElementalTurret {
	private EntitySelector targetSelector;
	private Health health;
	private Entity target;

	@Override
	public void onAttached() {
		this.getOwner().getNetwork().setEntityProtocol(VanillaPlugin.VANILLA_PROTOCOL_ID, new ObjectEntityProtocol(ObjectType.ENDER_CRYSTAL));
		super.onAttached();

		this.targetSelector = new EntitySelectorMonster();
		this.health = this.getOwner().add(Health.class);
	}

	@Override
	public void onDetached() {
		super.onDetached();
	}

	@Override
	public void onTick(float dt) {
		super.onTick(dt);

		if (this.target == null || this.target.isRemoved() || (this.target.get(Health.class) != null && this.target.get(Health.class).isDead())) {
			this.target = this.getNearbyTarget();
		}

		//TODO: Shooting arrows.
	}

	@Override
	public TurretInventory getInventory() {
		return this.getDatatable().get(ElementalData.TURRET_INVENTORY);
	}

	@Override
	public EntitySelector getTargetSelector() {
		return this.targetSelector;
	}

	@Override
	public void setTargetSelector(EntitySelector selector) {
		this.targetSelector = selector;
	}

	@Override
	public int getTargetRange() {
		return this.getDatatable().get(ElementalData.TURRET_RANGE);
	}

	@Override
	public void setTargetRange(int range) {
		this.getDatatable().put(ElementalData.TURRET_RANGE, range);
	}

	@Override
	public Health getHealth() {
		return this.health;
	}

	@Override
	public int getAttackDelay() {
		return this.getDatatable().get(ElementalData.TURRET_ATTACK_DELAY);
	}

	@Override
	public void setAttackDelay(int delay) {
		this.getDatatable().put(ElementalData.TURRET_ATTACK_DELAY, delay);
	}

	@Override
	public Entity getTarget() {
		return this.target;
	}

	@Override
	public void setTarget(Entity target) {
		this.target = target;
	}

	protected Entity getNearbyTarget() {
		List<Entity> entities = ElementalArrows.getAPI().getNearbyEntities(this.getOwner(), this.getTargetRange(), this.targetSelector);
		Collections.sort(entities, new EntityDistanceComparator(this.getOwner()));
		return entities.size() > 0 ? entities.get(0) : null;
	}
}