package me.cybermaxke.elementarrows.forge.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrowRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemArrowTab extends CreativeTabs {
	private final ElementArrowRegistry registry;
	private final ItemArrow item;

	public ItemArrowTab(String label, ItemArrow item, ElementArrowRegistry registry) {
		super(label);

		this.registry = registry;
		this.item = item;
		this.func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.bow });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() {
		Short[] data = this.registry.dataValuesArray();

		if (data.length == 0) {
			return new ItemStack(this.item, 1, 0);
		} else {
			return new ItemStack(this.item, 1, data[0]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return this.item;
	}

}