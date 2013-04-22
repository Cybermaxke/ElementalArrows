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
package me.cybermaxke.elementalarrows.plugin.craftbukkit.entity;

import java.util.Random;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;
import me.cybermaxke.elementalarrows.plugin.craftbukkit.entity.nms.EntityElementalArrow;

import net.minecraft.server.v1_5_R2.EntityPlayer;

import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CraftElementalPlayer extends CraftPlayer implements ElementalPlayer {
	private Random random = new Random();

	public CraftElementalPlayer(Player player) {
		super(((CraftPlayer) player).getHandle().world.getServer(), ((CraftPlayer) player).getHandle());
	}

	@Override
	public ElementalArrow shootElementalArrow(float speed) {
		EntityPlayer player = this.getHandle();
		EntityElementalArrow arrow = new EntityElementalArrow(player.world, player, speed);
		player.world.addEntity(arrow);
		player.world.makeSound(player, "random.bow", 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
		return arrow.getBukkitEntity();
	}
}