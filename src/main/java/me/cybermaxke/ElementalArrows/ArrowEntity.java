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

import java.lang.reflect.Field;

import net.minecraft.server.EntityArrow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;

public class ArrowEntity extends EntityArrow {
	private CustomArrowItem arrow;
	private float power;
	private int damage = 0;
	private int knockback = 0;
	private int fireticks = 0;
	private boolean canPickup = true;

	public ArrowEntity(World world, EntityLiving entityliving, float f) {
		super(world, entityliving, f);
		this.power = f;
	}

	public ArrowEntity(World world) {
		super(world);
	}

	public void setCanPickup(boolean pickup) {
		this.canPickup = pickup;
	}

	public boolean canPickup() {
		return this.canPickup;
	}

	public float getPower() {
		return this.power;
	}

	public void setArrow(CustomArrowItem arrow) {
		this.arrow = arrow;
	}

	public CustomArrowItem getArrow() {
		return this.arrow;
	}

	public void setDamage(int amount) {
		if (amount > 0) {
			this.b(this.c() + (double) amount * 0.5D + 0.5D);
		}

		this.damage = amount;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setKnockback(int amount) {
		if (amount > 0) {
			this.a(amount);
		}

		this.knockback = amount;
	}

	public int getKnockback() {
		return this.knockback;
	}

	public void setFireTicks(int amount) {
		if (amount > 0) {
			this.setOnFire(amount);
		}

		this.fireticks = amount;
	}

	public int getFireTicks() {
		return this.fireticks;
	}

	public boolean inGround() {
		try {
			Field f = EntityArrow.class.getDeclaredField("inGround");
			f.setAccessible(true);
			return (Boolean) f.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void b(NBTTagCompound tag) {
		super.b(tag);
		if (this.arrow != null) {
			tag.setInt("CustomArrowId", this.arrow.getCustomId());
		}
	}

	@Override
	public void a(NBTTagCompound tag) {
		super.a(tag);
		if (tag.hasKey("CustomArrowId")) {
			CustomItem item = MaterialData.getCustomItem(tag.getInt("CustomArrowId"));
			this.arrow = (CustomArrowItem) (item instanceof CustomArrowItem ? item : null);
		}
	}

	@Override
	public void l_() {
		if (this.arrow != null) {
			Arrow a = (Arrow) this.getBukkitEntity();
			this.arrow.onTick((Player) a.getShooter(), this);
		}

		super.l_();
	}

	@Override
	public void b_(EntityHuman entityhuman) {
		if (!this.canPickup) {
			return;
		}

		if ((!this.world.isStatic) && (this.inGround()) && (this.shake <= 0)) {
	    	ItemStack ist = new ItemStack(Item.ARROW);
	    	org.bukkit.inventory.ItemStack is = null;

	    	if (this.arrow != null) {
	    		is = this.arrow.getArrowDrop();
	    		ist = CraftItemStack.asNMSCopy(is);
	    	}

	    	if ((this.fromPlayer == 1) && (this.shake <= 0) && (entityhuman.inventory.canHold(ist) > 0)) {
	    		EntityItem i = new EntityItem(this.world, this.locX, this.locY, this.locZ, ist);

	    	  	PlayerPickupItemEvent e = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new CraftItem(this.world.getServer(), this, i), 0);
	        	this.world.getServer().getPluginManager().callEvent(e);

	        	if (e.isCancelled()) {
	        		return;
	        	}
	    	}

	    	boolean flag = (this.fromPlayer == 1) || ((this.fromPlayer == 2) && (entityhuman.abilities.canInstantlyBuild));

	    	if ((this.fromPlayer == 1) && (!entityhuman.inventory.pickup(ist))) {
	      		flag = false;
	      	}

	      	if (flag) {
	      		this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
	      		entityhuman.receive(this, 1);
	      		this.die();
	      	}
	    }
	}
}