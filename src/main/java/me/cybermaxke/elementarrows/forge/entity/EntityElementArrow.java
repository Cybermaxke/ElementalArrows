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
package me.cybermaxke.elementarrows.forge.entity;

import java.lang.reflect.Field;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowHitGroundEvent;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowTickEvent;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.Source;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityElementArrow extends EntityArrow {
	private boolean lastInGround;

	/**
	 * The last motion values.
	 */
	public double lastMotX;
	public double lastMotY;
	public double lastMotZ;

	/**
	 * The source.
	 */
	public Source<?> source;

	/**
	 * Whether the arrow is on the ground.
	 */
	public boolean inGround;

	public EntityElementArrow(World world) {
		super(world);
	}

	public EntityElementArrow(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityElementArrow(World world, EntityLivingBase shooter, float power) {
		super(world, shooter, power);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		/**
		 * Add the elemental arrow data.
		 */
		this.dataWatcher.addObject(20, new Short((short) 0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("elementarrow", this.getElementData());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("elementarrow")) {
			this.setElementData(nbt.getShort("elementarrow"));
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (this.worldObj.isRemote || !this.inGround || this.arrowShake > 0) {
			return;
		}

		int i = this.canBePickedUp == 1 || (this.canBePickedUp == 2 && (player.capabilities.isCreativeMode)) ? 1 : 0;
		if (this.canBePickedUp == 1 && !player.inventory.addItemStackToInventory(new ItemStack(Items.arrow, 1, this.getElementData()))) {
			i = 0;
		}

		if (i != 0) {
			this.playSound("random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1f) * 2f);
			player.onItemPickup(this, 1);
			this.setDead();
		}
	}

	@Override
	public void onUpdate() {
		this.lastMotX = this.motionX;
		this.lastMotY = this.motionY;
		this.lastMotZ = this.motionZ;

		super.onUpdate();

		/**
		 * Update the in ground field state.
		 */
		try {
			Field field = EntityArrow.class.getDeclaredField("inGround");
			field.setAccessible(true);

			this.lastInGround = this.inGround;
			this.inGround = field.getBoolean(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ElementArrow arrow = EArrowMod.mod.registry.fromData(this.getElementData());

		/**
		 * Check hit ground stuff.
		 */
		if (this.inGround && this.inGround != this.lastInGround && arrow != null) {
			ArrowHitGroundEvent event0 = new ArrowHitGroundEvent(this.source, this);
			arrow.onArrowHitGround(event0);
		}

		if (arrow != null) {
			arrow.onArrowTick(new ArrowTickEvent(this));

			if (this.worldObj.isRemote) {
				arrow.onArrowClientTick(new ArrowTickEvent(this));
			}
		}
	}

	/**
	 * Gets the data value of the elemental arrow.
	 * 
	 * @return the data value
	 */
	public short getElementData() {
		return this.dataWatcher.getWatchableObjectShort(20);
	}

	/**
	 * Sets the data value of the elemental arrow.
	 * 
	 * @param data the data value
	 */
	public void setElementData(int data) {
		this.dataWatcher.updateObject(20, new Short((short) (data & 0xffff)));
	}

}