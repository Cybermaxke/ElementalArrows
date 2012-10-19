package me.cybermaxke.ElementalArrows;

import java.lang.reflect.Method;

import me.cybermaxke.ElementalArrows.Materials.DirtArrow;
import me.cybermaxke.ElementalArrows.Materials.EggArrow;
import me.cybermaxke.ElementalArrows.Materials.ExplosionArrow;
import me.cybermaxke.ElementalArrows.Materials.FireArrow;
import me.cybermaxke.ElementalArrows.Materials.LightningArrow;
import me.cybermaxke.ElementalArrows.Materials.PoisonArrow;
import me.cybermaxke.ElementalArrows.Utils.Metrics;

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
		    System.out.println("[" + this.getName() + "] Metrics loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[" + this.getName() + "] Plugin loaded.");
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
	}
	
	private void updateBows() {
		net.minecraft.server.Item.byId[MaterialData.bow.getRawId()] = null;
		net.minecraft.server.Item.byId[MaterialData.bow.getRawId()] = new CustomItemBow();
	}
	
	private void updateArrows() {
		try {
			Class<?>[] args = new Class[3];
			args[0] = Class.class;
			args[1] = String.class;
			args[2] = int.class;

			Method a = net.minecraft.server.EntityTypes.class.getDeclaredMethod("a", args);
			a.setAccessible(true);

			a.invoke(a, ArrowEntity.class, "Arrow", 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}