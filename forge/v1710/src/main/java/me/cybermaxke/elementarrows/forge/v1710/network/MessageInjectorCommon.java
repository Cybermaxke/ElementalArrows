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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntity;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityPlayer;
import me.cybermaxke.elementarrows.forge.v1710.util.Fields;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C17PacketCustomPayload;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MessageInjectorCommon {

	/**
	 * Initialize the client protocol injector.
	 */
	public void onInit() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerMPJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			this.onJoinWorld((EntityPlayerMP) event.entity);
		}
	}

	/**
	 * We will inject the packet handler into the players network channel.
	 * 
	 * @param player the mp player
	 */
	private void onJoinWorld(EntityPlayerMP player) {
		Field field0 = Fields.findField(NetworkManager.class, Channel.class, 0);
		field0.setAccessible(true);

		try {
			Channel channel = (Channel) field0.get(player.playerNetServerHandler.netManager);

			if (channel.pipeline().get("elementArrows_handler") == null) {
				FEntityPlayer fplayer = FEntity.of(player);
				channel.pipeline().addBefore("packet_handler", "elementArrows_handler", new Handler(fplayer));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final class Handler extends ChannelDuplexHandler {
		/**
		 * ItemStack field cache.
		 */
		private final Map<Class<?>, Field[]> fields0 = new HashMap<Class<?>, Field[]>();
		private final Map<Class<?>, Field[]> fields1 = new HashMap<Class<?>, Field[]>();

		/**
		 * The reset color. 
		 */
		private final String reset = new StringBuilder().append('\u00A7').append('f').toString();
		private final FEntityPlayer player;

		/**
		 * The language of the player.
		 */
		private String language;

		public Handler(FEntityPlayer player) {
			this.player = player;
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			if (msg instanceof C15PacketClientSettings) {
				this.language = ((C15PacketClientSettings) msg).func_149524_c();
			} else if (msg instanceof C17PacketCustomPayload) {
				C17PacketCustomPayload msg0 = (C17PacketCustomPayload) msg;

				if (msg0.func_149559_c().equals("elementArrowsInit")) {
					ByteBuf buffer = Unpooled.copiedBuffer(msg0.func_149558_e());
					String version = ByteBufUtils.readUTF8String(buffer);

					this.player.installedVersion = version;
				}
			}

			Class<?> type = msg.getClass();

			if (!this.fields0.containsKey(type)) {
				this.fields0.put(type, Fields.findFields(type, ItemStack.class, -1));
				this.fields1.put(type, Fields.findFields(type, ItemStack[].class, -1));
			}

			Field[] fields0 = this.fields0.get(type);
			if (fields0.length > 0) {
				for (int i = 0; i < fields0.length; i++) {
					fields0[i].setAccessible(true);

					ItemStack itemStack = (ItemStack) fields0[i].get(msg);
					if (itemStack != null) {
						this.processIn(itemStack);
					}
				}
			}
			Field[] fields1 = this.fields1.get(type);
			if (fields1.length > 0) {
				for (int i = 0; i < fields1.length; i++) {
					fields1[i].setAccessible(true);

					ItemStack[] itemStacks = (ItemStack[]) fields1[i].get(msg);
					if (itemStacks != null && itemStacks.length > 0) {
						for (int j = 0; j < itemStacks.length; j++) {
							if (itemStacks[j] != null) {
								this.processIn(itemStacks[j]);
							}
						}
					}
				}
			}

			super.channelRead(ctx, msg);
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			if (this.player.hasClient()) {
				super.write(ctx, msg, promise);
				return;
			}

			Class<?> type = msg.getClass();

			if (!this.fields0.containsKey(type)) {
				this.fields0.put(type, Fields.findFields(type, ItemStack.class, -1));
				this.fields1.put(type, Fields.findFields(type, ItemStack[].class, -1));
			}

			Field[] fields0 = this.fields0.get(type);
			if (fields0.length > 0) {
				for (int i = 0; i < fields0.length; i++) {
					fields0[i].setAccessible(true);

					ItemStack itemStack = (ItemStack) fields0[i].get(msg);
					if (itemStack != null) {
						fields0[i].set(msg, this.processOut(itemStack));
					}
				}
			}
			Field[] fields1 = this.fields1.get(type);
			if (fields1.length > 0) {
				for (int i = 0; i < fields1.length; i++) {
					fields1[i].setAccessible(true);

					ItemStack[] itemStacks = (ItemStack[]) fields1[i].get(msg);
					if (itemStacks != null && itemStacks.length > 0) {
						ItemStack[] itemStacks0 = new ItemStack[itemStacks.length];
						for (int j = 0; j < itemStacks.length; j++) {
							if (itemStacks[j] != null) {
								itemStacks0[j] = this.processOut(itemStacks[j]);
							}
						}
						fields1[i].set(msg, itemStacks0);
					}
				}
			}

			super.write(ctx, msg, promise);
		}

		public ItemStack processIn(ItemStack itemStack) {
			NBTTagCompound nbt = itemStack.stackTagCompound;

			if (nbt != null) {
				if (nbt.hasKey("ench")) {
					NBTTagList list = (NBTTagList) nbt.getTag("ench");
					if (list.tagCount() == 0) {
						nbt.removeTag("ench");
					}
				}

				if (nbt.hasKey("display")) {
					nbt = nbt.getCompoundTag("display");
					if (nbt.hasKey("ENameFix")) {
						nbt.removeTag("ENameFix");
						nbt.removeTag("Name");
					}
				}
			}

			return itemStack;
		}

		public ItemStack processOut(ItemStack itemStack) {
			NBTTagCompound nbt = itemStack.stackTagCompound;
			Item item = itemStack.getItem();

			if (item == Items.arrow && itemStack.getItemDamage() > 0) {
				ElementArrow arrow = Arrows.find(itemStack.getItemDamage());

				if (arrow == null) {
					return itemStack;
				}

				if (nbt == null) {
					nbt = new NBTTagCompound();
					itemStack.setTagCompound(nbt);
				}

				NBTTagCompound display;

				if (nbt.hasKey("display")) {
					display = nbt.getCompoundTag("display");
				} else {
					display = new NBTTagCompound();
					nbt.setTag("display", display);
				}

				if (!display.hasKey("Name")) {
					itemStack = itemStack.copy();
					nbt = itemStack.stackTagCompound;
					display = nbt.getCompoundTag("display");

					String name = item.getUnlocalizedName(itemStack) + ".name";
					String local;

					if (this.language == null) {
						local = LanguageRegistry.instance().getStringLocalization(name);
					} else {
						local = LanguageRegistry.instance().getStringLocalization(name, this.language);
					}

					display.setString("Name", this.reset + local);
					display.setBoolean("ENameFix", true);
				}

				if (!nbt.hasKey("ench") && arrow.hasEffect()) {
					nbt.setTag("ench", new NBTTagList());
				}
			}

			return itemStack;
		}

	}
}