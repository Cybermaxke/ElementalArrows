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
package me.cybermaxke.elementalarrows.plugin.inventory.nms;

import java.util.ArrayList;
import java.util.List;

import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalTurret;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.ItemStack;

public class InventoryTurret implements IInventory {
	private EntityElementalTurret turret;
	private ItemStack[] items = new ItemStack[9];
	private List<HumanEntity> humans = new ArrayList<HumanEntity>();
	private int maxStackSize;

	public InventoryTurret(EntityElementalTurret turret) {
		this.turret = turret;
		this.maxStackSize = 64;
	}

	@Override
	public boolean a(EntityHuman human) {
		return true;
	}

	@Override
	public boolean b(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean c() {
		return true;
	}

	@Override
	public void g() {

	}

	@Override
	public ItemStack[] getContents() {
		return this.items;
	}

	@Override
	public ItemStack getItem(int i) {
		return this.items[i];
	}

	@Override
	public int getMaxStackSize() {
		return this.maxStackSize;
	}

	@Override
	public String getName() {
		return this.turret.getLocalizedName();
	}

	@Override
	public InventoryHolder getOwner() {
		return this.turret.getBukkitEntity();
	}

	@Override
	public int getSize() {
		return this.items.length;
	}

	@Override
	public List<HumanEntity> getViewers() {
		return this.humans;
	}

	@Override
	public void onClose(CraftHumanEntity human) {
		this.humans.remove(human);
	}

	@Override
	public void onOpen(CraftHumanEntity human) {
		this.humans.add(human);
	}

	@Override
	public void setItem(int i, ItemStack item) {
		this.items[i] = item;
		if (item != null && item.count > this.maxStackSize) {
			item.count = this.maxStackSize;
		}
	}

	@Override
	public void setMaxStackSize(int size) {
		this.maxStackSize = size;
	}

	@Override
	public ItemStack splitStack(int i, int j) {
		if (this.items[i] != null) {
			if (this.items[i].count <= j) {
				ItemStack is = this.items[i];
				this.items[i] = null;
				this.update();
				return is;
			}

			ItemStack is = this.items[i].a(j);
			if (this.items[i].count == 0) {
				this.items[i] = null;
			}

			this.update();
			return is;
		}

		return null;
	}

	@Override
	public ItemStack splitWithoutUpdate(int i) {
		if (this.items[i] != null) {
			ItemStack is = this.items[i];
			this.items[i] = null;
			return is;
		}

		return null;
	}

	@Override
	public void startOpen() {

	}

	@Override
	public void update() {

	}
}