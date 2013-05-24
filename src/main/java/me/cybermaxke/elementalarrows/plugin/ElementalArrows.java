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

import java.lang.reflect.Field;

import net.minecraft.server.v1_5_R3.Block;
import net.minecraft.server.v1_5_R3.Packet63WorldParticles;
import net.minecraft.server.v1_5_R3.World;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import me.cybermaxke.elementalarrows.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.api.ParticleEffect;
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
		return CraftElementalPlayer.getPlayer(player);
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

	@Override
	public void playEffect(Player player, Location location, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, int count, Object... data) {
		Packet63WorldParticles packet = this.getParticlePacket(location, effect, offsetX, offsetY, offsetZ, count, data);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public void playEffect(Location location, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, int count, Object... data) {
		Packet63WorldParticles packet = this.getParticlePacket(location, effect, offsetX, offsetY, offsetZ, count, data);
		for (Player player : location.getWorld().getPlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	private Packet63WorldParticles getParticlePacket(Location location, ParticleEffect effect, float offsetX, float offsetY, float offsetZ, int count, Object... data) {
		String n = this.getId(effect);
		float d = (Float) (data != null && data.length > 0 && float.class.isInstance(data[0]) ? data[0] : 0F);
		switch (effect) {
			case ICONCRACK:
				if (data == null || data.length < 1) {
					throw new IllegalArgumentException("Missing data values for a icon effect, id is required.");
				} else if (data.length > 1) {
					throw new IllegalArgumentException("Too many data values.");
				}
				n = n + data[0];
				break;
			case TILECRACK:
				if (data == null || data.length < 2) {
					throw new IllegalArgumentException("Missing data values for a tilecrack effect, id and data are required.");
				} else if (data.length > 2) {
					throw new IllegalArgumentException("Too many data values.");
				}
				n = n + data[0] + "_" + data[1];
				break;
			default:
				break;
		}

		Packet63WorldParticles packet = new Packet63WorldParticles();
		this.setField(Packet63WorldParticles.class, "a", packet, n);
		this.setField(Packet63WorldParticles.class, "b", packet, (float) location.getX());
		this.setField(Packet63WorldParticles.class, "c", packet, (float) location.getY());
		this.setField(Packet63WorldParticles.class, "d", packet, (float) location.getZ());
		this.setField(Packet63WorldParticles.class, "e", packet, offsetX);
		this.setField(Packet63WorldParticles.class, "f", packet, offsetY);
		this.setField(Packet63WorldParticles.class, "g", packet, offsetZ);
		this.setField(Packet63WorldParticles.class, "h", packet, d);
		this.setField(Packet63WorldParticles.class, "i", packet, count);

		return packet;
	}

	private String getId(ParticleEffect effect) {
		switch (effect) {
			case ANGRY_VILLAGER:
				return "angryVillager";
			case BUBBLE:
				return "bubble";
			case CLOUD:
				return "cloud";
			case CRITICAL:
				return "crit";
			case DEPTH_SUSPEND:
				return "depthSuspend";
			case DRIP_LAVA:
				return "dripLava";
			case DRIP_WATER:
				return "dripWater";
			case ENCHANTMENT_TABLE:
				return "enchantmenttable";
			case EXPLOSION:
				return "explode";
			case FIREWORKS_SPARK:
				return "fireworksSpark";
			case FLAME:
				return "flame";
			case FOOTSTEP:
				return "footstep";
			case HAPPY_VILLAGER:
				return "happyVillager";
			case HEART:
				return "heart";
			case HUGE_EXPLOSION:
				return "hugeexplosion";
			case ICONCRACK:
				return "iconcrack_";
			case INSTANT_SPELL:
				return "instantSpell";
			case LARGE_EXPLOSION:
				return "largeexplode";
			case LARGE_SMOKE:
				return "largesmoke";
			case LAVA:
				return "lava";
			case MAGIC_CRITICAL:
				return "magicCrit";
			case MOB_SPELL:
				return "mobSpell";
			case MOB_SPELL_AMBIENT:
				return "mobSpellAmbient";
			case NOTE:
				return "note";
			case PORTAL:
				return "portal";
			case REDSTONE_DUST:
				return "reddust";
			case SLIME:
				return "slime";
			case SNOWBALL_POOF:
				return "snowballpoof";
			case SNOW_SHOVEL:
				return "snowshovel";
			case SPELL:
				return "spell";
			case SPLASH:
				return "splash";
			case SUSPEND:
				return "suspend";
			case TILECRACK:
				return "tilecrack_";
			case TOWN_AURA:
				return "townaura";
			case WITCH_MAGIC:
				return "witchMagic";
			default:
				return "heart";
		}
	}

	private void setField(Class<?> clazz, String field, Object target, Object object) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			f.set(target, object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}