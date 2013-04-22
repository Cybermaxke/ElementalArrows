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
package me.cybermaxke.elementalarrows.plugin.craftbukkit.dispenser.nms;

import net.minecraft.server.v1_5_R2.BlockDispenser;
import net.minecraft.server.v1_5_R2.DispenseBehaviorItem;
import net.minecraft.server.v1_5_R2.EnumFacing;
import net.minecraft.server.v1_5_R2.IDispenseBehavior;
import net.minecraft.server.v1_5_R2.IPosition;
import net.minecraft.server.v1_5_R2.ISourceBlock;
import net.minecraft.server.v1_5_R2.ItemStack;
import net.minecraft.server.v1_5_R2.World;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_5_R2.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

import org.getspout.spoutapi.inventory.SpoutItemStack;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms.EntityElementalArrow;

public class DispenseBehaviorElementalArrow extends DispenseBehaviorItem {

	@Override
	public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
		World world = isourceblock.k();
		IPosition iposition = BlockDispenser.a(isourceblock);
		EnumFacing enumfacing = BlockDispenser.j_(isourceblock.h());

		SpoutItemStack is = new SpoutItemStack(CraftItemStack.asCraftMirror(itemstack));
		if (!is.isCustomItem() || !(is.getMaterial() instanceof ArrowMaterial)) {
			return super.b(isourceblock, itemstack);
		}

		ArrowMaterial m = (ArrowMaterial) is.getMaterial();
		if (m.isBlackListWorld(world.getWorld())) {
			return super.b(isourceblock, itemstack);
		}

		ItemStack itemstack1 = itemstack.a(1);
		Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
		CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);

		BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(enumfacing.c(), enumfacing.d() + 0.1F, enumfacing.e()));
		if (!BlockDispenser.eventFired) {
			world.getServer().getPluginManager().callEvent(event);
		}

		if (event.isCancelled()) {
			itemstack.count += 1;
			return itemstack;
		}

		if (!event.getItem().equals(craftItem)) {
			itemstack.count += 1;

			ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
			IDispenseBehavior idispensebehavior = (IDispenseBehavior) BlockDispenser.a.a(eventStack.getItem());
			if ((idispensebehavior != IDispenseBehavior.a) && (idispensebehavior != this)) {
				idispensebehavior.a(isourceblock, eventStack);
				return itemstack;
			}
		}

		EntityElementalArrow a = new EntityElementalArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());
		a.shoot(event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), this.b() * (float) m.getSpeedMutiplier(), this.a());

		ElementalArrow ea = a.getBukkitEntity();
		ea.setMaterial(m);
		ea.setFireTicks(m.getFireTicks());
		ea.setDamage(ea.getDamage() * m.getDamageMultiplier());
		ea.setPickupable(true);
		if (m.getKnockbackStrengthMultiplier() != 0.0D) {
			ea.setKnockbackStrength(Math.round((float) (1.0D * m.getKnockbackStrengthMultiplier())));
		}

		world.addEntity(a);
		m.onShoot(null, ea, null);
		return itemstack;
	}

	@Override
	protected void a(ISourceBlock isourceblock) {
		isourceblock.k().triggerEffect(1002, isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ(), 0);
	}

	protected float a() {
		return 6.0F;
	}

	protected float b() {
		return 1.1F;
	}
}