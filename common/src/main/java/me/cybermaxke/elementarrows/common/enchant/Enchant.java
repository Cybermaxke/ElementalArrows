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
package me.cybermaxke.elementarrows.common.enchant;

import com.google.common.base.Preconditions;

import me.cybermaxke.elementarrows.common.item.type.ItemArmor;
import me.cybermaxke.elementarrows.common.item.type.ItemArmor.Type;
import me.cybermaxke.elementarrows.common.item.type.ItemBow;
import me.cybermaxke.elementarrows.common.item.type.ItemDigging;
import me.cybermaxke.elementarrows.common.item.type.ItemFishingRod;
import me.cybermaxke.elementarrows.common.item.type.ItemType;
import me.cybermaxke.elementarrows.common.item.type.ItemWeapon;

public interface Enchant {

	public static final Enchant Protection = null;
	public static final Enchant FireProtection = null;
	public static final Enchant FeatherFalling = null;
	public static final Enchant BlastProtection = null;
	public static final Enchant ProjectileProtection = null;
	public static final Enchant Respiration = null;
	public static final Enchant AquaAffinity = null;
	public static final Enchant Thorns = null;
	public static final Enchant Sharpness = null;
	public static final Enchant Smite = null;
	public static final Enchant BaneOfArthropods = null;
	public static final Enchant Knockback = null;
	public static final Enchant FireAspect = null;
	public static final Enchant Looting = null;
	public static final Enchant Efficiency = null;
	public static final Enchant SilkTouch = null;
	public static final Enchant Unbreaking = null;
	public static final Enchant Fortune = null;
	public static final Enchant Power = null;
	public static final Enchant Punch = null;
	public static final Enchant Flame = null;
	public static final Enchant Infinity = null;
	public static final Enchant LuckOfTheSea = null;
	public static final Enchant Lure = null;

	/**
	 * Gets the id of the enchantment.
	 * 
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the internal id of the enchantment.
	 * 
	 * @return the internal id
	 */
	@Deprecated
	int getInternalId();

	/**
	 * Gets the maximum level of the enchantment.
	 * 
	 * @return the maximum level
	 */
	int getMaxLevel();

	/**
	 * Gets the minimum level of the enchantment.
	 * 
	 * @return the minimum level
	 */
	int getMinLevel();

	/**
	 * Gets the enchantment target of the enchantment.
	 * 
	 * @return the target
	 */
	Target getTarget();

	/**
	 * Gets whether this enchantment can be combined with the other enchantment.
	 * 
	 * @param enchant the other enchantment
	 * @return can be combined
	 */
	boolean worksWith(Enchant enchant);

	/**
	 * Gets whether the item type can be enchanted by this enchantment.
	 * 
	 * @param type the item type
	 * @return whether it can be enchanted
	 */
	boolean worksFor(ItemType type);

	/**
	 * The target of the enchantment.
	 */
	enum Target {
		/**
		 * Works for all the item types.
		 */
		All,
		/**
		 * Works for all the items with durability (= that are breakable).
		 */
		Breakable,
		/**
		 * Works for all the armor types.
		 */
		Armor,
		/**
		 * Works for all the helmet types.
		 */
		Helmet,
		/**
		 * Works for all the chestplate types.
		 */
		Chestplate,
		/**
		 * Works for all the leggings types.
		 */
		Leggings,
		/**
		 * Works for all the boots types.
		 */
		Boots,
		/**
		 * Works for all the weapon/sword types.
		 */
		Weapon,
		/**
		 * Works for all the tool types except hoes.
		 */
		Digger,
		/**
		 * Works for all the bow types.
		 */
		Bow,
		/**
		 * Works for all the fishing rod types.
		 */
		FishingRod;

		/**
		 * Gets whether this target type works for the target item type.
		 * 
		 * @param type the item type
		 * @return whether it works
		 */
		public boolean worksFor(ItemType type) {
			Preconditions.checkNotNull(type);

			if (this == All) {
				return true;
			}
			if (this == Breakable && type.hasDurability()) {
				return true;
			}
			if (type instanceof ItemArmor) {
				if (this == Armor) {
					return true;
				}

				Type type0 = ((ItemArmor) type).getType();

				if (this == Helmet && type0 == Type.Helmet) {
					return true;
				}
				if (this == Chestplate && type0 == Type.Chestplate) {
					return true;
				}
				if (this == Leggings && type0 == Type.Leggings) {
					return true;
				}
				if (this == Boots && type0 == Type.Boots) {
					return true;
				}
			}
			if (this == Weapon && type instanceof ItemWeapon) {
				return true;
			}
			if (this == Digger && type instanceof ItemDigging) {
				return true;
			}
			if (this == Bow && type instanceof ItemBow) {
				return true;
			}
			if (this == FishingRod && type instanceof ItemFishingRod) {
				return true;
			}

			return false;
		}

	}

}