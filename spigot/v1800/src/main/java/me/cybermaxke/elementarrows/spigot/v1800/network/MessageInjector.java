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
package me.cybermaxke.elementarrows.spigot.v1800.network;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.locale.Locales;
import me.cybermaxke.elementarrows.common.util.reflect.Fields;
import me.cybermaxke.elementarrows.spigot.v1800.entity.FEntity;
import me.cybermaxke.elementarrows.spigot.v1800.entity.FEntityPlayer;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import net.minecraft.server.v1_8_R1.NBTTagList;
import net.minecraft.server.v1_8_R1.NetworkManager;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R1.PacketPlayInSettings;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class MessageInjector implements Listener {

	/**
	 * Initialize the client protocol injector.
	 */
	public void onInit(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerMPJoinWorld(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		EntityPlayer player0 = ((CraftPlayer) player).getHandle();

		this.onJoinWorld(player0);
	}

	/**
	 * We will inject the packet handler into the players network channel.
	 * 
	 * @param player the mp player
	 */
	private void onJoinWorld(EntityPlayer player) {
		Field field0 = Fields.findField(NetworkManager.class, Channel.class, 0);
		field0.setAccessible(true);

		try {
			Channel channel = (Channel) field0.get(player.playerConnection.networkManager);

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
			if (msg instanceof PacketPlayInSettings) {
				this.language = ((PacketPlayInSettings) msg).a();
			} else if (msg instanceof PacketPlayInCustomPayload) {
				PacketPlayInCustomPayload msg0 = (PacketPlayInCustomPayload) msg;

				if (msg0.a().equals("elementArrowsInit")) {
					PacketDataSerializer buffer = msg0.b();
					String version = buffer.c(Integer.MAX_VALUE);

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
			NBTTagCompound nbt = itemStack.getTag();

			if (nbt != null) {
				/**
				 * This is a fix for the missing textures on clients without the mod.
				 */
				if (nbt.hasKey("EDataValue")) {
					itemStack.setData(nbt.getInt("EDataValue"));
					nbt.remove("EDataValue");
				}

				/**
				 * A fix to support glows on clients without the mod.
				 */
				if (nbt.hasKey("ench")) {
					NBTTagList list = (NBTTagList) nbt.get("ench");
					if (list.size() == 0) {
						nbt.remove("ench");
					}
				}

				/**
				 * A fix for names on clients without the mod.
				 */
				if (nbt.hasKey("display")) {
					nbt = nbt.getCompound("display");
					if (nbt.hasKey("ENameFix")) {
						nbt.remove("ENameFix");
						nbt.remove("Name");
					}
				}
			}

			return itemStack;
		}

		public ItemStack processOut(ItemStack itemStack) {
			NBTTagCompound nbt = itemStack.getTag();
			Item item = itemStack.getItem();

			if (item == Items.ARROW && itemStack.getData() > 0) {
				ElementArrow arrow = Arrows.find(itemStack.getData());

				if (arrow == null) {
					return itemStack;
				}

				if (nbt == null) {
					nbt = new NBTTagCompound();
					itemStack.setTag(nbt);
				}

				/**
				 * A fix for names on clients without the mod.
				 */
				NBTTagCompound display;

				if (nbt.hasKey("display")) {
					display = nbt.getCompound("display");
				} else {
					display = new NBTTagCompound();
					nbt.set("display", display);
				}

				if (!display.hasKey("Name")) {
					itemStack = itemStack.cloneItemStack();
					nbt = itemStack.getTag();
					display = nbt.getCompound("display");

					String name = item.a(itemStack) + ".name";
					String local;

					if (this.language == null) {
						local = Locales.get(name);
					} else {
						local = Locales.get(this.language, name);
					}

					display.setString("Name", this.reset + local);
					display.setBoolean("ENameFix", true);
				}

				/**
				 * A fix to support glows on clients without the mod.
				 */
				if (!nbt.hasKey("ench") && arrow.hasEffect()) {
					nbt.set("ench", new NBTTagList());
				}

				/**
				 * This is a fix for the missing textures on clients without the mod.
				 */
				nbt.setInt("EDataValue", itemStack.getData());
				itemStack.setData(0);
			}

			return itemStack;
		}

	}
}