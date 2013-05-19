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
package me.cybermaxke.elementalarrows.plugin;

import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityVillager;
import net.minecraft.server.v1_5_R3.EntityWitch;
import net.minecraft.server.v1_5_R3.World;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import me.cybermaxke.elementalarrows.api.EffectType;
import me.cybermaxke.elementalarrows.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.api.entity.ElementalSkeleton;
import me.cybermaxke.elementalarrows.api.entity.ElementalTurret;
import me.cybermaxke.elementalarrows.plugin.dispenser.nms.DispenseBehaviorManager;
import me.cybermaxke.elementalarrows.plugin.entity.CraftElementalPlayer;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalArrow;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalSkeleton;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalTurret;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityManager;
import me.cybermaxke.elementalarrows.plugin.item.nms.ItemManager;

public class ElementalArrows implements ElementalArrowsAPI {

	public ElementalArrows() {
		new ItemManager();
		new EntityManager();
		new DispenseBehaviorManager();
	}

	@Override
	public ElementalPlayer getPlayer(Player player) {
		return new CraftElementalPlayer(player);
	}

	@Override
	public boolean isRegionProtected(Location location) {
		return false;
	}

	@Override
	public ElementalArrow shootElementalArrow(Location location, Vector vector, float speed, float spread) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		EntityElementalArrow a = new EntityElementalArrow(w);
		a.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
		a.shoot(vector.getX(), vector.getY(), vector.getZ(), speed, spread);
		a.speed = speed;
		w.addEntity(a);
		return a.getBukkitEntity();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T spawn(Class<T> entity, Location location, SpawnReason reason) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		if (ElementalSkeleton.class.isAssignableFrom(entity)) {
			EntityElementalSkeleton ent = new EntityElementalSkeleton(w);
			ent.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
			w.addEntity(ent, reason);
			return (T) ent.getBukkitEntity();
		} else if (ElementalTurret.class.isAssignableFrom(entity)) {
			EntityElementalTurret ent = new EntityElementalTurret(w);
			ent.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
			w.addEntity(ent, reason);
			return (T) ent.getBukkitEntity();
		}
		return location.getWorld().spawn(location, entity);
	}

	@Override
	public <T extends Entity> T spawn(Class<T> entity, Location location) {
		return this.spawn(entity, location, SpawnReason.CUSTOM);
	}

	@Override
	public boolean isReplaceable(Material block) {
		if (!block.isBlock()) {
			return false;
		}
		return Block.byId[block.getId()].material.isReplaceable();
	}

	@Override
	public void playFireworkEffect(Location location, FireworkEffect... effects) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		Firework f = this.spawn(Firework.class, location);
		FireworkMeta m = f.getFireworkMeta();
		m.clearEffects();
		m.setPower(1);
		m.addEffects(effects);
		f.setFireworkMeta(m);
		w.broadcastEntityEffect(((CraftEntity) f).getHandle(), (byte) 17);
		f.remove();
	}

	@Override
	public void playEffect(Location location, EffectType effect) {
		World w = ((CraftWorld) location.getWorld()).getHandle();
		EntityLiving ent = effect.equals(EffectType.MAGIC) ? new EntityWitch(w) : new EntityVillager(w);
		ent.setPosition(location.getX(), location.getY(), location.getZ());
		ent.setInvisible(true);
		w.addEntity(ent);
		w.broadcastEntityEffect(ent, (byte) this.getEffectId(effect));
		w.removeEntity(ent);
	}

	private int getEffectId(EffectType type) {
		switch (type) {
			case ANGRY:
				return 13;
			case HAPPY:
				return 14;
			case HEARTH:
				return 12;
			case MAGIC:
				return 15;
			default:
				return 0;
		}
	}

	@Override
	public float getEntityHeight(Entity entity) {
		return ((CraftEntity) entity).getHandle().height;
	}

	@Override
	public float getEntityLength(Entity entity) {
		return ((CraftEntity) entity).getHandle().length;
	}

	@Override
	public float getEntityWidth(Entity entity) {
		return ((CraftEntity) entity).getHandle().width;
	}
}