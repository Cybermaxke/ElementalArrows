/**
 * 
 * This software is part of the ElementalArrows
 * 
 * This plugins adds custom arrows to the game like they from the
 * ElemantalArrows mod but ported to spoutplugin and bukkit.
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
package me.cybermaxke.ElementalArrows;

import java.lang.reflect.Method;
import java.util.logging.Level;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;
import me.cybermaxke.ElementalArrows.Materials.DazingArrow;
import me.cybermaxke.ElementalArrows.Materials.DirtArrow;
import me.cybermaxke.ElementalArrows.Materials.DualArrow;
import me.cybermaxke.ElementalArrows.Materials.EggArrow;
import me.cybermaxke.ElementalArrows.Materials.EnderEyeArrow;
import me.cybermaxke.ElementalArrows.Materials.ExplosionArrow;
import me.cybermaxke.ElementalArrows.Materials.FireArrow;
import me.cybermaxke.ElementalArrows.Materials.IceArrow;
import me.cybermaxke.ElementalArrows.Materials.LightningArrow;
import me.cybermaxke.ElementalArrows.Materials.PoisonArrow;
import me.cybermaxke.ElementalArrows.Materials.RazorArrow;
import me.cybermaxke.ElementalArrows.Materials.VampireArrow;
import me.cybermaxke.ElementalArrows.Materials.VollyArrow;
import me.cybermaxke.ElementalArrows.Utils.Metrics;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.Item;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.getspout.spoutapi.material.MaterialData;

public class ElementalArrows extends JavaPlugin {
	private static ElementalArrows instance;

	public static CustomArrowItem EGG_ARROW;
	public static CustomArrowItem EXPLOSION_ARROW;
	public static CustomArrowItem FIRE_ARROW;
	public static CustomArrowItem LIGHTNING_ARROW;
	public static CustomArrowItem DIRT_ARROW;
	public static CustomArrowItem POISON_ARROW;
	public static CustomArrowItem RAZOR_ARROW;
	public static CustomArrowItem DUAL_ARROW;
	public static CustomArrowItem ICE_ARROW;
	public static CustomArrowItem ENDER_EYE_ARROW;
	public static CustomArrowItem DAZING_ARROW;
	public static CustomArrowItem VOLLY_ARROW;
	public static CustomArrowItem VAMPIRE_ARROW;
	public static CustomArrowItem PULL_ARROW;
	public static CustomArrowItem BLINDNESS_ARROW;

	@Override
	public void onEnable() {
		instance = this;

		Bukkit.getServer().getPluginManager().registerEvents(new ArrowListener(), this);

		this.updateArrows();
		this.updateBows();
		this.registerArrows();

		try {
			Metrics m = new Metrics(this);
			m.start();
			this.getLogger().log(Level.INFO, "Metrics loaded.");
		} catch (Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.WARNING, "Couldn't load Metrics!");
		}

		this.getLogger().log(Level.INFO, "Plugin enabled.");
	}

	@Override
	public void onDisable() {

	}

	private void registerArrows() {
		EGG_ARROW = new EggArrow(this, "Egg Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/EggArrow.png");
		EXPLOSION_ARROW = new ExplosionArrow(this, "Explosion Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/ExplosionArrow.png");
		FIRE_ARROW = new FireArrow(this, "Fire Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/FireArrow.png");
		LIGHTNING_ARROW = new LightningArrow(this, "Lightning Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/LightningArrow.png");
		DIRT_ARROW = new DirtArrow(this, "Dirt Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/DirtArrow.png");
		POISON_ARROW = new PoisonArrow(this, "Poison Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/PoisonArrow.png");
		RAZOR_ARROW = new RazorArrow(this, "Razor Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/RazorArrow.png");
		DUAL_ARROW = new DualArrow(this, "Dual Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/DualArrow.png");
		ICE_ARROW = new IceArrow(this, "Ice Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/IceArrow.png");
		ENDER_EYE_ARROW = new EnderEyeArrow(this, "EnderEye Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/EnderEyeArrow.png");
		DAZING_ARROW = new DazingArrow(this, "Dazing Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/DazingArrow.png");
		VOLLY_ARROW = new VollyArrow(this, "Volly Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/VollyArrow.png");
		VAMPIRE_ARROW = new VampireArrow(this, "Vampiric Arrow", "http://dl.dropbox.com/u/104060836/ElementalArrows/Resources/VampireArrow.png");
		//PULL_ARROW = new PullArrow(this, "Pull Arrow", null);
		//BLINDNESS_ARROW = new BlindnessArrow(this, "Blindness Arrow", null);
	}

	private void updateBows() {
		Item.byId[MaterialData.bow.getRawId()] = null;
		Item.byId[MaterialData.bow.getRawId()] = new CustomItemBow();
	}

	private void updateArrows() {
		try {
			Class<?>[] args = new Class[] { Class.class, String.class, int.class };

			Method a = EntityTypes.class.getDeclaredMethod("a", args);
			a.setAccessible(true);

			a.invoke(a, ArrowEntity.class, "ElementalArrow", 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ElementalArrows getInstance() {
		return instance;
	}
}