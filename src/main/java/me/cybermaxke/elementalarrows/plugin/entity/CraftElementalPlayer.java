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
package me.cybermaxke.elementalarrows.plugin.entity;

import java.lang.reflect.Field;
import java.util.Random;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.api.inventory.TurretInventory;
import me.cybermaxke.elementalarrows.plugin.container.ContainerTurret;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalArrow;
import me.cybermaxke.elementalarrows.plugin.inventory.CraftTurretInventory;
import me.cybermaxke.elementalarrows.plugin.inventory.nms.InventoryTurret;
import me.cybermaxke.elementalarrows.plugin.player.ElementalPlayerConnection;

import net.minecraft.server.v1_5_R3.Container;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.Packet100OpenWindow;

import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import org.getspout.spout.player.SpoutCraftPlayer;

public class CraftElementalPlayer extends SpoutCraftPlayer implements ElementalPlayer {
	private Random random = new Random();

	public CraftElementalPlayer(EntityPlayer player) {
		super(player.world.getServer(), player);
		this.getPlayerConnection();
	}

	@Override
	public ElementalArrow shootElementalArrow(float speed) {
		EntityPlayer player = this.getHandle();
		EntityElementalArrow arrow = new EntityElementalArrow(player.world, player, speed);
		player.world.addEntity(arrow);
		player.world.makeSound(player, "random.bow", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
		return arrow.getBukkitEntity();
	}

	@Override
	public int getArrowsInBody() {
		return this.getHandle().getDataWatcher().getByte(10);
	}

	@Override
	public void setArrowsInBody(int amount) {
		try {
			this.getHandle().getDataWatcher().watch(10, new Byte((byte) amount));
		} catch (Exception e) {
			this.getHandle().getDataWatcher().a(10, new Byte((byte) amount));
		}
	}

	@Override
	public InventoryView openInventory(Inventory inventory) {
		if (inventory instanceof TurretInventory) {
			InventoryTurret turret = ((CraftTurretInventory) inventory).getInventory();

			EntityPlayer p = this.getHandle();		
			Container container = CraftEventFactory.callInventoryOpenEvent(p, new ContainerTurret(p.inventory, turret));
			if (container == null) {
				return null;
			}

			int c = p.nextContainerCounter();
			p.playerConnection.sendPacket(new Packet100OpenWindow(c, 3, turret.getName(), turret.getSize(), turret.c()));
			p.activeContainer = container;
			p.activeContainer.windowId = c;
			p.activeContainer.addSlotListener(p);
			return container.getBukkitView();
		}
		return super.openInventory(inventory);
	}

	@Override
	public ElementalPlayerConnection getPlayerConnection() {
		if (!(this.getHandle().playerConnection instanceof ElementalPlayerConnection)) {
			this.getHandle().playerConnection = new ElementalPlayerConnection(this.getHandle());
		}
		return (ElementalPlayerConnection) this.getHandle().playerConnection;
	}

	public static CraftElementalPlayer getPlayer(Player player) {
		SpoutCraftPlayer p = (SpoutCraftPlayer) SpoutCraftPlayer.getPlayer(player);
		if (p instanceof CraftElementalPlayer) {
			p.getPlayerConnection();
			return (CraftElementalPlayer) p;
		}

		CraftElementalPlayer p2 = new CraftElementalPlayer(p.getHandle());
		for (Field f : SpoutCraftPlayer.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				f.set(p2, f.get(p));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			Field f = Entity.class.getDeclaredField("bukkitEntity");
			f.setAccessible(true);
			f.set(p.getHandle(), p2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p2;
	}
}