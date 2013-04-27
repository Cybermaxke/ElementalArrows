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
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_5_R2.inventory.CraftItemStack;

import org.getspout.spout.SpoutPlayerConnection;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;

import net.minecraft.server.v1_5_R2.EntityPlayer;
import net.minecraft.server.v1_5_R2.Item;
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
		if (sp.isSpoutCraftEnabled()) {
			super.sendPacket(packet);
			return;
		}

		if (packet instanceof Packet103SetSlot) {
			Packet103SetSlot p = (Packet103SetSlot) packet;
			if (p.c != null) {
				p.c = this.getUpdatedItemStack(p.c);
			}
		} else if (packet instanceof Packet104WindowItems) {
			Packet104WindowItems p = (Packet104WindowItems) packet;
			for (int i = 0; i < p.b.length; i++) {
				if (p.b[i] != null) {
					p.b[i] = this.getUpdatedItemStack(p.b[i]);
				}
			}
		} else if (packet instanceof Packet5EntityEquipment) {
			Packet5EntityEquipment p = (Packet5EntityEquipment) packet;
			try {
				Field f = Packet5EntityEquipment.class.getDeclaredField("c");
				f.setAccessible(true);
				ItemStack is = (ItemStack) f.get(p);
				if (is != null) {
					f.set(p, this.getUpdatedItemStack(is));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (packet instanceof Packet40EntityMetadata) {
			Packet40EntityMetadata p = (Packet40EntityMetadata) packet;
			try {
				Field f = Packet40EntityMetadata.class.getDeclaredField("b");
				f.setAccessible(true);
				List<WatchableObject> l = (List<WatchableObject>) f.get(p);
				if (l != null) {
					for (int i = 0; i < l.size(); i++) {
						WatchableObject o = l.get(i);
						if (o.b() != null && o.b() instanceof ItemStack) {
							o.a(this.getUpdatedItemStack((ItemStack) o.b()));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.sendPacket(packet);
	}

	private ItemStack getUpdatedItemStack(ItemStack item) {
		SpoutItemStack is = new SpoutItemStack(CraftItemStack.asCraftMirror(item));
		if (is.isCustomItem()) {
			Material m = is.getMaterial();
			if (m instanceof ArrowMaterial) {
				ItemStack is2 = item.cloneItemStack();
				is2.id = Item.ARROW.id;
				is2.c(ChatColor.RESET + (is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : m.getName()));
				return is2;
			}
		}
		return item;
	}
}