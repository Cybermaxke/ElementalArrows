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
package me.cybermaxke.elementalarrows.bukkit.api;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.util.Vector;

import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalArrow;
import me.cybermaxke.elementalarrows.bukkit.api.entity.ElementalPlayer;

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

	/**
	 * Gets if the block is replaceable by a other block.
	 * @param block
	 * @return replaceable
	 */
	public boolean isReplaceable(Material block);

	/**
	 * Plays all the firework effects at the location.
	 * @param location
	 * @param effects
	 */
	public void playFireworkEffect(Location location, FireworkEffect... effects);

	/**
	 * Gets the height of the entity.
	 * @return height
	 */
	public float getEntityHeight(Entity entity);

	/**
	 * Gets the length of the entity.
	 * @return length
	 */
	public float getEntityLength(Entity entity);

	/**
	 * Gets the width of the entity.
	 * @return width
	 */
	public float getEntityWidth(Entity entity);

	/**
	 * Plays a particle effect at the location.
	 * @param effect
	 * @param location
	 * @param offset
	 * @param velocity
	 * @param count
	 */
	public void playEffect(ParticleEffect effect, Location location, Vector offset, float velocity, int count);

	/**
	 * Plays a particle effect at the location.
	 * @param effect
	 * @param location
	 * @param offset
	 * @param velocity
	 * @param count
	 * @param data
	 */
	public void playEffect(ParticleEffect effect, Location location, Vector offset, float velocity, int count, Object... data);

	/**
	 * Plays a particle effect at the location for a specific player.
	 * @param player
	 * @param effect
	 * @param location
	 * @param offset
	 * @param velocity
	 * @param count
	 */
	public void playEffect(Player player, ParticleEffect effect, Location location, Vector offset, float velocity, int count);

	/**
	 * Plays a particle effect at the location for a specific player.
	 * @param player
	 * @param effect
	 * @param location
	 * @param offset
	 * @param velocity
	 * @param count
	 * @param data
	 */
	public void playEffect(Player player, ParticleEffect effect, Location location, Vector offset, float velocity, int count, Object... data);
}