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
package me.cybermaxke.elementalarrows.spout.plugin;

import me.cybermaxke.elementalarrows.spout.api.ElementalArrows;
import me.cybermaxke.elementalarrows.spout.api.ElementalArrowsAPI;
import me.cybermaxke.elementalarrows.spout.api.data.ParticleEffect;
import me.cybermaxke.elementalarrows.spout.plugin.listener.ElementalPlayerListener;
import me.cybermaxke.elementalarrows.spout.plugin.material.ElementalBow;
import me.cybermaxke.elementalarrows.spout.plugin.material.ElementalMaterialUtils;

import org.spout.api.UnsafeMethod;
import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Vector3;
import org.spout.api.plugin.Plugin;
import org.spout.api.protocol.reposition.RepositionManager;

import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.protocol.msg.world.ParticleEffectMessage;
import org.spout.vanilla.protocol.reposition.VanillaRepositionManager;

public class ElementalArrowsPlugin extends Plugin implements ElementalArrowsAPI {
	private RepositionManager repositionManager;

	public static ElementalBow BOW;

	@Override
	@UnsafeMethod
	public void onEnable() {
		ElementalArrows.setAPI(this);
		ElementalMaterialUtils.setDataMask(VanillaMaterials.ARROW, (short) 0x7F);
		ElementalMaterialUtils.setDataMask(VanillaMaterials.BOW, (short) 0x7F);

		this.repositionManager = new VanillaRepositionManager();
		new ElementalPlayerListener(this);

		BOW = new ElementalBow("Elemental Bow");
	}

	@Override
	@UnsafeMethod
	public void onDisable() {

	}

	@Override
	public void playEffect(ParticleEffect effect, Point position, Vector3 offset, float velocity, int count) {
		this.playEffect(effect, position, offset, velocity, count, new Object[] {});
	}

	@Override
	public void playEffect(ParticleEffect effect, Point position, Vector3 offset, float velocity, int count, Object... data) {
		ParticleEffectMessage message = this.getParticleMessage(effect, position, offset, velocity, count, data);
		for (Player player : position.getWorld().getPlayers()) {
			player.getSession().send(false, message);
		}
	}

	@Override
	public void playEffect(Player player, ParticleEffect effect, Point position, Vector3 offset, float velocity, int count) {
		this.playEffect(player, effect, position, offset, velocity, count, new Object[] {});
	}

	@Override
	public void playEffect(Player player, ParticleEffect effect, Point position, Vector3 offset, float velocity, int count, Object... data) {
		player.getSession().send(false, this.getParticleMessage(effect, position, offset, velocity, count, data));
	}

	protected ParticleEffectMessage getParticleMessage(ParticleEffect effect, Point position, Vector3 offset, float velocity, int count, Object... data) {
		String name = this.getParticleName(effect);
		switch (effect) {
			case ICONCRACK:
				name = name + (data.length > 0 ? data[0] : 1);
				break;
			case TILECRACK:
				name = name + (data.length > 0 ? data[0] : 1) + "_" + (data.length > 1 ? data[1] : 0);
				break;
			default:
				break;
		}
		return new ParticleEffectMessage(name, position, offset, velocity, count, this.repositionManager);
	}

	protected String getParticleName(ParticleEffect effect) {
		switch (effect) {
			case ANGRY_VILLAGER:
				return "angryVillager";
			case BUBBLE:
				return "bubble";
			case CLOUD:
				return "cloud";
			case CRITICAL:
				return "crit";
			case DEPTH_SUSPEND:
				return "depthSuspend";
			case DRIP_LAVA:
				return "dripLava";
			case DRIP_WATER:
				return "dripWater";
			case ENCHANTMENT_TABLE:
				return "enchantmenttable";
			case EXPLOSION:
				return "explode";
			case FIREWORKS_SPARK:
				return "fireworksSpark";
			case FLAME:
				return "flame";
			case FOOTSTEP:
				return "footstep";
			case HAPPY_VILLAGER:
				return "happyVillager";
			case HUGE_EXPLOSION:
				return "hugeexplosion";
			case ICONCRACK:
				return "iconcrack_";
			case INSTANT_SPELL:
				return "instantSpell";
			case LARGE_EXPLOSION:
				return "largeexplode";
			case LARGE_SMOKE:
				return "largesmoke";
			case LAVA:
				return "lava";
			case MAGIC_CRITICAL:
				return "magicCrit";
			case MOB_SPELL:
				return "mobSpell";
			case MOB_SPELL_AMBIENT:
				return "mobSpellAmbient";
			case NOTE:
				return "note";
			case PORTAL:
				return "portal";
			case REDSTONE_DUST:
				return "reddust";
			case SLIME:
				return "slime";
			case SNOWBALL_POOF:
				return "snowballpoof";
			case SNOW_SHOVEL:
				return "snowshovel";
			case SPELL:
				return "spell";
			case SPLASH:
				return "splash";
			case SUSPEND:
				return "suspend";
			case TILECRACK:
				return "tilecrack_";
			case TOWN_AURA:
				return "townaura";
			case WITCH_MAGIC:
				return "witchMagic";
			case HEART:
			default:
				return "heart";
		}
	}
}