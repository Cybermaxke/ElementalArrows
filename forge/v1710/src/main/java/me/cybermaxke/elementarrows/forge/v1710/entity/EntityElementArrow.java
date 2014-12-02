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
package me.cybermaxke.elementarrows.forge.v1710.entity;

import java.lang.reflect.Field;

import me.cybermaxke.elementarrows.common.arrow.Arrows;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitGround;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityTick;
import me.cybermaxke.elementarrows.common.source.Source;
import me.cybermaxke.elementarrows.common.source.SourceUnknown;
import me.cybermaxke.elementarrows.forge.v1710.util.Fields;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityElementArrow extends EntityArrow {

	/**
	 * The cached in ground field.
	 */
	private static Field inGroundField;

	/**
	 * Field to track when the state changes.
	 */
	private boolean lastInGround;

	/**
	 * The source.
	 */
	public Source source = new SourceUnknown();

	/**
	 * Whether the arrow in the ground is.
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
		super.writeEntityToNBT(nbt);

		/**
		 * Save the elemental arrow type.
		 */
		nbt.setShort("elementarrow", this.getElementData());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		/**
		 * Try to load the elemental arrow type.
		 */
		if (nbt.hasKey("elementarrow")) {
			this.setElementData(nbt.getShort("elementarrow"));
		}

		/**
		 * Fix the in ground field and I forgot to save/load default data.
		 */
		if (nbt.hasKey("inGround")) {
			super.readEntityFromNBT(nbt);

			this.inGround = nbt.getBoolean("inGround");
			this.lastInGround = this.inGround;
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
		super.onUpdate();

		/**
		 * Update the in ground field state.
		 */
		boolean flag = this.inGround;

		try {
			if (inGroundField == null) {
				inGroundField = Fields.findField(EntityArrow.class, boolean.class, 0, false);
			}
			
			inGroundField.setAccessible(true);
			flag = inGroundField.getBoolean(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.lastInGround = this.inGround;
		this.inGround = flag;

		ElementArrow arrow = Arrows.find(this.getElementData());
		FEntityArrow wrapper = FEntity.of(this);

		/**
		 * Check hit ground stuff.
		 */
		if (this.inGround && this.inGround != this.lastInGround && arrow != null) {
			EventEntityHitGround event = new EventEntityHitGround(wrapper, this.source);
			arrow.handle(event);
		}

		if (arrow != null) {
			arrow.handle(new EventEntityTick(wrapper));
		}
		if (this.worldObj.isRemote) {
			//System.out.print("\n1111111111111DDDDDDDDDDDDDDEEEEEEEEEEEBBBBBBug");
		} else {
			//System.out.print("\n222222222222DDDDDDDDDDDDDDEEEEEEEEEEEBBBBBBug");
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