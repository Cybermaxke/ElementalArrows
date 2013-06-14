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
package me.cybermaxke.elementalarrows.plugin.entity.nms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.util.Vector;

import me.cybermaxke.elementalarrows.api.ElementalArrows;
import me.cybermaxke.elementalarrows.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.api.ParticleEffect;
import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.selector.TargetSelector;
import me.cybermaxke.elementalarrows.api.entity.selector.TargetSelectorMonster;
import me.cybermaxke.elementalarrows.api.inventory.ElementalItemStack;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.container.ContainerTurret;
import me.cybermaxke.elementalarrows.plugin.entity.CraftElementalTurret;
import me.cybermaxke.elementalarrows.plugin.entity.CraftElementalArrow;
import me.cybermaxke.elementalarrows.plugin.inventory.nms.InventoryTurret;

import net.minecraft.server.v1_5_R3.Container;
import net.minecraft.server.v1_5_R3.DamageSource;
import net.minecraft.server.v1_5_R3.DistanceComparator;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityArrow;
import net.minecraft.server.v1_5_R3.EntityEnderCrystal;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityItem;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.MathHelper;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.NBTTagList;
import net.minecraft.server.v1_5_R3.Packet100OpenWindow;
import net.minecraft.server.v1_5_R3.World;

public class EntityElementalTurret extends EntityEnderCrystal {
	public TargetSelector selector;
	public DistanceComparator h;
	public InventoryTurret inventory;
	public String name;
	public int health;
	public float range;
	public int attackTimer;
	public int attackDelay;
	public Entity target;

	public EntityElementalTurret(World world) {
		super(world);
		this.selector = new TargetSelectorMonster();
		this.inventory = new InventoryTurret(this);
		this.h = new DistanceComparator(null, this);
		this.b = 8;
		this.health = 8;
		this.attackDelay = 15;
		this.range = 8.0F;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void l_() {
		this.lastX = this.locX;
		this.lastY = this.locY;
		this.lastZ = this.locZ;
		this.a += 1;
		this.datawatcher.watch(8, Integer.valueOf(this.health));

		if (this.target == null || this.target.dead || this.e(this.target) > this.range) {
			this.target = this.getValidTarget();
		}

		if (this.a % 2 == 0) {
			List<Entity> e = this.world.getEntities(this, this.boundingBox.grow(0.5D, this.length, 0.5D));
			e.addAll(this.findNearbyPlayers(3.0D));
			for (Entity ent : e) {
				this.collide(ent);
			}
		}

		if (this.target == null) {
			this.attackTimer = 0;
		} else {
			this.attackTimer--;
			if (this.attackTimer <= 0) {
				this.attackTimer = this.attackDelay;
			} else {
				return;
			}

			List<Entity> e2 = this.getNearbyTargets();
			boolean stop = false;
			for (Entity ent : e2) {
				if (this.e(ent) <= 8.0D) {
					double d0 = ent.locX - this.locX;
					double d1 = ent.locZ - this.locZ;
					double d2 = MathHelper.a(d0, d1);

					d2 = MathHelper.sqrt(d2);
					d0 /= d2;
					d1 /= d2;
					double d3 = 1.0D / d2;

					d0 *= d3 * 0.95D;
					d1 *= d3 * 0.95D;
					ent.g(d0, 0.1D, d1);

					if (ent == this.target) {
						stop = true;
					}
				}
			}

			if (stop) {
				return;
			}

			Integer[] i = this.getArrowSlots();
			if (i.length > 0) {
				Location l = this.getBukkitEntity().getLocation().add(0.0D, this.length + 0.4D, 0.0D);
				Location l2 = this.target.getBukkitEntity().getLocation().add(0, this.target.length, 0);

				Vector v = this.getVector(l, l2);
				double speedMulti = 1.0D;

				int s = i[this.random.nextInt(i.length)];
				ItemStack is = this.inventory.getItem(s);
				ItemStack is2 = is.cloneItemStack();
				is2.count--;
				this.inventory.setItem(s, is2.count <= 0 ? null : is2);

				ArrowMaterial m = this.getMaterial(is);
				if (m != null) {
					speedMulti = m.getSpeedMutiplier();
				}

				ElementalArrowsAPI api = ElementalArrows.getAPI();
				ElementalArrow a = api.shootElementalArrow(l, v, (float) (1.4F * speedMulti), 3.0F);
				((CraftElementalArrow) a).getHandle().turret = this;
				a.setCritical(true);
				a.setPickupable(true);

				if (m != null) {
					double d = m.getKnockbackStrengthMultiplier();
					a.setMaterial(m);
					a.setKnockbackStrength(Math.round((float) (d == 0.0D ? 1.0D : d)));
					a.setDamage(1.2D * a.getDamage() + this.random.nextGaussian() * 0.22D * m.getDamageMultiplier() + this.world.difficulty * 0.09F);
					m.onShoot(null, a, null);
				}

				this.world.a((EntityHuman) null, 1014, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
				api.playEffect(ParticleEffect.WITCH_MAGIC, this.getBukkitEntity().getLocation().add(0, 0.9D, 0), new Vector(), 0.1F, 70);
			}
		}
	}

	@Override
	public boolean a_(EntityHuman human) {
		EntityPlayer p = (EntityPlayer) human;
		Container container = CraftEventFactory.callInventoryOpenEvent(p, new ContainerTurret(p.inventory, this.inventory));
		if (container == null) {
			return super.a_(p);
		}

		int c = p.nextContainerCounter();
		p.playerConnection.sendPacket(new Packet100OpenWindow(c, 3, this.inventory.getName(), this.inventory.getSize(), this.inventory.c()));
		p.activeContainer = container;
		p.activeContainer.windowId = c;
		p.activeContainer.addSlotListener(p);

		return super.a_(human);
	}

	private boolean canSee(Entity ent) {
		return this.world.a(this.world.getVec3DPool().create(this.locX, this.locY + this.getHeadHeight(), this.locZ), this.world.getVec3DPool().create(ent.locX, ent.locY + ent.getHeadHeight(), ent.locZ)) == null;
	}

	private List<EntityHuman> findNearbyPlayers(double d3) {
		List<EntityHuman> l = new ArrayList<EntityHuman>();

		for (int i = 0; i < this.world.players.size(); i++) {
			EntityHuman h = (EntityHuman) this.world.players.get(i);

			if (h == null || h.dead) {
				continue;
			}

			if (this.e(h) <= d3) {
				l.add(h);
			}
		}

		return l;
	}

	private Integer[] getArrowSlots() {
		List<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < this.inventory.getSize(); i++) {
			ItemStack is = this.inventory.getItem(i);
			if (is != null) {
				if (this.getMaterial(is) != null || is.id == Item.ARROW.id) {
					l.add(i);
				}
			}
		}
		return l.toArray(new Integer[] {});
	}

	private Vector getVector(Location start, Location end) {
		double dX = start.getX() - end.getX();
		double dY = start.getY() - end.getY();
		double dZ = start.getZ() - end.getZ();

		double yaw = Math.atan2(dZ, dX);
		double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

		double X = Math.sin(pitch) * Math.cos(yaw);
		double Y = Math.sin(pitch) * Math.sin(yaw);
		double Z = Math.cos(pitch);

		return new Vector(X, Z, Y);
	}

	private ArrowMaterial getMaterial(ItemStack item) {
		ElementalItemStack i = new ElementalItemStack(CraftItemStack.asCraftMirror(item));
		return (ArrowMaterial) (i.isCustomItem() && i.getMaterial() instanceof ArrowMaterial ? i.getMaterial() : null);
	}

	private Entity getValidTarget() {
		List<Entity> l = this.getValidTargets();
		return l.size() > 0 ? l.get(0) : null;
	}

	private List<Entity> getValidTargets() {
		List<Entity> l = new ArrayList<Entity>();
		List<Entity> l2 = this.getNearbyTargets();
		for (Entity e : l2) {
			if (this.canSee(e)) {
				l.add(e);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	private List<Entity> getNearbyTargets() {
		if (this.selector == null) {
			return new ArrayList<Entity>();
		}
		List<Entity> l = this.world.a(Entity.class, this.boundingBox.grow(this.range, 4.0D, this.range), new EntityTargetSelector(this.selector));
		Collections.sort(l, this.h);
		return l;
	}

	@Override
	public CraftElementalTurret getBukkitEntity() {
		if (this.bukkitEntity == null || !(this.bukkitEntity instanceof CraftElementalTurret)) {
			this.bukkitEntity = new CraftElementalTurret(this);
		}
		return (CraftElementalTurret) this.bukkitEntity;
	}

	@Override
	public void a(NBTTagCompound tag) {
		super.a(tag);
		NBTTagList l = tag.getList("Items");

		for (int i = 0; i < l.size(); i++) {
			NBTTagCompound s = (NBTTagCompound) l.get(i);
			int j = s.getByte("Slot") & 0xFF;

			if (j >= 0 && j < this.inventory.getSize()) {
				this.inventory.setItem(j, ItemStack.createStack(s));
			}
		}

		if (tag.hasKey("CustomName")) {
			this.name = tag.getString("CustomName");
		}

		this.attackDelay = tag.getInt("AttackDelay");
		this.health = tag.getInt("Health");
		this.range = tag.getFloat("AttackRange");
	}

	@Override
	public void b(NBTTagCompound tag) {
		super.b(tag);
		NBTTagList l = new NBTTagList();

		for (int i = 0; i < this.inventory.getSize(); i++) {
			ItemStack is = this.inventory.getItem(i);
			if (is != null) {
				NBTTagCompound t = new NBTTagCompound();
				t.setByte("Slot", (byte) i);
				is.save(t);
				l.add(t);
			}
		}

		tag.set("Items", l);
		tag.setInt("AttackDelay", this.attackDelay);
		tag.setInt("Health", this.health);
		tag.setFloat("AttackRange", this.range);
		if (this.name != null) {
			tag.setString("CustomName", this.name);
		}
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, int i) {
		if (this.isInvulnerable()) {
			return false;
		}

		if (damagesource.h() != null && damagesource.h() instanceof EntityArrow) {
			return false;
		}

		if (!this.dead) {
			this.health -= i;
			if (this.health <= 0) {
				this.die();
			}
		}

		return true;
	}

	@Override
	public void die() {
		ItemStack[] is = this.inventory.getContents();
		for (HumanEntity h : this.inventory.getViewers()) {
			h.closeInventory();
		}
		super.die();
		this.world.createExplosion(this, this.locX, this.locY, this.locZ, 3.0F, false, false);
		for (ItemStack is2 : is) {
			if (is2 != null) {
				EntityItem ent = new EntityItem(this.world, this.locX, this.locY, this.locZ, is2);
				this.world.addEntity(ent);
			}
		}
	}

	@Override
	public String getLocalizedName() {
		return this.name == null ? "Elemental Turret" : this.name;
	}
}