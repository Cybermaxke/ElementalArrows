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
package me.cybermaxke.elementarrows.forge.v1710.network;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import me.cybermaxke.elementarrows.common.util.MapSerializer;
import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import me.cybermaxke.elementarrows.forge.v1710.FElementArrows;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntity;
import me.cybermaxke.elementarrows.forge.v1710.util.Mods;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MessageInjectorClient {

	/**
	 * Initialize the client protocol injector.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onClientPlayerMPJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityClientPlayerMP) {
			this.onJoinWorld((EntityClientPlayerMP) event.entity);

			ByteBuf buffer = Unpooled.buffer();
			ByteBufUtils.writeUTF8String(buffer, Mods.getVersionFor(FElementArrows.class));

			/**
			 * Create the custom payload packet.
			 */
			C17PacketCustomPayload packet = new C17PacketCustomPayload("elementArrowsInit", buffer);

			((EntityClientPlayerMP) event.entity).sendQueue.addToSendQueue(packet);
		}
	}

	/**
	 * We will inject the packet handler into the players network channel.
	 * 
	 * @param player the mp player
	 */
	private void onJoinWorld(EntityClientPlayerMP player) {
		Field field0 = Fields.findField(NetHandlerPlayClient.class, NetworkManager.class, 0);
		field0.setAccessible(true);

		Field field1 = Fields.findField(NetworkManager.class, Channel.class, 0);
		field1.setAccessible(true);

		try {
			NetworkManager networkManager = (NetworkManager) field0.get(player.sendQueue);
			Channel channel = (Channel) field1.get(networkManager);

			if (channel.pipeline().get("elementArrows_handler_client") == null) {
				channel.pipeline().addBefore("packet_handler", "elementArrows_handler_client", new Handler());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class Handler extends ChannelDuplexHandler {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			if (msg instanceof S3FPacketCustomPayload) {
				S3FPacketCustomPayload msg0 = (S3FPacketCustomPayload) msg;

				if (msg0.func_149169_c().equals("elementArrowsEntProps")) {
					PacketBuffer buffer = new PacketBuffer(Unpooled.wrappedBuffer(msg0.func_149168_d()));
					World world = Minecraft.getMinecraft().theWorld;

					/**
					 * The target entity id.
					 */
					Entity entity = world.getEntityByID(buffer.readVarIntFromBuffer());

					if (entity != null) {
						FEntity entity0 = FEntity.of(entity);

						Map<String, Serializable> map = MapSerializer.fromByteArray(buffer.readBytes(buffer.readableBytes()).array());
						Map<String, Serializable> delegate = entity0.properties.getDelegate();

						for (Entry<String, Serializable> en : map.entrySet()) {
							Serializable value = en.getValue();

							if (value == null) {
								delegate.remove(en.getKey());
							} else {
								delegate.put(en.getKey(), value);
							}
						}
					}

					return;
				}
			} else if (msg instanceof S0EPacketSpawnObject) {
				S0EPacketSpawnObject msg0 = (S0EPacketSpawnObject) msg;
				WorldClient world = Minecraft.getMinecraft().theWorld;

				/**
				 * We override the default arrow spawn protocol. By default
				 * are just {@link EntityArrow}s spawned.
				 */
				if (msg0.func_148993_l() == 60) {
					double x = msg0.func_148997_d() / 32d;
					double y = msg0.func_148998_e() / 32d;
					double z = msg0.func_148994_f() / 32d;

					EntityElementArrow arrow = new EntityElementArrow(world, x, y, z);

					arrow.serverPosX = msg0.func_148997_d();
					arrow.serverPosY = msg0.func_148998_e();
					arrow.serverPosZ = msg0.func_148994_f();
					arrow.rotationPitch = msg0.func_149008_j() * 360f / 256f;
					arrow.rotationYaw = msg0.func_149006_k() * 360f / 256f;

					world.addEntityToWorld(msg0.func_149001_c(), arrow);

					if (msg0.func_149009_m() > 0) {
						Entity entity = world.getEntityByID(msg0.func_149009_m());

						if (entity instanceof EntityLivingBase) {
							arrow.shootingEntity = entity;
						}

						double motionX = msg0.func_149010_g() / 8000d;
						double motionY = msg0.func_149004_h() / 8000d;
						double motionZ = msg0.func_148999_i() / 8000d;

						arrow.setVelocity(motionX, motionY, motionZ);
					}
					return;
				}
			}

			super.channelRead(ctx, msg);
		}

	}
}