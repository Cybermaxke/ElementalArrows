/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.spigot.v1800.entity;

import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.Plugin;

import me.cybermaxke.elementarrows.common.util.MapSerializer;
import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EntityTracker;
import net.minecraft.server.v1_8_R1.EntityTrackerEntry;
import net.minecraft.server.v1_8_R1.IWorldAccess;
import net.minecraft.server.v1_8_R1.IntHashMap;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R1.WorldServer;

public class FEntityTickHandler implements IWorldAccess, Listener {
	private static Field field0 = null;
	private static Field field1 = null;
	private static Field field2 = null;

	private Map<EntityTracker, Entry> sets = new WeakHashMap<EntityTracker, Entry>();

	static class Entry {
		private Set<EntityTrackerEntry> set;
		private IntHashMap map;
	}

	public void onInit(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onWorldInit(WorldInitEvent event) {
		((CraftWorld) event.getWorld()).getHandle().addIWorldAccess(this);
	}

	@Override
	public void a(Entity entity) {
		WorldServer world = (WorldServer) entity.world;
		EntityTracker tracker = world.getTracker();

		if (field0 == null) {
			field0 = Fields.findField(EntityTracker.class, Set.class, 0);
			field1 = Fields.findField(EntityTracker.class, IntHashMap.class, 0);
			field2 = Fields.findField(FEntityTrackerEntry.class, boolean.class, 1);
		}

		Set<EntityTrackerEntry> entries = null;
		IntHashMap map = null;

		if (this.sets.containsKey(tracker)) {
			entries = this.sets.get(tracker).set;
			map = this.sets.get(tracker).map;
		} else {
			field0.setAccessible(true);
			field1.setAccessible(true);

			try {
				entries = (Set<EntityTrackerEntry>) field0.get(tracker);
				map = (IntHashMap) field1.get(tracker);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Entry entry = new Entry();
			entry.set = entries;
			entry.map = map;

			this.sets.put(tracker, entry);
		}

		EntityTrackerEntry entry = (EntityTrackerEntry) map.d(entity.getId());

		boolean velocity = false;

		/**
		 * Get the field.
		 */
		try {
			field2.setAccessible(true);
			velocity = field2.getBoolean(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}

		FEntityTrackerEntry entry0 = new FEntityTrackerEntry(entry.tracker, entry.b, entry.c, velocity);

		entries.remove(entry);
		entries.add(entry0);
		map.a(entity.getId(), entry0);
	}

	@Override
	public void a(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}

	@Override
	public void a(String arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {}

	@Override
	public void b(Entity arg0) {}

	@Override
	public void a(BlockPosition arg0) {}

	@Override
	public void a(String arg0, BlockPosition arg1) {}

	@Override
	public void a(int arg0, BlockPosition arg1, int arg2) {}

	@Override
	public void a(EntityHuman arg0, int arg1, BlockPosition arg2, int arg3) {}

	@Override
	public void a(EntityHuman arg0, String arg1, double arg2, double arg3, double arg4, float arg5, float arg6) {}

	@Override
	public void a(int arg0, boolean arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7, int... arg8) {}

	@Override
	public void b(BlockPosition arg0) {}

	@Override
	public void b(int arg0, BlockPosition arg1, int arg2) {}

	static class FEntityTrackerEntry extends EntityTrackerEntry {

		public FEntityTrackerEntry(Entity entity, int blocksDistanceThreshold, int updateFrequency, boolean sendVelocityUpdates) {
			super(entity, blocksDistanceThreshold, updateFrequency, sendVelocityUpdates);
		}

		@Override
		public void track(List list) {
			super.track(list);

			if (this.m % this.c == 0) {
				FEntity entity = FEntity.of(this.tracker);

				if (entity.properties != null) {
					Map<String, Serializable> map = entity.properties.getDirtyMapAndReset();

					if (!map.isEmpty()) {
						try {
							this.broadcastIncludingSelf(this.toPacket(map));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		@Override
		public void updatePlayer(EntityPlayer player) {
			super.updatePlayer(player);

			FEntity entity = FEntity.of(this.tracker);
			Map<String, Serializable> map = entity.properties.getDelegate();
			PacketPlayOutCustomPayload packet = null;

			try {
				packet = this.toPacket(map);
			} catch (IOException e) {
				e.printStackTrace();
			}

			player.playerConnection.sendPacket(packet);
		}

		public PacketPlayOutCustomPayload toPacket(Map<String, Serializable> map) throws IOException {
			PacketDataSerializer buffer = new PacketDataSerializer(Unpooled.buffer());
			buffer.b(this.tracker.getId());
			buffer.writeBytes(MapSerializer.toByteArray(map));

			return new PacketPlayOutCustomPayload("elementArrowsEntProps", buffer);
		}

	}

}