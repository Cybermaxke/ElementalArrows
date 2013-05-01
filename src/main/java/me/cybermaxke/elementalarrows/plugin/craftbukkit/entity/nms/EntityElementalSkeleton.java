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
package me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_5_R2.Enchantment;
import net.minecraft.server.v1_5_R2.EnchantmentManager;
import net.minecraft.server.v1_5_R2.EntityHuman;
import net.minecraft.server.v1_5_R2.EntityLiving;
import net.minecraft.server.v1_5_R2.EntitySkeleton;
import net.minecraft.server.v1_5_R2.Item;
import net.minecraft.server.v1_5_R2.ItemStack;
import net.minecraft.server.v1_5_R2.NBTTagCompound;
import net.minecraft.server.v1_5_R2.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_5_R2.PathfinderGoalFleeSun;
import net.minecraft.server.v1_5_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_5_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_5_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_5_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_5_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_5_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_5_R2.PathfinderGoalRestrictSun;
import net.minecraft.server.v1_5_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_5_R2.World;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R2.event.CraftEventFactory;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.player.EntitySkinType;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.CraftElementalSkeleton;
import me.cybermaxke.elementalarrows.plugin.material.arrow.ArrowManager;

public class EntityElementalSkeleton extends EntitySkeleton {
	public ArrowMaterial arrow;
	public boolean firstTick;

	public EntityElementalSkeleton(World world) {
		super(world);
		try {
			Field f = PathfinderGoalSelector.class.getDeclaredField("a");
			f.setAccessible(true);

			((List<?>) f.get(this.goalSelector)).clear();
			((List<?>) f.get(this.targetSelector)).clear();
		} catch (Exception e) {}

		this.bI = 0.31F;
		this.goalSelector.a(1, new PathfinderGoalFloat(this));
		this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
		this.goalSelector.a(3, new PathfinderGoalFleeSun(this, this.bI));
		this.goalSelector.a(4, new PathfinderGoalArrowAttack(this, 0.25F, 20, 60, 15.0F));
		this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, this.bI));
		this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 16.0F, 0, true));
	}

	@Override
	public void l_() {
		super.l_();

		if (!this.firstTick) {
			this.firstTick = true;
			if (this.arrow != null && this.arrow.getSkeletonSkin() != null) {
				for (SpoutPlayer p : SpoutManager.getOnlinePlayers()) {
					p.setEntitySkin(this.getBukkitEntity(), this.arrow.getSkeletonSkin(), EntitySkinType.DEFAULT);
				}
			}
		}

		if (this.arrow == null) {
			this.arrow = ArrowManager.getRandomArrow();
		}

		if (this.getEquipment(0) == null || EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.getEquipment(0)) < 1) {
			ItemStack is = new ItemStack(Item.BOW);
			is.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			this.setEquipment(0, is);
		}
	}

	@Override
	public CraftElementalSkeleton getBukkitEntity() {
		if (this.bukkitEntity == null || !(this.bukkitEntity instanceof CraftElementalSkeleton)) {
			this.bukkitEntity = new CraftElementalSkeleton(this);
		}
		return (CraftElementalSkeleton) this.bukkitEntity;
	}

	@Override
	public void b(NBTTagCompound tag) {
		super.b(tag);
		if (this.arrow != null && this.arrow instanceof CustomItem) {
			tag.setInt("ElementalArrowId", ((CustomItem) this.arrow).getCustomId());
		}
	}

	@Override
	public void a(NBTTagCompound tag) {
		super.a(tag);
		if (tag.hasKey("ElementalArrowId")) {
			CustomItem item = MaterialData.getCustomItem(tag.getInt("ElementalArrowId"));
			this.arrow = (ArrowMaterial) (item == null ? null : item instanceof ArrowMaterial ? item : null);
		}
	}

	@Override
	public void a(EntityLiving entityliving, float f) {
		if (this.arrow == null) {
			super.a(entityliving, f);
			return;
		}

		EntityElementalArrow a = new EntityElementalArrow(this.world, this, entityliving, 1.6F * (float) this.arrow.getSpeedMutiplier(), 14 - this.world.difficulty * 4);
		ElementalArrow arrow = a.getBukkitEntity();
		arrow.setMaterial(this.arrow);
		arrow.setPickupable(false);
		ItemStack itemstack = this.bG();

		int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack);
		int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack);
		int n = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack);

		arrow.setDamage(f * 2.0F + this.random.nextGaussian() * 0.25D * this.arrow.getDamageMultiplier() + this.world.difficulty * 0.11F);
		if (k > 0) {
			arrow.setDamage(arrow.getDamage() + k * 0.5D + 0.5D);
		}

		double d = this.arrow.getKnockbackStrengthMultiplier();
		if (l > 0) {
			arrow.setKnockbackStrength(Math.round((float) (l * (d == 0.0D ? 1.0D : d))));
		}

		if (n > 0) {
			arrow.setFireTicks(100 + this.arrow.getFireTicks());
		}

		this.makeSound("random.bow", 1.0F, 1.0F / (this.aE().nextFloat() * 0.4F + 0.8F));
		this.world.addEntity(a);
	}

	@Override
	public void dropDeathLoot(boolean flag, int i) {
		List<org.bukkit.inventory.ItemStack> loot = new ArrayList<org.bukkit.inventory.ItemStack>();

		int count = this.random.nextInt(3 + i);
		if (count > 0) {
			if (this.arrow != null && this.arrow instanceof CustomItem) {
				loot.add(new SpoutItemStack((CustomItem) this.arrow, count));
			} else {
				loot.add(new org.bukkit.inventory.ItemStack(Material.ARROW, count));
			}
		}

		int count2 = this.random.nextInt(3 + i);
		if (count2 > 0) {
			loot.add(new org.bukkit.inventory.ItemStack(Material.BONE, count2));
		}

		CraftEventFactory.callEntityDeathEvent(this, loot);
	}

	@Override
	public void m() {

	}
}