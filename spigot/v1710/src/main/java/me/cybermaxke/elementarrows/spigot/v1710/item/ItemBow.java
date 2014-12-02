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
package me.cybermaxke.elementarrows.spigot.v1710.item;

import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EnchantmentManager;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.PlayerInventory;
import net.minecraft.server.v1_7_R4.World;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.source.SourceEntity;
import me.cybermaxke.elementarrows.spigot.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.spigot.v1710.entity.FEntity;
import me.cybermaxke.elementarrows.spigot.v1710.entity.FEntityArrow;

public class ItemBow extends net.minecraft.server.v1_7_R4.ItemBow {

	public ItemBow() {
		this.c("bow");
		this.f("bow");
	}

	@Override
	public void a(ItemStack bow, World world, EntityHuman player, int charge) {
		charge = this.d_(bow) - charge;

		/**
		 * Find the first arrow, the first arrow will give also effect in creative and with infinite arrows.
		 */
		ItemStack arrow = this.findFirstArrow(player.inventory);

		/**
		 * Flag to make the arrows not retrievable after hitting the ground.
		 */
		boolean flag = player.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, bow) > 0;

		if (flag || arrow != null) {
			ElementArrow arrow0 = null;

			if (arrow != null) {
				arrow0 = Arrows.find(arrow.getData());
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
					entity0.setElementData(arrow.getData());
					entity0.source = source;
				}
			} else {
				entity0 = new EntityElementArrow(world, player, power * 2f);
				entity0.source = source;
			}

			if (power == 1f) {
				entity0.setCritical(true);
			}

			int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, bow);
			if (k > 0) {
				entity0.b(entity0.e() + k * 0.5d + 0.5d);
			}

			int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, bow);
			if (l > 0) {
				entity0.setKnockbackStrength(l);
			}

			if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, bow) > 0) {
				entity0.setOnFire(100);
			}

			bow.damage(1, player);
			world.makeSound(player, "random.bow", 1f, 1f / (Item.g.nextFloat() * 0.4f + 1.2f) + power * 0.5f);

			if (flag) {
				entity0.fromPlayer = 2;
			} else {
				entity0.fromPlayer = 1;
				arrow.count--;
			}

			if (arrow0 != null) {
				EventEntityShot event0 = new EventEntityShot(entity1, entity0.source, charge / 72000f, power);
				arrow0.handle(event0);
			}

			if (!world.isStatic) {
				world.addEntity(entity0);
			}
		}
	}

	@Override
	public ItemStack a(ItemStack bow, World world, EntityHuman player) {
		if (player.abilities.canInstantlyBuild || this.findFirstArrow(player.inventory) != null) {
			player.a(bow, this.d_(bow));
		}

		return bow;
	}

	/**
	 * Finds the first arrow stack in the inventory.
	 * 
	 * @param inventory the inventory
	 * @return the arrow stack
	 */
	public ItemStack findFirstArrow(PlayerInventory inventory) {
		ItemStack[] main = inventory.items;

		for (int i = 0; i < main.length; i++) {
			if (main[i] != null && main[i].getItem() == Items.ARROW) {
				return main[i];
			}
		}

		return null;
	}

}