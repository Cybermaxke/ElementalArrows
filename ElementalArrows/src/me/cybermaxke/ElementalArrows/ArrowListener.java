package me.cybermaxke.ElementalArrows;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;

import net.minecraft.server.v1_4_6.EntityArrow;

import org.bukkit.craftbukkit.v1_4_6.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ArrowListener implements Listener {

	@EventHandler
	public void onPlayerArrowHit(ProjectileHitEvent e) {
		if (!(e.getEntity() instanceof Arrow))
			return;
		
		Arrow a = (Arrow) e.getEntity();
		
		if (!(a.getShooter() instanceof Player))
			return;
		
		SpoutPlayer p = SpoutManager.getPlayer((Player) a.getShooter());
		EntityArrow en = ((CraftArrow) a).getHandle();
			
		if (!(en instanceof ArrowEntity))
			return;
		
		ArrowEntity ea = (ArrowEntity) en;
		if (ea.getArrow() != null) {
			CustomArrowItem i = ea.getArrow();
			
			i.onHit(p, ea);
		}
	}
	
	@EventHandler
	public void onPlayerArrowDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Arrow))
			return;
		
		if (!(e.getEntity() instanceof LivingEntity))
			return;
		
		Arrow a = (Arrow) e.getDamager();
		LivingEntity ent = (LivingEntity) e.getEntity();
		
		if (!(a.getShooter() instanceof Player))
			return;
		
		SpoutPlayer p = SpoutManager.getPlayer((Player) a.getShooter());		
		EntityArrow en = ((CraftArrow) a).getHandle();
			
		if (!(en instanceof ArrowEntity))
			return;
		
		ArrowEntity ea = (ArrowEntity) en;
		if (ea.getArrow() != null) {
			CustomArrowItem i = ea.getArrow();
			
			i.onHit(p, ent, ea);
		}
	}
}