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
package me.cybermaxke.elementalarrows.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.Vector;

import me.cybermaxke.elementalarrows.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.api.entity.ElementalPlayer;

public interface ElementalArrowsAPI {

	/**
	 * Gets a elemental player for the player.
	 * @param player
	 * @return elemental player
	 */
	public ElementalPlayer getPlayer(Player player);

	/**
	 * Shoots a new arrow with the args.
	 * @param location
	 * @param vector
	 * @param speed
	 * @param spread
	 * @return arrow
	 */
	public ElementalArrow shootElementalArrow(Location location, Vector vector, float speed, float spread);

	/**
	 * Spawn and returns a entity with the class.
	 * @param entity
	 * @param location
	 * @return entity
	 */
	public <T extends Entity> T spawn(Class<T> entity, Location location);

	/**
	 * Spawn and returns a entity with the class.
	 * @param entity
	 * @param location
	 * @param reason
	 * @return entity
	 */
	public <T extends Entity> T spawn(Class<T> entity, Location location, SpawnReason reason);

	/**
	 * Gets if the region is protected.
	 * @param location
	 * @return protected
	 */
	public boolean isRegionProtected(Location location);
}