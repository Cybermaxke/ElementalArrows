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
package me.cybermaxke.elementarrows.forge.v1710.entity;

import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import me.cybermaxke.elementarrows.common.util.MapSerializer;
import me.cybermaxke.elementarrows.forge.v1710.util.Fields;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.util.IntHashMap;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FEntityTickHandler implements IWorldAccess {
	private static Field field0 = null;
	private static Field field1 = null;
	private static Field field2 = null;

	private Map<EntityTracker, Entry> sets = new WeakHashMap<EntityTracker, Entry>();

	static class Entry {
		private Set<EntityTrackerEntry> set;
		private IntHashMap map;
	}
	
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onWorldInit(WorldEvent.Save event) {
		World world = event.world;

		if (world instanceof WorldServer) {
			WorldServer world0 = (WorldServer) world;
			EntityTracker tracker = world0.getEntityTracker();

			if (field0 == null) {
				field0 = Fields.findField(EntityTracker.class, Set.class, 0);
				field1 = Fields.findField(EntityTracker.class, IntHashMap.class, 0);
				field2 = Fields.findField(EntityTrackerEntry.class, boolean.class, 1);
			}

			field0.setAccessible(true);
			field1.setAccessible(true);

			try {
				IntHashMap map = (IntHashMap) field1.get(tracker);

				/**
				 * Update the entity tracker entry set to support concurrent calls. Fixes errors.
				 */
				Set<EntityTrackerEntry> entries0 = (Set<EntityTrackerEntry>) field0.get(tracker);
				Set<EntityTrackerEntry> entries1 = Collections.newSetFromMap(new ConcurrentHashMap<EntityTrackerEntry, Boolean>());

				if (!entries0.isEmpty()) {
					entries1.addAll(entries0);
				}

				field0.set(tracker, entries1);

				/**
				 * Store the map and set for faster lookup.
				 */
				Entry entry = new Entry();
				entry.set = entries1;
				entry.map = map;

				this.sets.put(tracker, entry);
			} catch (Exception e) {
				e.printStackTrace();
			}

			world.addWorldAccess(this);
		}
	}

	@Override
	public void onEntityCreate(Entity entity) {
		if (!(entity.worldObj instanceof WorldServer)) {
			return;
		}

		WorldServer world = (WorldServer) entity.worldObj;
		EntityTracker tracker = world.getEntityTracker();
		Entry en = this.sets.get(tracker);

		Set<EntityTrackerEntry> entries = en.set;
		IntHashMap map = en.map;

		EntityTrackerEntry entry = (EntityTrackerEntry) map.lookup(entity.getEntityId());
		if (entry instanceof FEntityTrackerEntry) {
			return;
		}

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

		FEntityTrackerEntry entry0 = new FEntityTrackerEntry(entity, entry.blocksDistanceThreshold, entry.updateFrequency, velocity);

		synchronized (entries) {
			entries.remove(entry);
			entries.add(entry0);
		}

		map.addKey(entity.getEntityId(), entry0);
	}

	@Override
	public void broadcastSound(int arg0, int arg1, int arg2, int arg3, int arg4) {}

	@Override
	public void destroyBlockPartially(int arg0, int arg1, int arg2, int arg3, int arg4) {}

	@Override
	public void markBlockForRenderUpdate(int arg0, int arg1, int arg2) {}

	@Override
	public void markBlockForUpdate(int arg0, int arg1, int arg2) {}

	@Override
	public void markBlockRangeForRenderUpdate(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}

	@Override
	public void onEntityDestroy(Entity arg0) {}

	@Override
	public void onStaticEntitiesChanged() {}

	@Override
	public void playAuxSFX(EntityPlayer arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}

	@Override
	public void playRecord(String arg0, int arg1, int arg2, int arg3) {}

	@Override
	public void playSound(String arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {}

	@Override
	public void playSoundToNearExcept(EntityPlayer arg0, String arg1, double arg2, double arg3, double arg4, float arg5, float arg6) {}

	@Override
	public void spawnParticle(String arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6) {}

	static class FEntityTrackerEntry extends EntityTrackerEntry {

		public FEntityTrackerEntry(Entity entity, int blocksDistanceThreshold, int updateFrequency, boolean sendVelocityUpdates) {
			super(entity, blocksDistanceThreshold, updateFrequency, sendVelocityUpdates);
		}

		@Override
		public void sendLocationToAllClients(List list) {
			super.sendLocationToAllClients(list);

			if (this.ticks % this.updateFrequency == 0) {
				FEntity entity = FEntity.of(this.myEntity);

				if (entity.properties != null) {
					Map<String, Serializable> map = entity.properties.getDirtyMapAndReset();

					if (!map.isEmpty()) {
						try {
							this.func_151261_b(this.toPacket(map));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		@Override
		public void tryStartWachingThis(EntityPlayerMP player) {
			super.tryStartWachingThis(player);

			FEntity entity = FEntity.of(this.myEntity);
			Map<String, Serializable> map = entity.properties.getDelegate();
			S3FPacketCustomPayload packet = null;

			if (map.isEmpty()) {
				return;
			}

			try {
				packet = this.toPacket(map);
			} catch (IOException e) {
				e.printStackTrace();
			}

			player.playerNetServerHandler.sendPacket(packet);
		}

		public S3FPacketCustomPayload toPacket(Map<String, Serializable> map) throws IOException {
			PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
			buffer.writeVarIntToBuffer(this.myEntity.getEntityId());
			buffer.writeBytes(MapSerializer.toByteArray(map));

			return new S3FPacketCustomPayload("elementArrowsEntProps", buffer);
		}

	}

}