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
package me.cybermaxke.elementarrows.forge.arrows;

import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.item.ItemArrow;
import me.cybermaxke.elementarrows.forge.item.ItemBow;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ElementArrow {

	/**
	 * The icon of the arrow item.
	 */
	@SideOnly(Side.CLIENT)
	public String icon;

	/**
	 * The texture of the arrow entity.
	 */
	@SideOnly(Side.CLIENT)
	public String texture;

	/**
	 * The name of the arrow.
	 */
	public String unlocalizedName;

	/**
	 * Whether the item the enchantment effect has.
	 */
	public boolean effect;

	/**
	 * Called when the arrow gets initialized.
	 * 
	 * @param event the event
	 */
	public void onInit(ArrowInitEvent event) {

	}

	/**
	 * Called when the arrow gets initialized on the client.
	 * 
	 * @param event the event
	 */
	@SideOnly(Side.CLIENT)
	public void onClientInit(ArrowInitEvent event) {

	}

	/**
	 * Called before the entity is spawned.
	 * 
	 * @param event the event
	 */
	public void onArrowBuild(ArrowBuildEvent event) {
		Source<?> source = event.source;

		if (source instanceof SourcePlayer) {
			event.arrow = new EntityElementArrow(event.world, (EntityPlayer) source.source, event.power * 2f);
		} else {
			IBlockSource source0 = (IBlockSource) source.source;

			IPosition pos = BlockDispenser.func_149939_a(source0);
			EnumFacing face = BlockDispenser.func_149937_b(source0.getBlockMetadata());

			double x = pos.getX();
			double y = pos.getY();
			double z = pos.getZ();

			double tx = face.getFrontOffsetX();
			double ty = face.getFrontOffsetY();
			double tz = face.getFrontOffsetZ();

			event.arrow = new EntityElementArrow(event.world, x, y, z);
			event.arrow.setThrowableHeading(tx, ty + 0.1f, tz, event.power * 2f * 1.5f, 6f);
		}
	}

	/**
	 * Called right before the entity is spawned but the entity is ready.
	 * 
	 * @param event the event
	 */
	public void onArrowShot(ArrowShotEvent event) {

	}

	/**
	 * Called when the arrow the ground hits.
	 * 
	 * @param event the event
	 */
	public void onArrowHitGround(ArrowHitGroundEvent event) {

	}

	/**
	 * Called when the arrow a entity hits (damages).
	 * 
	 * @param event the event
	 */
	public void onArrowHitEntity(ArrowHitEntityEvent event) {

	}

	/**
	 * Called when the arrow ticks.
	 * 
	 * @param event the event
	 */
	public void onArrowTick(ArrowTickEvent event) {

	}

	/**
	 * Called when the arrow ticks on the client.
	 * 
	 * @param event the event
	 */
	@SideOnly(Side.CLIENT)
	public void onArrowClientTick(ArrowTickEvent event) {

	}

	public static class ArrowInitEvent {
		public final ItemArrow itemArrow;
		public final ItemBow itemBow;
		public final short data;

		public ArrowInitEvent(ItemArrow itemArrow, ItemBow itemBow, int data) {
			this.itemArrow = itemArrow;
			this.itemBow = itemBow;
			this.data = (short) data;
		}

	}

	public static class ArrowTickEvent {
		public final EntityElementArrow arrow;

		public ArrowTickEvent(EntityElementArrow arrow) {
			this.arrow = arrow;
		}

	}

	public static class ArrowHitEntityEvent {
		public final EntityElementArrow arrow;
		public final EntityLivingBase entity;
		public final DamageSource damage;
		public final Source<?> source;

		public ArrowHitEntityEvent(EntityElementArrow arrow, Source<?> source, EntityLivingBase entity, DamageSource damage) {
			this.source = source;
			this.damage = damage;
			this.entity = entity;
			this.arrow = arrow;
		}

	}

	public static class ArrowHitGroundEvent {
		public final EntityElementArrow arrow;
		public final Source<?> source;

		public ArrowHitGroundEvent(Source<?> source, EntityElementArrow arrow) {
			this.source = source;
			this.arrow = arrow;
		}

	}

	public static class ArrowShotEvent {
		public final Source<?> source;

		/**
		 * The created arrow.
		 */
		public final EntityElementArrow arrow;

		/**
		 * The bow item that was used to shoot the arrow.
		 */
		public final ItemStack bow;

		/**
		 * How hard the bow was charged.
		 */
		public int charge;

		/**
		 * The power that was used to shoot the arrow.
		 */
		public float power;

		public ArrowShotEvent(EntityElementArrow arrow, Source<?> source, ItemStack bow, float power, int charge) {
			this.source = source;
			this.charge = charge;
			this.arrow = arrow;
			this.power = power;
			this.bow = bow;
		}

	}

	public static class ArrowBuildEvent {
		public final Source<?> source;

		/**
		 * The world.
		 */
		public final World world;

		/**
		 * The bow item that was used to shoot the arrow.
		 */
		public final ItemStack bow;

		/**
		 * How hard the bow was charged.
		 */
		public int charge;

		/**
		 * The power that was used to shoot the arrow.
		 */
		public float power;

		/**
		 * The created arrow.
		 */
		public EntityElementArrow arrow;

		public ArrowBuildEvent(Source<?> source, World world, ItemStack bow, float power, int charge) {
			this.source = source;
			this.charge = charge;
			this.power = power;
			this.world = world;
			this.bow = bow;
		}

	}

	/**
	 * A source what causes the arrow events to be called.
	 */
	public static class Source<T> {

		/**
		 * The source.
		 */
		public final T source;

		public Source(T source) {
			this.source = source;
		}

	}

	public static class SourcePlayer extends Source<EntityPlayer> {

		public SourcePlayer(EntityPlayer source) {
			super(source);
		}

	}

	public static class SourceDispenser extends Source<IBlockSource> {

		public SourceDispenser(IBlockSource source) {
			super(source);
		}

	}

}