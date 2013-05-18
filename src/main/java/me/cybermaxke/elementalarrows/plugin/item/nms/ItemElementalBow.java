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
package me.cybermaxke.elementalarrows.plugin.item.nms;

import me.cybermaxke.elementalarrows.api.ElementalArrows;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.api.inventory.ElementalItemStack;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.entity.nms.EntityElementalArrow;

import net.minecraft.server.v1_5_R3.Enchantment;
import net.minecraft.server.v1_5_R3.EnchantmentManager;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.EnumAnimation;
import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.ItemBow;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.Packet103SetSlot;
import net.minecraft.server.v1_5_R3.World;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ItemElementalBow extends ItemBow {

	public ItemElementalBow(int id) {
		super(id);
		this.b("bow");
	}

	private int getFirstArrowSlot(Player p) {
		for (int i = 0; i < p.getInventory().getSize(); i++) {
			org.bukkit.inventory.ItemStack is = p.getInventory().getItem(i);
			if (is != null) {
				ArrowMaterial m = this.getMaterial(is);
				if (m != null && (m.getPermission() == null || p.hasPermission(m.getPermission()))) {
					return i;
				} else if (is.getType().equals(Material.ARROW)) {
					return -1;
				}
			}
		}
		return -1;
	}

	private org.bukkit.inventory.ItemStack getFirstArrow(Player p) {
		int s = this.getFirstArrowSlot(p);
		return s == -1 ? null : p.getInventory().getItem(s);
	}

	private ArrowMaterial getMaterial(org.bukkit.inventory.ItemStack item) {
		if (item == null) {
			return null;
		}

		ElementalItemStack i = new ElementalItemStack(item);
		return (ArrowMaterial) (i.isCustomItem() && i.getMaterial() instanceof ArrowMaterial ? i.getMaterial() : null);
	}

	@Override
	public void a(ItemStack itemstack, World world, EntityHuman entityhuman, int i) {
		boolean flag = entityhuman.abilities.canInstantlyBuild || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0;
		Player p = (Player) entityhuman.getBukkitEntity();

		int slot = this.getFirstArrowSlot(p);
		org.bukkit.inventory.ItemStack is1 = this.getFirstArrow(p);
		if (is1 == null) {
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

		ElementalItemStack is = new ElementalItemStack(is1);
		ArrowMaterial m = this.getMaterial(is);

		int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack);
		int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack);
		int n = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack);

		EntityElementalArrow a1 = new EntityElementalArrow(world, entityhuman, f * (float) m.getSpeedMutiplier() * 2.0F);
		ElementalArrow a = a1.getBukkitEntity();

		if (f == 1.0F) {
			a.setCritical(true);
		}

		a.setMaterial(m);
		a.setPickupable(flag ? false : m.isPickupable());

		if (k > 0) {
			a.setDamage((a.getDamage() * m.getDamageMultiplier()) + k * 0.5D + 0.5D);
		} else {
			a.setDamage(a.getDamage() * m.getDamageMultiplier());
		}

		double d = m.getKnockbackStrengthMultiplier();
		if (l > 0) {
			a.setKnockbackStrength(Math.round((float) (l * (d == 0.0D ? 1.0D : d))));
		} else if (m.getKnockbackStrengthMultiplier() != 0.0D) {
			a.setKnockbackStrength(Math.round((float) (1.0D * d)));
		}

		if (n > 0) {
			a.setFireTicks(100 + m.getFireTicks());
		} else {
			a.setFireTicks(m.getFireTicks());
		}

		EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(entityhuman, itemstack, a1, f);
		if (event.isCancelled()) {
			event.getProjectile().remove();
			return;
		}

		if (event.getProjectile() == a) {
			world.addEntity(a1);      
		}

		m.onShoot(p, a, is);

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
	public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
		ElementalPlayer p = ElementalArrows.getAPI().getPlayer((Player) entityhuman.getBukkitEntity());

		if (this.getFirstArrow(p) == null) {
			return super.a(itemstack, world, entityhuman);
		}

		if (p.isSpoutCraftEnabled()) {
			Packet103SetSlot packet = new Packet103SetSlot(0, 9, new ItemStack(Item.ARROW));
			((EntityPlayer) entityhuman).playerConnection.sendPacket(packet);
		}

		entityhuman.a(itemstack, this.c_(itemstack));
		return itemstack;
	}

	@Override
	public int c() {
		return 1;
	}
}