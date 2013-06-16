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
package me.cybermaxke.elementalarrows.spout.plugin.entity;

import java.util.Random;

import me.cybermaxke.elementalarrows.spout.api.data.PickupMode;
import me.cybermaxke.elementalarrows.spout.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.spout.plugin.data.ElementalData;
import me.cybermaxke.elementalarrows.spout.plugin.protocol.ElementalArrowProtocol;
import me.cybermaxke.elementalarrows.spout.plugin.utils.EntityUtils;

import org.spout.api.collision.BoundingBox;
import org.spout.api.component.entity.SceneComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.BlockMaterial;
import org.spout.api.math.Quaternion;
import org.spout.api.math.QuaternionMath;
import org.spout.api.math.Vector3;
import org.spout.api.util.Parameter;

import org.spout.vanilla.VanillaPlugin;
import org.spout.vanilla.component.entity.living.Human;
import org.spout.vanilla.component.entity.misc.Burn;
import org.spout.vanilla.component.entity.misc.EntityHead;
import org.spout.vanilla.component.entity.misc.Health;
import org.spout.vanilla.data.GameMode;
import org.spout.vanilla.event.cause.DamageCause.DamageType;
import org.spout.vanilla.event.cause.EntityDamageCause;
import org.spout.vanilla.material.block.Liquid;
import org.spout.vanilla.material.block.liquid.Lava;
import org.spout.vanilla.material.block.liquid.Water;
import org.spout.vanilla.material.block.misc.Fire;

public class ElementArrow extends ElementalArrow {
	private Random random = new Random();
	private Entity shooter;

	@Override
	public void onAttached() {
		this.getOwner().getNetwork().setEntityProtocol(VanillaPlugin.VANILLA_PROTOCOL_ID, new ElementalArrowProtocol());
		super.onAttached();

		SceneComponent scene = this.getOwner().getScene();
		scene.activate(new BoundingBox(0.5F, 0.5F, 0.5F), scene.getMass());

		EntityUtils.updateSnapshotPosition(scene);
	}

	@Override
	public void onDetached() {
		super.onDetached();
	}

	@Override
	public void onCollided(Point point, Entity entity) {
		if (entity.get(Health.class) != null) {
			Vector3 v = this.getOwner().getScene().getMovementVelocity();

			float motX = v.getX();
			float motY = v.getY();
			float motZ = v.getZ();

			float damage = (float) (this.getDamage() * Math.sqrt(motX * motX + motY * motY + motZ * motZ));
			if (this.isCritical()) {
				damage += this.random.nextInt(Math.round(damage) / 2 + 2);
			}

			entity.get(Health.class).damage(Math.round(damage), new EntityDamageCause(this.getOwner(), DamageType.PROJECTILE));
		}
		if (entity.get(Burn.class) != null) {
			entity.get(Burn.class).setOnFire(this.getFireTicks(), true);
		}
	}

	@Override
	public void onCollided(Point point, Block block) {
		//TODO: Setting the arrow as inGround and allowing players to pick it up.

		SceneComponent scene = this.getOwner().getScene();
		scene.setMovementVelocity(Vector3.ZERO);

		EntityUtils.updateSnapshotPosition(scene);
	}

	@Override
	public void shoot(Entity shooter, float speed) {
		this.shooter = shooter;

		if (shooter instanceof Player && shooter.get(Human.class) != null) {
			Human h = shooter.get(Human.class);
			this.setPickupMode(h.getGameMode().equals(GameMode.CREATIVE) ? PickupMode.CREATIVE : PickupMode.NORMAL);
		}

		SceneComponent scene1 = this.getOwner().getScene();
		SceneComponent scene2 = shooter.getScene();

		Point p = scene2.getPosition();
		Quaternion r = null;

		if (shooter.get(EntityHead.class) != null) {
			r = shooter.get(EntityHead.class).getOrientation();
		} else {
			r = scene2.getRotation();
		}

		float locX = p.getX();
		float locY = p.getY();
		float locZ = p.getZ();

		float yaw = r.getYaw();
		float pitch = r.getPitch();

		locX -= Math.cos(-yaw / 180.0F * Math.PI) * 0.16F;
		locY -= 0.1F;
		locZ -= Math.sin(-yaw / 180.0F * Math.PI) * 0.16F;

		if (shooter.get(EntityHead.class) != null) {
			locY += shooter.get(EntityHead.class).getHeight();
		}

		scene1.setPosition(new Point(p.getWorld(), locX, locY, locZ));
		scene1.setRotation(QuaternionMath.rotation(pitch, yaw, 0.0F));

		float motX = (float) (Math.sin(yaw / 180.0F * Math.PI) * Math.cos(pitch / 180.0F * Math.PI));
		float motZ = (float) (Math.cos(yaw / 180.0F * Math.PI) * Math.cos(pitch / 180.0F * Math.PI));
		float motY = (float) -Math.sin(pitch / 180.0F * Math.PI);

		this.shoot(motX, motY, motZ, speed * 1.5F, 1.0F);
	}

	@Override
	public void shoot(Vector3 vector, float speed, float spread) {
		this.shoot(vector.getX(), vector.getY(), vector.getZ(), speed, spread);
	}

	@Override
	public void shoot(float motX, float motY, float motZ, float speed, float spread) {
		float f = (float) Math.sqrt(motX * motX + motY * motY + motZ * motZ);

		motX /= f;
		motY /= f;
		motZ /= f;

		motX += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.0075F * spread;
		motY += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.0075F * spread;
		motZ += this.random.nextGaussian() * (this.random.nextBoolean() ? -1 : 1) * 0.0075F * spread;

		motX *= speed;
		motY *= speed;
		motZ *= speed;

		float yaw = (float) (Math.atan2(motX, motZ) * 180.0F / Math.PI);
		float pitch = (float) (Math.atan2(motY, Math.sqrt(motX * motX + motZ * motZ)) * 180.0F / Math.PI);

		SceneComponent scene = this.getOwner().getScene();

		scene.setMovementVelocity(new Vector3(motX, motY, motZ));	
		scene.setRotation(QuaternionMath.rotation(pitch, yaw, 0.0F));

		/**
		 * Without calling this method, the arrow will just fall down.
		 */
		EntityUtils.updateSnapshotPosition(scene);
	}

	@Override
	public void onTick(float dt) {
		SceneComponent scene = this.getOwner().getScene();

		Point p = scene.getPosition();
		Vector3 v = scene.getMovementVelocity();

		float locX = p.getX();
		float locY = p.getY();
		float locZ = p.getZ();

		float motX = v.getX();
		float motY = v.getY();
		float motZ = v.getZ();

		locX += motX;
		locY += motY;
		locZ += motZ;

		/**
		 * TODO: Finding out why the arrow is moving glitchy.
		 */
		float yaw = (float) (Math.atan2(motX, motZ) * 180.0F / Math.PI);
		float pitch = (float) (Math.atan2(motY, Math.sqrt(motX * motX + motZ * motZ)) * 180.0F / Math.PI);

		float f = 0.99F;

		BlockMaterial block = this.getOwner().getWorld().getBlock(locX, locY, locZ).getMaterial();
		if (block instanceof Liquid) {
			f = 0.8F;
		}

		motX *= f;
		motY *= f;
		motZ *= f;
		motY -= 0.05F;

		scene.setMovementVelocity(new Vector3(motX, motY, motZ));
		scene.setPosition(new Point(p.getWorld(), locX, locY, locZ));
		scene.setRotation(QuaternionMath.rotation(pitch, yaw, 0.0F));

		if (this.isOnFire()) {
			this.setFireTicks(this.getFireTicks() - 1);
		}

		if (block instanceof Lava || block instanceof Fire) {
			this.setFireTicks(this.getFireTicks() + 100);
		} else if (block instanceof Water) {
			this.setFireTicks(0);
		}
	}

	@Override
	public Entity getShooter() {
		return this.shooter;
	}

	@Override
	public void setShooter(Entity shooter) {
		this.shooter = shooter;
	}

	@Override
	public PickupMode getPickupMode() {
		return this.getDatatable().get(ElementalData.PICKUP_MODE);
	}

	@Override
	public void setPickupMode(PickupMode mode) {
		this.getDatatable().put(ElementalData.PICKUP_MODE, mode);
	}

	@Override
	public boolean isCritical() {
		return this.getDatatable().get(ElementalData.CRITICAL);
	}

	@Override
	public void setCritical(boolean critical) {
		this.getDatatable().put(ElementalData.CRITICAL, critical);
		this.setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, (byte) (critical ? 1 : 0)));
	}

	@Override
	public int getDamage() {
		return this.getDatatable().get(ElementalData.ARROW_DAMAGE);
	}

	@Override
	public void setDamage(int damage) {
		this.getDatatable().put(ElementalData.ARROW_DAMAGE, damage);
	}

	@Override
	public boolean isOnFire() {
		return this.getFireTicks() > 0;
	}

	@Override
	public int getFireTicks() {
		return this.getDatatable().get(ElementalData.FIRE_TICKS);
	}

	@Override
	public void setFireTicks(int ticks) {
		this.getDatatable().put(ElementalData.FIRE_TICKS, ticks);
		this.setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 0, (byte) (ticks > 0 ? 1 : 0)));
	}
}