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
package me.cybermaxke.elementalarrows.bukkit.plugin.container;

import org.bukkit.craftbukkit.v1_6_R1.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_6_R1.inventory.CraftInventoryView;
import org.bukkit.inventory.InventoryView;

import me.cybermaxke.elementalarrows.bukkit.plugin.inventory.nms.InventoryTurret;

import net.minecraft.server.v1_6_R1.Container;
import net.minecraft.server.v1_6_R1.EntityHuman;
import net.minecraft.server.v1_6_R1.ItemStack;
import net.minecraft.server.v1_6_R1.PlayerInventory;
import net.minecraft.server.v1_6_R1.Slot;

public class ContainerTurret extends Container {
	private InventoryView view;
	private InventoryTurret inventory;
	private PlayerInventory player;

	public ContainerTurret(PlayerInventory inventory, InventoryTurret turret) {
		this.inventory = turret;
		this.player = inventory;
		this.view = new CraftInventoryView(inventory.player.getBukkitEntity(), new CraftInventory(this.inventory), this);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.a(new Slot(this.inventory, j + i * 3, 62 + j * 18, 17 + i * 18));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.a(new Slot(this.player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.a(new Slot(this.player, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean a(EntityHuman entityhuman) {
		if (!this.checkReachable) {
			return true;
		}
		return this.inventory.a(entityhuman);
	}

	@Override
	public ItemStack b(EntityHuman human, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.c.get(i);

		if (slot != null && slot.e()) {
			ItemStack itemstack1 = slot.getItem();

			itemstack = itemstack1.cloneItemStack();
			if (i < 9) {
				if (!this.a(itemstack1, 9, 45, true)) {
					return null;
				}
			} else if (!this.a(itemstack1, 0, 9, false)) {
				return null;
			}

			if (itemstack1.count == 0) {
				slot.set((ItemStack) null);
			} else {
				slot.f();
			}

			if (itemstack1.count == itemstack.count) {
				return null;
			}

			slot.a(human, itemstack1);
		}

		return itemstack;
	}

	@Override
	public InventoryView getBukkitView() {
		return this.view;
	}
}