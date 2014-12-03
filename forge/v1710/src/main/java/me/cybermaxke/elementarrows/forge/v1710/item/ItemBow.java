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
package me.cybermaxke.elementarrows.forge.v1710.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.source.SourceEntity;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntity;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityArrow;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemBow extends net.minecraft.item.ItemBow {
	private IIcon[] icons;

	public ItemBow() {
		this.setUnlocalizedName("bow");
		this.setTextureName("bow");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack bow, World world, EntityPlayer player, int charge) {
		charge = this.getMaxItemUseDuration(bow) - charge;

		ArrowLooseEvent event = new ArrowLooseEvent(player, bow, charge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		charge = event.charge;

		/**
		 * Find the first arrow, the first arrow will give also effect in creative and with infinite arrows.
		 */
		ItemStack arrow = this.findFirstArrow(player.inventory);

		/**
		 * Flag to make the arrows not retrievable after hitting the ground.
		 */
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, bow) > 0;

		if (flag || arrow != null) {
			ElementArrow arrow0 = null;

			if (arrow != null) {
				arrow0 = Arrows.find(arrow.getItemDamage());
			}

			float power = charge / 20f;
			power = (power * power + power * 2f) / 3f;

			if (power < 0f) {
				return;
			}

			if (power > 1f) {
				power = 1f;
			}

			SourceEntity source = new SourceEntity(FEntity.of(player));
			EntityElementArrow entity0 = null;
			FEntityArrow entity1 = null;

			if (arrow0 != null) {
				EventEntityBuild event0 = new EventEntityBuild(source, charge / 72000f, power);
				arrow0.handle(event0);

				entity1 = (FEntityArrow) event0.getEntity();

				if (entity1 == null) {
					return;
				} else {
					entity0 = (EntityElementArrow) entity1.entity;
					entity0.setElementData(arrow.getItemDamage());
					entity0.source = source;
				}
			} else {
				entity0 = new EntityElementArrow(world, player, power * 2f);
				entity0.source = source;
			}

			if (power == 1f) {
				entity0.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, bow);
			if (k > 0) {
				entity0.setDamage(entity0.getDamage() + k * 0.5d + 0.5d);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, bow);
			if (l > 0) {
				entity0.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, bow) > 0) {
				entity0.setFire(100);
			}

			bow.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1f, 1f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + power * 0.5f);

			if (flag) {
				entity0.canBePickedUp = 2;
			} else {
				entity0.canBePickedUp = 1;
				arrow.stackSize--;
			}

			if (arrow0 != null) {
				EventEntityShot event0 = new EventEntityShot(entity1, entity0.source, charge / 72000f, power);
				arrow0.handle(event0);
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(entity0);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack bow, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, bow);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.isCanceled()) {
			return event.result;
		}
	
		if (player.capabilities.isCreativeMode || this.findFirstArrow(player.inventory) != null) {
			player.setItemInUse(bow, this.getMaxItemUseDuration(bow));
		}

		return bow;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		this.itemIcon = registry.registerIcon("bow_standby");

		this.icons = new IIcon[3];
		this.icons[0] = registry.registerIcon("bow_pulling_0");
		this.icons[1] = registry.registerIcon("bow_pulling_1");
		this.icons[2] = registry.registerIcon("bow_pulling_2");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int duration) {
		return this.icons[duration];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (usingItem == null) {
			return this.itemIcon;
		}

		int ticksInUse = stack.getMaxItemUseDuration() - useRemaining;

		if (ticksInUse > 18) {
			return this.icons[2];
		} else if (ticksInUse > 14) {
			return this.icons[1];
		} else if (ticksInUse > 0) {
			return this.icons[0];
		} else {
			return this.itemIcon;
		}
	}

	/**
	 * Finds the first arrow stack in the inventory.
	 * 
	 * @param inventory the inventory
	 * @return the arrow stack
	 */
	public ItemStack findFirstArrow(InventoryPlayer inventory) {
		ItemStack[] main = inventory.mainInventory;

		for (int i = 0; i < main.length; i++) {
			if (main[i] != null && main[i].getItem() == Items.arrow) {
				return main[i];
			}
		}

		return null;
	}

}