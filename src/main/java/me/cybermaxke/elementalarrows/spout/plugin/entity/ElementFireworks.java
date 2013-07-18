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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkEffect;
import me.cybermaxke.elementalarrows.spout.api.entity.ElementalFireworks;
import me.cybermaxke.elementalarrows.spout.plugin.data.ElementalData;
import me.cybermaxke.elementalarrows.spout.plugin.protocol.ElementalFireworkProtocol;
import me.cybermaxke.elementalarrows.spout.plugin.utils.EntityUtils;
import me.cybermaxke.elementalarrows.spout.plugin.utils.FireworkUtils;

import org.spout.api.collision.BoundingBox;
import org.spout.api.component.entity.SceneComponent;
import org.spout.api.geo.discrete.Point;
import org.spout.api.inventory.ItemStack;
import org.spout.api.math.QuaternionMath;
import org.spout.api.math.Vector3;
import org.spout.api.util.Parameter;

import org.spout.nbt.CompoundMap;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.ListTag;

import org.spout.vanilla.VanillaPlugin;
import org.spout.vanilla.component.entity.substance.Substance;
import org.spout.vanilla.event.entity.EntityStatusEvent;

@SuppressWarnings("unchecked")
public class ElementFireworks extends Substance implements ElementalFireworks {
	private Random random = new Random();

	private int ticksFlown = 0;
	private int ticksBeforeExplosion = 0;

	@Override
	public void onAttached() {
		this.getOwner().getNetwork().setEntityProtocol(VanillaPlugin.VANILLA_PROTOCOL_ID, new ElementalFireworkProtocol());
		super.onAttached();

		this.ticksBeforeExplosion = 10 * this.getPower() + this.random.nextInt(6) + this.random.nextInt(7);

		float motX = (float) (this.random.nextGaussian() * 0.001F);
		float motZ = (float) (this.random.nextGaussian() * 0.001F);
		float motY = 0.05F;

		SceneComponent scene = this.getOwner().getScene();
		scene.setMovementVelocity(new Vector3(motX, motY, motZ));
		scene.activate(new BoundingBox(0.25F, 0.25F, 0.25F), scene.getMass());

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

		motX *= 1.15F;
		motY += 0.04F;
		motZ *= 1.15F;

		locX += motX;
		locY += motY;
		locZ += motZ;

		float yaw = (float) (Math.atan2(motX, motZ) * 180.0F / Math.PI);
		float pitch = (float) (Math.atan2(motY, Math.sqrt(motX * motX + motZ * motZ)) * 180.0F / Math.PI);

		scene.setMovementVelocity(new Vector3(motX, motY, motZ));
		scene.setPosition(new Point(p.getWorld(), locX, locY, locZ));
		scene.setRotation(QuaternionMath.rotation(pitch, yaw, 0.0F));

		this.ticksFlown++;
		if (this.ticksFlown >= this.ticksBeforeExplosion) {
			this.getOwner().getNetwork().callProtocolEvent(new EntityStatusEvent(this.getOwner(), (byte) 17));
			this.getOwner().remove();
		}
	}

	@Override
	public int getPower() {
		return this.getData().get(ElementalData.FIREWORK_POWER);
	}

	@Override
	public void setPower(int power) {
		this.getData().put(ElementalData.FIREWORK_POWER, power);
		this.ticksBeforeExplosion = 10 * power + this.random.nextInt(6) + this.random.nextInt(7);
	}

	@Override
	public List<FireworkEffect> getEffects() {
		List<FireworkEffect> effects = new ArrayList<FireworkEffect>();
		CompoundMap map1 = this.getItem().getNBTData();

		if (this.getItem().getNBTData() == null || !map1.containsKey("Fireworks")) {
			return effects;
		}

		CompoundMap map2 = (CompoundMap) map1.get("Fireworks").getValue();
		if (!map2.containsKey("Explosions")) {
			return effects;
		}

		List<CompoundTag> list = ((ListTag<CompoundTag>) map2.get("Explosions")).getValue();
		for (CompoundTag tag : list) {
			effects.add(FireworkUtils.getEffect(tag.getValue()));
		}

		return effects;
	}

	@Override
	public void addEffect(FireworkEffect effect) {
		this.addEffects(effect);
	}

	@Override
	public void addEffects(FireworkEffect... effects) {	
		CompoundMap map1 = this.getItem().getNBTData();

		if (map1 == null) {
			map1 = new CompoundMap();
		}

		List<CompoundTag> list = new ArrayList<CompoundTag>();
		CompoundMap map2 = (CompoundMap) (map1.containsKey("Fireworks") ? map1.get("Fireworks").getValue() : new CompoundMap());

		if (map2.containsKey("Explosions")) {
			list.addAll(((ListTag<CompoundTag>) map2.get("Explosions")).getValue());
		}

		for (FireworkEffect effect : effects) {
			list.add(new CompoundTag("Effect" + (list.size() + 1), FireworkUtils.getCompoundMap(effect)));
		}

		map2.put(new ListTag<CompoundTag>("Explosions", CompoundTag.class, list));
		map1.put(new CompoundTag("Fireworks", map2));

		ItemStack is = this.getItem();
		is.setNBTData(map1);

		this.setItem(is);
	}

	public ItemStack getItem() {
		return this.getData().get(ElementalData.FIREWORK_EFFECT);
	}

	public void setItem(ItemStack itemstack) {
		this.getData().put(ElementalData.FIREWORK_EFFECT, itemstack);
		this.setMetadata(new Parameter<ItemStack>(Parameter.TYPE_ITEM, 8, itemstack));
	}
}