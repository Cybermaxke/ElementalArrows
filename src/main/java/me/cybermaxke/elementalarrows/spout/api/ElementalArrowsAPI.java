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
package me.cybermaxke.elementalarrows.spout.api;

import me.cybermaxke.elementalarrows.spout.api.data.ParticleEffect;
import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkEffect;

import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Vector3;

public interface ElementalArrowsAPI {

	/**
	 * Plays a firework effect at the position.
	 * @param effect
	 * @param position
	 */
	public void playEffect(Point position, FireworkEffect effect);

	/**
	 * Plays the firework effects at the position.
	 * @param effects
	 * @param position
	 */
	public void playEffects(Point position, FireworkEffect... effects);

	/**
	 * Plays a particle effect at the position.
	 * @param effect
	 * @param position
	 * @param offset
	 * @param velocity
	 * @param count
	 */
	public void playEffect(ParticleEffect effect, Point position, Vector3 offset, float velocity, int count);

	/**
	 * Plays a particle effect at the position.
	 * @param effect
	 * @param position
	 * @param offset
	 * @param velocity
	 * @param count
	 * @param data
	 */
	public void playEffect(ParticleEffect effect, Point position, Vector3 offset, float velocity, int count, Object... data);

	/**
	 * Plays a particle effect at the position, for a specific player.
	 * @param player
	 * @param effect
	 * @param position
	 * @param offset
	 * @param velocity
	 * @param count
	 */
	public void playEffect(Player player, ParticleEffect effect, Point position, Vector3 offset, float velocity, int count);

	/**
	 * Plays a particle effect at the position, for a specific player.
	 * @param player
	 * @param effect
	 * @param position
	 * @param offset
	 * @param velocity
	 * @param count
	 * @param data
	 */
	public void playEffect(Player player, ParticleEffect effect, Point position, Vector3 offset, float velocity, int count, Object... data);
}