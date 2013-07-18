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
package me.cybermaxke.elementalarrows.bukkit.plugin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;

import me.cybermaxke.elementalarrows.bukkit.api.ElementalArrows;
import me.cybermaxke.elementalarrows.bukkit.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.bukkit.api.ParticleEffect;
import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalSkeleton;
import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalTurret;
import me.cybermaxke.elementalarrows.bukkit.plugin.cmd.Commands;
import me.cybermaxke.elementalarrows.bukkit.plugin.config.ElementalConfigFile;
import me.cybermaxke.elementalarrows.bukkit.plugin.dispenser.nms.DispenseBehaviorManager;
import me.cybermaxke.elementalarrows.bukkit.plugin.entity.CraftElementalPlayer;
import me.cybermaxke.elementalarrows.bukkit.plugin.entity.nms.EntityElementalArrow;
import me.cybermaxke.elementalarrows.bukkit.plugin.entity.nms.EntityElementalSkeleton;
import me.cybermaxke.elementalarrows.bukkit.plugin.entity.nms.EntityElementalTurret;
import me.cybermaxke.elementalarrows.bukkit.plugin.entity.nms.EntityManager;
import me.cybermaxke.elementalarrows.bukkit.plugin.item.nms.ItemManager;
import me.cybermaxke.elementalarrows.bukkit.plugin.listeners.EventListener;
import me.cybermaxke.elementalarrows.bukkit.plugin.material.MaterialManager;
import me.cybermaxke.elementalarrows.bukkit.plugin.textures.TextureManager;
import me.cybermaxke.elementalarrows.bukkit.plugin.utils.Metrics;

import net.minecraft.server.v1_6_R2.Block;
import net.minecraft.server.v1_6_R2.Packet63WorldParticles;
import net.minecraft.server.v1_6_R2.World;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class ElementalArrowsPlugin extends JavaPlugin implements ElementalArrowsAPI {
	private static ElementalArrowsPlugin instance;

	private ElementalConfigFile config;
	private ItemManager itemManager;
	private EntityManager entityManager;
	private Metrics metrics;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {
		ElementalArrows.setAPI(this);

		if (!this.getServer().getPluginManager().isPluginEnabled("Spout")) {
			this.getLogger().log(Level.WARNING, "You need to install the SpoutPlugin before you can use this!");
			this.getLogger().log(Level.WARNING, "Disabling...");
			this.setEnabled(false);
			return;
		}

		this.config = new ElementalConfigFile(this);

		new TextureManager(this);
		new MaterialManager(this);
		new EventListener(this);
		new Commands(this);
		new DispenseBehaviorManager();

		this.itemManager = new ItemManager();
		this.entityManager = new EntityManager();

		try {
			this.metrics = new Metrics(this);
			this.metrics.start();
			this.getLogger().log(Level.INFO, "The metrics are loaded.");
		} catch (Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.WARNING, "Couldn't load the metrics!");
		}

		this.getLogger().log(Level.INFO, "The plugin is loaded.");
	}

	@Override
	public void onDisable() {
		if (this.itemManager == null) {
			return;
		}

		this.itemManager.reset();
		this.entityManager.reset();

		try {
			this.metrics.disable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public ElementalConfigFile getConfigFile() {
		return this.config;
	}

	public static ElementalArrowsPlugin getInstance() {
		return instance;
	}

	@Override
	public ElementalPlayer getPlayer(Player player) {
		return CraftElementalPlayer.getPlayer(player);
	}

	@Override
	public boolean isRegionProtected(Location location) {
		if (Bukkit.getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
			WorldGuardPlugin worldguard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
 			return !worldguard.getRegionManager(location.getWorld()).getApplicableRegions(location).allows(DefaultFlag.TNT);
		}
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
	public void playEffect(ParticleEffect effect, Location location, Vector offset, float velocity, int count) {
		this.playEffect(effect, location, offset, velocity, count, new Object[] {});
	}

	@Override
	public void playEffect(Player player, ParticleEffect effect, Location location, Vector offset, float velocity, int count, Object... data) {
		Packet63WorldParticles packet = this.getParticlePacket(effect, location, offset, velocity, count, data);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public void playEffect(Player player, ParticleEffect effect, Location location, Vector offset, float velocity, int count) {
		this.playEffect(player, effect, location, offset, velocity, count, new Object[] {});
	}

	@Override
	public void playEffect(ParticleEffect effect, Location location, Vector offset, float velocity, int count, Object... data) {
		Packet63WorldParticles packet = this.getParticlePacket(effect, location, offset, velocity, count, data);
		for (Player player : location.getWorld().getPlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	protected Packet63WorldParticles getParticlePacket(ParticleEffect effect, Location location, Vector offset, float velocity, int count, Object... data) {
		String name = this.getParticleName(effect);
		switch (effect) {
			case ICONCRACK:
				name = name + (data.length > 0 ? data[0] : 1);
				break;
			case TILECRACK:
				name = name + (data.length > 0 ? data[0] : 1) + "_" + (data.length > 1 ? data[1] : 0);
				break;
			default:
				break;
		}

		Packet63WorldParticles packet = new Packet63WorldParticles();
		this.setField(Packet63WorldParticles.class, "a", packet, name);
		this.setField(Packet63WorldParticles.class, "b", packet, (float) location.getX());
		this.setField(Packet63WorldParticles.class, "c", packet, (float) location.getY());
		this.setField(Packet63WorldParticles.class, "d", packet, (float) location.getZ());
		this.setField(Packet63WorldParticles.class, "e", packet, (float) offset.getX());
		this.setField(Packet63WorldParticles.class, "f", packet, (float) offset.getY());
		this.setField(Packet63WorldParticles.class, "g", packet, (float) offset.getZ());
		this.setField(Packet63WorldParticles.class, "h", packet, velocity);
		this.setField(Packet63WorldParticles.class, "i", packet, count);

		return packet;
	}

	protected String getParticleName(ParticleEffect effect) {
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
			case HEART:
			default:
				return "heart";
		}
	}

	protected void setField(Class<?> clazz, String field, Object target, Object object) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			f.set(target, object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}