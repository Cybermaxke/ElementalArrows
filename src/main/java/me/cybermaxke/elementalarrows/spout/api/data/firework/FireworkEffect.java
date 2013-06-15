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
package me.cybermaxke.elementalarrows.spout.api.data.firework;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FireworkEffect {
	private boolean flicker = false;
	private boolean trail = false;

	private List<Color> colors = new ArrayList<Color>();
	private List<Color> fadeColors = new ArrayList<Color>();

	private FireworkType type;

	public FireworkEffect(FireworkType type) {
		this.type = type;
	}

	/**
	 * Gets the type of this effect.
	 * @return type
	 */
	public FireworkType getType() {
		return this.type;
	}

	/**
	 * Sets the type of this effect.
	 * @param type
	 */
	public void setType(FireworkType type) {
		this.type = type;
	}

	/**
	 * Gets if this effect has a flicker effect.
	 * @return flicker
	 */
	public boolean hasFlicker() {
		return this.flicker;
	}

	/**
	 * Sets if this effect has a flicker effect.
	 * @param flicker
	 */
	public void setFlicker(boolean flicker) {
		this.flicker = flicker;
	}

	/**
	 * Gets if this effect has a trail effect.
	 * @return trail
	 */
	public boolean hasTrail() {
		return this.trail;
	}

	/**
	 * Sets if this effect has a trail effect.
	 * @param trail
	 */
	public void setTrail(boolean trail) {
		this.trail = trail;
	}

	/**
	 * Gets the colors of this effect.
	 * @return colors
	 */
	public List<Color> getColors() {
		return this.colors;
	}

	/**
	 * Adds the colors to this effect.
	 * @param color
	 */
	public void addColors(List<Color> colors) {
		this.colors.addAll(colors);
	}

	/**
	 * Adds a color to this effect.
	 * @param color
	 */
	public void addColor(Color color) {
		this.colors.add(color);
	}

	/**
	 * Gets the fade colors of this effect.
	 * @return fadecolors
	 */
	public List<Color> getFadeColors() {
		return this.fadeColors;
	}

	/**
	 * Adds the fade colors to this effect.
	 * @param color
	 */
	public void addFadeColors(List<Color> colors) {
		this.fadeColors.addAll(colors);
	}

	/**
	 * Adds a fade color to this effect.
	 * @param color
	 */
	public void addFadeColor(Color color) {
		this.fadeColors.add(color);
	}
}