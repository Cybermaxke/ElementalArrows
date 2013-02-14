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
import me.cybermaxke.ElementalArrows.Utils.Metrics;

import net.minecraft.server.v1_4_R1.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import org.getspout.spoutapi.material.MaterialData;

public class ElementalArrows extends JavaPlugin {

	@Override
	public void onEnable() {	
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
		new EggArrow(this, "Egg Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/EggArrow.png");
		new ExplosionArrow(this, "Explosion Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/ExplosionArrow.png");
		new FireArrow(this, "Fire Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/FireArrow.png");
		new LightningArrow(this, "Lightning Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/LightningArrow.png");
		new DirtArrow(this, "Dirt Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/DirtArrow.png");
		new PoisonArrow(this, "Poison Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/PoisonArrow.png");
		new RazorArrow(this, "Razor Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/RazorArrow.png");
		new DualArrow(this, "Dual Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/DualArrow.png");
		new IceArrow(this, "Ice Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/IceArrow.png");
		new EnderEyeArrow(this, "EnderEye Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/EnderEyeArrow.png");
		new DazingArrow(this, "Dazing Arrow", "http://dl.dropbox.com/u/104060836/LegendsOfCubeCraft/Arrows/DazingArrow.png");
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

			a.invoke(a, ArrowEntity.class, "Arrow", 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}