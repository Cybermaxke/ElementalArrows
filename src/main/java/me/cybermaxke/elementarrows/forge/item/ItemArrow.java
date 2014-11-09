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
package me.cybermaxke.elementarrows.forge.item;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.arrows.ArrowRegistryCommon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ItemArrow extends Item {
	private ArrowRegistryCommon registry;

	private IIconRegister iconRegistry;
	private IIcon[] icons = new IIcon[Short.MAX_VALUE];

	public ItemArrow(ArrowRegistryCommon registry) {
		this.registry = registry;
		this.setHasSubtypes(true);
		this.setUnlocalizedName("arrow");
		this.setTextureName("arrow");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack, int i) {
		int data = itemStack.getItemDamage();

		if (data != 0) {
			ElementArrow arrow = this.registry.fromData(data);
			if (arrow != null) {
				return itemStack.isItemEnchanted() || arrow.effect;
			}
		}

		return super.hasEffect(itemStack, i);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack itemStack) {
		int data = itemStack.getItemDamage();

		if (data != 0) {
			ElementArrow arrow = this.registry.fromData(data);
			if (arrow != null) {
				return "item." + arrow.unlocalizedName;
			}
		}

		return super.getUnlocalizedName(itemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int data) {
		if (data == 0) {
			return super.getIconFromDamage(data);
		} else {
			IIcon icon = this.icons[data];
			if (icon == null) {
				ElementArrow arrow = this.registry.fromData(data);
				if (arrow != null && arrow.icon != null && this.iconRegistry != null) {
					this.icons[data] = this.iconRegistry.registerIcon(arrow.icon);
					return this.icons[data];
				} else {
					return super.getIconFromDamage(data);
				}
			} else {
				return icon;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		super.registerIcons(registry);

		if (this.iconRegistry == null) {
			this.iconRegistry = registry;
		}

		ElementArrow[] arrows = this.registry.array();

		Collection<Short> data = this.registry.dataValues();
		Iterator<Short> it = data.iterator();

		while (it.hasNext()) {
			int index = it.next();

			if (arrows[index] != null && arrows[index].icon != null && this.icons[index] == null) {
				this.icons[index] = registry.registerIcon(arrows[index].icon);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		super.getSubItems(item, tab, items);

		Collection<Short> data = this.registry.dataValues();
		Iterator<Short> it = data.iterator();

		while (it.hasNext()) {
			items.add(new ItemStack(item, 1, it.next()));
		}
	}

}