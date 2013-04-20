/**
 * 
 * This software is part of the ElementalArrows
 * 
 * This plugins adds custom arrows to the game like they from the
 * ElemantalArrows mod but ported to spoutplugin and bukkit.
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
package me.cybermaxke.ElementalArrows;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;

import net.minecraft.server.Enchantment;
import net.minecraft.server.EnchantmentManager;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.EnumAnimation;
import net.minecraft.server.Item;
import net.minecraft.server.ItemBow;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Packet103SetSlot;
import net.minecraft.server.World;

import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.player.SpoutPlayer;

public class CustomItemBow extends ItemBow {

	public CustomItemBow() {
		super(MaterialData.bow.getRawId());
		this.b("bow");
	}

	private int getFirstArrow(SpoutPlayer p) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			if (p.getInventory().getItem(i) != null) {
				SpoutItemStack is = new SpoutItemStack(p.getInventory().getItem(i));

				if (is.isCustomItem() && is.getMaterial() instanceof CustomArrowItem) {
					CustomArrowItem ai = (CustomArrowItem) is.getMaterial();

					if (!ai.isBlackListWorld(p.getWorld()) && ai.hasUsePermission(p)) {
						return i;
					}
				}
			}
		}

		return -1;	
	}

	@Override
	public void a(ItemStack itemstack, World world, EntityHuman entityhuman, int i) {
		boolean flag = entityhuman.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0;
		SpoutPlayer p = SpoutManager.getPlayer((Player) entityhuman.getBukkitEntity());

		int slot = this.getFirstArrow(p);
		if (slot == -1) {
			super.a(itemstack, world, entityhuman, i);
			return;
		}

		int j = this.c_(itemstack) - i;
		float f = (float) j / 20.0F;

		f = (f * f + f * 2.0F) / 3.0F;
		if ((double) f < 0.1D) {
			return;
		}

		if (f > 1.0F) {
			f = 1.0F;
		}

		SpoutItemStack is = new SpoutItemStack(p.getInventory().getItem(slot));
		CustomArrowItem ai = (CustomArrowItem) is.getMaterial();

		for (int it = 0; it < ai.getMultiplePerShot(); it++) {
			ArrowEntity a = new ArrowEntity(world, entityhuman, f * 2.0F);

			if (f == 1.0F) {
				a.a(true);
			}

			a.setArrow(ai);
			a.setFireTicks(ai.getFireTicks());
			a.setCanPickup(ai.canPickup());

			int damage = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack) + a.getDamage();
			a.setDamage(damage);

			int knockback = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack) + a.getKnockback();
			a.setKnockback(knockback);

			if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack) > 0) {
				a.setFireTicks(100 + a.getFireTicks());
			}

			EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(entityhuman, itemstack, a, f);
			if (event.isCancelled()) {
				event.getProjectile().remove();
				return;
			}

			if (flag) {
				a.setCanPickup(false);
			}

			if (event.getProjectile() == a.getBukkitEntity()) {
				world.addEntity(a);      
			}

			ai.onShoot(p, a);
		}

		if (!flag) {
			if (is.getAmount() <= 1) {
				is = null;
			} else {
				is.setAmount(is.getAmount() - 1);
			}
			itemstack.damage(1, entityhuman);
			p.getInventory().setItem(slot, is);
		}

		world.makeSound(entityhuman, "random.bow", 1.0F, 1.0F / (e.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
	}

	@Override
	public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman) {
		return itemstack;
	}

	@Override
	public int c_(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public EnumAnimation b_(ItemStack itemstack) {
		return EnumAnimation.BOW;
	}

	@Override
	public ItemStack a(ItemStack itemstack, World world, final EntityHuman entityhuman) {
		SpoutPlayer p = SpoutManager.getPlayer((Player) entityhuman.getBukkitEntity());

		int slot = this.getFirstArrow(p);
		if (slot == -1) {
			return super.a(itemstack, world, entityhuman);
		}

		Packet103SetSlot packet1 = new Packet103SetSlot(0, 9, new ItemStack(Item.ARROW));
		((EntityPlayer) entityhuman).playerConnection.sendPacket(packet1);
		entityhuman.a(itemstack, this.c_(itemstack));
		return itemstack;
	}

	@Override
	public int c() {
		return 1;
	}
}