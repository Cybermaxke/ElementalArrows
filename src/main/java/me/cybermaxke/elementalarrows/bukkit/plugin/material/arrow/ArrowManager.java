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
package me.cybermaxke.elementalarrows.bukkit.plugin.material.arrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.cybermaxke.elementalarrows.bukkit.api.material.ArrowMaterial;

import org.bukkit.plugin.Plugin;

import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.MaterialData;

public class ArrowManager {
	public static ArrowMaterial ARROW_BLINDNESS;
	public static ArrowMaterial ARROW_DAZING;
	public static ArrowMaterial ARROW_DIRT;
	public static ArrowMaterial ARROW_DUAL;
	public static ArrowMaterial ARROW_EGG;
	public static ArrowMaterial ARROW_ENDER_EYE;
	public static ArrowMaterial ARROW_EXPLOSION;
	public static ArrowMaterial ARROW_FIRE;
	public static ArrowMaterial ARROW_ICE;
	public static ArrowMaterial ARROW_IMPLOSION;
	public static ArrowMaterial ARROW_LIGHTNING;
	public static ArrowMaterial ARROW_POISON;
	public static ArrowMaterial ARROW_PULL;
	public static ArrowMaterial ARROW_RAZOR;
	public static ArrowMaterial ARROW_TORCH;
	public static ArrowMaterial ARROW_VAMPIRE;
	public static ArrowMaterial ARROW_VOLLEY;

	public ArrowManager(Plugin plugin) {
		ARROW_BLINDNESS = new ArrowBlindness(plugin, "Blindness Arrow", "https://dl.dropbox.com/u/104060836/ElementalArrows/Resources/BlindnessArrow.png");
		ARROW_DAZING = new ArrowDazing(plugin, "Dazing Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/DazingArrow.png");
		ARROW_DIRT = new ArrowDirt(plugin, "Dirt Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/DirtArrow.png");
		ARROW_DUAL = new ArrowDual(plugin, "Dual Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/DualArrow.png");
		ARROW_EGG = new ArrowEgg(plugin, "Egg Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/EggArrow.png");
		ARROW_ENDER_EYE = new ArrowEnderEye(plugin, "EnderEye Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/EnderEyeArrow.png");
		ARROW_EXPLOSION = new ArrowExplosion(plugin, "Explosion Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/ExplosionArrow.png");
		ARROW_FIRE = new ArrowFire(plugin, "Fire Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/FireArrow.png");
		ARROW_ICE = new ArrowIce(plugin, "Ice Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/IceArrow.png");
		ARROW_IMPLOSION = new ArrowImplosion(plugin, "Implosion Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/ImplosionArrow.png");
		ARROW_LIGHTNING = new ArrowLightning(plugin, "Lightning Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/LightningArrow.png");
		ARROW_POISON = new ArrowPoison(plugin, "Poison Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/PoisonArrow.png");
		//ARROW_PULL = new ArrowPull(plugin, "Pull Arrow", "");
		ARROW_RAZOR = new ArrowRazor(plugin, "Razor Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/RazorArrow.png");
		ARROW_TORCH = new ArrowTorch(plugin, "Torch Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/TorchArrow.png");
		ARROW_VAMPIRE = new ArrowVampire(plugin, "Vampiric Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/VampireArrow.png");
		ARROW_VOLLEY = new ArrowVolley(plugin, "Volley Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/VollyArrow.png");
	}

	public static ArrowMaterial getRandomSkeletonArrow() {
		List<ArrowMaterial> m = getSkeletonArrows();
		return m.size() > 0 ? m.get(new Random().nextInt(m.size())) : null;
	}

	public static ArrowMaterial getRandomArrow() {
		List<ArrowMaterial> m = getArrows();
		return m.size() > 0 ? m.get(new Random().nextInt(m.size())) : null;
	}

	public static List<ArrowMaterial> getSkeletonArrows() {
		List<ArrowMaterial> l = new ArrayList<ArrowMaterial>();
		for (CustomItem i : MaterialData.getCustomItems()) {
			if (i instanceof ArrowMaterial && ((ArrowMaterial) i).isSkeletonUsable()) {
				l.add((ArrowMaterial) i);
			}
		}
		return l;
	}

	public static List<ArrowMaterial> getArrows() {
		List<ArrowMaterial> l = new ArrayList<ArrowMaterial>();
		for (CustomItem i : MaterialData.getCustomItems()) {
			if (i instanceof ArrowMaterial) {
				l.add((ArrowMaterial) i);
			}
		}
		return l;
	}
}