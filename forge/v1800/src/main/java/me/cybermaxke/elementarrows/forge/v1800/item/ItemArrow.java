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
package me.cybermaxke.elementarrows.forge.v1800.item;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class ItemArrow extends Item {
	/**
	private IIconRegister iconRegistry;
	private IIcon[] icons = new IIcon[Short.MAX_VALUE];*/

	public ItemArrow() {
		this.setHasSubtypes(true);
		this.setUnlocalizedName("arrow");
		//this.setTextureName("arrow");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemStack) {
		int data = itemStack.getItemDamage();

		if (data != 0) {
			ElementArrow arrow = Arrows.find(data);
			if (arrow != null) {
				return itemStack.isItemEnchanted() || arrow.hasEffect();
			}
		}

		return super.hasEffect(itemStack);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		int data = itemStack.getItemDamage();

		if (data != 0) {
			ElementArrow arrow = Arrows.find(data);
			if (arrow != null) {
				return "item." + arrow.getUnlocalizedName();
			}
		}

		return super.getUnlocalizedName(itemStack);
	}

	/**
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int data) {
		if (data == 0) {
			return super.getIconFromDamage(data);
		} else {
			IIcon icon = this.icons[data];

			if (icon == null) {
				ElementArrow arrow = Arrows.find(data);

				if (arrow != null) {
					String icon0 = arrow.getIcon();

					if (icon0 != null) {
						this.icons[data] = this.iconRegistry.registerIcon(icon0.replace(".png", ""));
						return this.icons[data];
					}
				}

				return super.getIconFromDamage(data);
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

		Iterator<Entry<Short, ElementArrow>> it = Arrows.iterator();

		while (it.hasNext()) {
			Entry<Short, ElementArrow> entry = it.next();

			String icon0 = entry.getValue().getIcon();
			Short index = entry.getKey();

			if (icon0 != null) {
				this.icons[index] = this.iconRegistry.registerIcon(icon0.replace(".png", ""));
			}
		}
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List items) {
		super.getSubItems(item, tab, items);

		Collection<Short> data = Arrows.keys();
		Iterator<Short> it = data.iterator();

		while (it.hasNext()) {
			items.add(new ItemStack(item, 1, it.next()));
		}
	}

}