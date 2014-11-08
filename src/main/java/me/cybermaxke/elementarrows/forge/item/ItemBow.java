package me.cybermaxke.elementarrows.forge.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import me.cybermaxke.elementarrows.forge.arrows.ElementArrow;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.SourcePlayer;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrowRegistry;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowBuildEvent;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowShotEvent;
import me.cybermaxke.elementarrows.forge.entity.EntityElementArrow;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public final class ItemBow extends net.minecraft.item.ItemBow {
	private ElementArrowRegistry registry;
	private ItemArrow arrow;
	private IIcon[] icons;

	public ItemBow(ElementArrowRegistry registry, ItemArrow arrow) {
		this.registry = registry;
		this.arrow = arrow;
		this.setUnlocalizedName("bow");
		this.setTextureName("bow");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack bow, World world, EntityPlayer player, int charge) {
		charge = this.getMaxItemUseDuration(bow) - charge;

		ArrowLooseEvent event = new ArrowLooseEvent(player, bow, charge);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		charge = event.charge;

		/**
		 * Find the first arrow, the first arrow will give also effect in creative and with infinite arrows.
		 */
		ItemStack arrow = this.findFirstArrow(player.inventory);

		/**
		 * Flag to make the arrows not retrievable after hitting the ground.
		 */
		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, bow) > 0;

		if (flag || arrow != null) {
			ElementArrow arrow0 = null;

			if (arrow != null) {
				arrow0 = this.registry.fromData(arrow.getItemDamage());
			}

			float power = charge / 20f;
			power = (power * power + power * 2f) / 3f;
		   
			if (power < 0f) {
				return;
			}

			if (power > 1f) {
				power = 1f;
			}

			EntityElementArrow entity;

			if (arrow0 != null) {
				SourcePlayer source = new SourcePlayer(player);

				ArrowBuildEvent event0 = new ArrowBuildEvent(source, world, bow, power, charge);
				arrow0.onArrowBuild(event0);
				entity = event0.arrow;

				if (entity == null) {
					return;
				} else {
					entity.setElementData(arrow.getItemDamage());
					entity.source = source;
				}
			} else {
				entity = new EntityElementArrow(world, player, power * 2f);
			}

			if (power == 1f) {
				entity.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, bow);
			if (k > 0) {
				entity.setDamage(entity.getDamage() + k * 0.5d + 0.5d);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, bow);
			if (l > 0) {
				entity.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, bow) > 0) {
				entity.setFire(100);
			}

			bow.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1f, 1f / (itemRand.nextFloat() * 0.4f + 1.2f) + power * 0.5f);

			if (flag) {
				entity.canBePickedUp = 2;
			} else {
				arrow.stackSize--;
			}

			if (arrow0 != null) {
				ArrowShotEvent event0 = new ArrowShotEvent(entity, entity.source, arrow, power, charge);
				arrow0.onArrowShot(event0);
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(entity);
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack bow, World world, EntityPlayer player) {
		ArrowNockEvent event = new ArrowNockEvent(player, bow);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.isCanceled()) {
			return event.result;
		}
	
		if (player.capabilities.isCreativeMode || this.findFirstArrow(player.inventory) != null) {
			player.setItemInUse(bow, this.getMaxItemUseDuration(bow));
		}

		return bow;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		this.itemIcon = registry.registerIcon("bow_standby");

		this.icons = new IIcon[3];
		this.icons[0] = registry.registerIcon("bow_pulling_0");
		this.icons[1] = registry.registerIcon("bow_pulling_1");
		this.icons[2] = registry.registerIcon("bow_pulling_2");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int duration) {
		return this.icons[duration];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (usingItem == null) {
			return this.itemIcon;
		}

		int ticksInUse = stack.getMaxItemUseDuration() - useRemaining;

		if (ticksInUse > 18) {
			return this.icons[2];
		} else if (ticksInUse > 14) {
			return this.icons[1];
		} else if (ticksInUse > 0) {
			return this.icons[0];
		} else {
			return this.itemIcon;
		}
	}

	/**
	 * Finds the first arrow stack in the inventory.
	 * 
	 * @param inventory the inventory
	 * @return the arrow stack
	 */
	public ItemStack findFirstArrow(InventoryPlayer inventory) {
		ItemStack[] main = inventory.mainInventory;

		for (int i = 0; i < main.length; i++) {
			if (main[i] != null && main[i].getItem() == this.arrow) {
				return main[i];
			}
		}

		return null;
	}

}