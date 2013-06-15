/**
 * 
 * This software is part of the VigondorsMagic
 * 
 * VigondorsMagic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * VigondorsMagic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with VigondorsMagic. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.spout.plugin.listener;

import me.cybermaxke.elementalarrows.spout.plugin.ElementalArrowsPlugin;
import me.cybermaxke.elementalarrows.spout.plugin.component.entity.ElementTurret;
import me.cybermaxke.elementalarrows.spout.plugin.component.player.ElementPlayer;

import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.player.PlayerInteractEntityEvent;
import org.spout.api.event.player.PlayerJoinEvent;
import org.spout.api.inventory.ItemStack;
import org.spout.api.plugin.Plugin;

import org.spout.vanilla.component.entity.inventory.PlayerInventory;
import org.spout.vanilla.component.entity.living.Human;

public class ElementPlayerListener implements Listener {

	public ElementPlayerListener(Plugin plugin) {
		plugin.getEngine().getEventManager().registerEvents(this, plugin);
	}

	@EventHandler(order = Order.EARLIEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.add(ElementPlayer.class);

		/**
		 * Adding the test bow ad fireworks to the players inventory.
		 */
		PlayerInventory inv = p.add(PlayerInventory.class);
		inv.add(new ItemStack(ElementalArrowsPlugin.BOW, 1));
		inv.add(new ItemStack(ElementalArrowsPlugin.FIREWORKS, 1));
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		Entity entity = e.getInteracted();

		Human human = e.getEntity().get(Human.class);
		ElementTurret turret = entity.get(ElementTurret.class);
		if (human != null && turret != null) {
			//TODO: Opening the inventory.
		}

		/**
		 * Testing the arrows in body method.
		 */
		if (human != null) {
			human.getOwner().add(ElementPlayer.class).setArrowsInBody((byte) 100);
		}
	}
}