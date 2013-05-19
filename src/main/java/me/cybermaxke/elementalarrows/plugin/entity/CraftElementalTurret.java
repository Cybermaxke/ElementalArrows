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

import me.cybermaxke.elementalarrows.api.entity.ElementalTurret;
import me.cybermaxke.elementalarrows.api.entity.selector.TargetSelector;
import me.cybermaxke.elementalarrows.api.inventory.TurretInventory;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalTurret;
import me.cybermaxke.elementalarrows.plugin.inventory.CraftTurretInventory;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class CraftElementalTurret extends CraftEntity implements ElementalTurret {

	public CraftElementalTurret(EntityElementalTurret entity) {
		super(entity.world.getServer(), entity);
	}

	@Override
	public EntityElementalTurret getHandle() {
		return (EntityElementalTurret) this.entity;
	}

	@Override
	public EntityType getType() {
		return EntityType.ENDER_CRYSTAL;
	}

	@Override
	public TurretInventory getInventory() {
		return new CraftTurretInventory(this.getHandle().inventory);
	}

	@Override
	public void setName(String name) {
		this.getHandle().name = name;
	}

	@Override
	public String getName() {
		return this.getHandle().name;
	}

	@Override
	public int getAttackDelay() {
		return this.getHandle().attackDelay;
	}

	@Override
	public void setAttackDelay(int delay) {
		this.getHandle().attackDelay = delay;
	}

	@Override
	public int getHealth() {
		return this.getHandle().health;
	}

	@Override
	public void setHealth(int health) {
		this.getHandle().health = health;
	}

	@Override
	public float getTargetRange() {
		return this.getHandle().range;
	}

	@Override
	public void setTargetRange(float range) {
		this.getHandle().range = range;
	}

	@Override
	public Entity getTarget() {
		return this.getHandle().target == null ? null : this.getHandle().target.getBukkitEntity();
	}

	@Override
	public void setTarget(Entity target) {
		this.getHandle().target = target == null ? null : ((CraftEntity) target).getHandle();
	}

	@Override
	public TargetSelector getTargetSelector() {
		return this.getHandle().selector;
	}

	@Override
	public void setTargetSelector(TargetSelector selector) {
		this.getHandle().selector = selector;
	}
}