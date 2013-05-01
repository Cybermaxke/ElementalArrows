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
package me.cybermaxke.elementalarrows.plugin.craftbukkit.player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_5_R2.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.getspout.spout.SpoutPlayerConnection;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.api.material.CustomItem;

import net.minecraft.server.v1_5_R2.EntityPlayer;
import net.minecraft.server.v1_5_R2.ItemStack;
import net.minecraft.server.v1_5_R2.MinecraftServer;
import net.minecraft.server.v1_5_R2.Packet;
import net.minecraft.server.v1_5_R2.Packet103SetSlot;
import net.minecraft.server.v1_5_R2.Packet104WindowItems;
import net.minecraft.server.v1_5_R2.Packet40EntityMetadata;
import net.minecraft.server.v1_5_R2.Packet5EntityEquipment;
import net.minecraft.server.v1_5_R2.WatchableObject;

/**
 * Changing the flint items to normal arrows for the players who aren't using Spoutcraft.
 */
public class ElementalPlayerConnection extends SpoutPlayerConnection {

	public ElementalPlayerConnection(EntityPlayer player) {
		super(MinecraftServer.getServer(), player.playerConnection.networkManager, player);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sendPacket(Packet packet) {
		SpoutPlayer sp = SpoutManager.getPlayer(this.player.getBukkitEntity());

		if (packet instanceof Packet103SetSlot) {
			Packet103SetSlot p = (Packet103SetSlot) packet;
			ItemStack is = sp.isSpoutCraftEnabled() ? this.getOldItemStack(p.c) : this.getUpdatedItemStack(p.c);
			Packet103SetSlot p2 = new Packet103SetSlot(p.a, p.b, is);
			super.sendPacket(p2);
			return;
		} else if (packet instanceof Packet104WindowItems) {
			Packet104WindowItems p = (Packet104WindowItems) packet;
			List<ItemStack> is = new ArrayList<ItemStack>();
			for (int i = 0; i < p.b.length; i++) {
				is.add(sp.isSpoutCraftEnabled() ? this.getOldItemStack(p.b[i]) : this.getUpdatedItemStack(p.b[i]));
			}
			Packet104WindowItems p2 = new Packet104WindowItems(p.a, is);
			super.sendPacket(p2);
			return;
		} else if (packet instanceof Packet5EntityEquipment) {
			Packet5EntityEquipment p = (Packet5EntityEquipment) packet;
			try {
				Field f = Packet5EntityEquipment.class.getDeclaredField("c");
				f.setAccessible(true);
				ItemStack o = (ItemStack) f.get(p);
				ItemStack is = sp.isSpoutCraftEnabled() ? this.getOldItemStack(o) : this.getUpdatedItemStack(o);
				Packet5EntityEquipment p2 = new Packet5EntityEquipment(p.a, p.b, is);
				super.sendPacket(p2);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (packet instanceof Packet40EntityMetadata) {
			Packet40EntityMetadata p = (Packet40EntityMetadata) packet;
			try {
				Field f = Packet40EntityMetadata.class.getDeclaredField("b");
				f.setAccessible(true);
				List<WatchableObject> l = (List<WatchableObject>) f.get(p);
				List<WatchableObject> l2 = new ArrayList<WatchableObject>();
				if (l != null) {
					for (int i = 0; i < l.size(); i++) {
						WatchableObject o = l.get(i);
						if (o.b() != null && o.b() instanceof ItemStack) {
							WatchableObject o2 = new WatchableObject(o.c(), o.a(), sp.isSpoutCraftEnabled() ? this.getOldItemStack((ItemStack) o.b()) : this.getUpdatedItemStack((ItemStack) o.b()));
							l2.add(o2);
						} else {
							l2.add(o);
						}
					}
				}
				Packet40EntityMetadata p2 = new Packet40EntityMetadata();
				p2.a = p.a;
				f.set(p2, l2);
				super.sendPacket(p2);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.sendPacket(packet);
	}

	private ItemStack getUpdatedItemStack(ItemStack item) {
		SpoutItemStack is = item == null ? null : new SpoutItemStack(CraftItemStack.asCraftMirror(item));
		if (is != null && is.isCustomItem()) {
			Material m = is.getMaterial();
			if (m instanceof CustomItem) {
				ItemStack is2 = item.cloneItemStack();
				is2.id = ((CustomItem) m).getId();
				is2.c(ChatColor.RESET + (is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : m.getName()));
				return is2;
			}
		}
		return item;
	}

	private ItemStack getOldItemStack(ItemStack item) {
		if (item == null) {
			return null;
		}

		SpoutItemStack i = new SpoutItemStack(CraftItemStack.asCraftMirror(item));
		if (i.isCustomItem()) {
			return item;
		}

		ItemStack is = item.cloneItemStack();
		int d = is.getData();
		if (d < 1024) {
			return is;
		}

		Material m = MaterialData.getCustomItem(d);
		if (m == null || !(m instanceof ArrowMaterial)) {
			return is;
		}

		String n = ChatColor.RESET + m.getName();
		is.id = m.getRawId();
		SpoutItemStack is2 = new SpoutItemStack(CraftItemStack.asCraftMirror(is));
		if (is2.hasItemMeta()) {
			ItemMeta meta = is2.getItemMeta();
			if (meta.hasDisplayName() && meta.getDisplayName().equals(n)) {
				meta.setDisplayName(null);
			}
			is2.setItemMeta(meta);
		}
		return CraftItemStack.asNMSCopy(is2);
	}
}