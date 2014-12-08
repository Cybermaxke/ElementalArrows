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
package me.cybermaxke.elementarrows.spigot.v1710.entity;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import me.cybermaxke.elementarrows.common.item.inventory.Inventory;
import me.cybermaxke.elementarrows.common.item.inventory.ItemStack;
import me.cybermaxke.elementarrows.spigot.v1710.inventory.FInventory;
import me.cybermaxke.elementarrows.spigot.v1710.inventory.FItemStack;

public class FEntityPlayer extends FEntityLiving<EntityPlayer> implements me.cybermaxke.elementarrows.common.entity.EntityPlayer {
	public String installedVersion;

	public FEntityPlayer(EntityPlayer entity) {
		super(entity);
	}

	@Override
	public Inventory getInventory() {
		return new FInventory(this.entity.inventory);
	}

	@Override
	public boolean hasClient() {
		return this.installedVersion != null;
	}

	@Override
	public ItemStack getHeldItem() {
		return FItemStack.of(this.entity.inventory.getCarried());
	}

}