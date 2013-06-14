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

import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundMap;
import org.spout.nbt.IntArrayTag;

public class FireworkEffect {
	private boolean flicker = false;
	private boolean trail = false;

	private List<Color> colors = new ArrayList<Color>();
	private List<Color> fadeColors = new ArrayList<Color>();

	private FireworkType type;

	public FireworkEffect(FireworkType type) {
		this.type = type;
	}

	public FireworkType getType() {
		return this.type;
	}

	public void setType(FireworkType type) {
		this.type = type;
	}

	public boolean hasFlicker() {
		return this.flicker;
	}

	public void setFlicker(boolean flicker) {
		this.flicker = flicker;
	}

	public boolean hasTrail() {
		return this.trail;
	}

	public void setTrail(boolean trail) {
		this.trail = trail;
	}

	public List<Color> getColors() {
		return this.colors;
	}

	public void addColor(Color color) {
		this.colors.add(color);
	}

	public List<Color> getFadeColors() {
		return this.fadeColors;
	}

	public void addFadeColor(Color color) {
		this.fadeColors.add(color);
	}

	public CompoundMap getTag() {
		CompoundMap map = new CompoundMap();

		int[] colors = new int[this.colors.size()];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = this.colors.get(i).getRGB();
		}

		int[] fadeColors = new int[this.fadeColors.size()];
		for (int i = 0; i < fadeColors.length; i++) {
			fadeColors[i] = this.fadeColors.get(i).getRGB();
		}

		map.put(new ByteTag("Flicker", (byte) (this.flicker ? 1 : 0)));
		map.put(new ByteTag("Trail", (byte) (this.trail ? 1 : 0)));
		map.put(new ByteTag("Type", (byte) (this.getType().getId())));
		map.put(new IntArrayTag("Colors", colors));
		map.put(new IntArrayTag("FadeColors", fadeColors));

		return map;
	}

	public static FireworkEffect getEffect(CompoundMap map) {
		boolean flicker = (Byte) map.get("Flicker").getValue() == 1 ? true : false;
		boolean trail = (Byte) map.get("Trail").getValue() == 1 ? true : false;
		FireworkType type = FireworkType.getType((Byte) map.get("Type").getValue());
		int[] colors = (int[]) map.get("Colors").getValue();
		int[] fadeColors = (int[]) map.get("FadeColors").getValue();

		FireworkEffect firework = new FireworkEffect(type);
		firework.setFlicker(flicker);
		firework.setFlicker(trail);

		for (int color : colors) {
			firework.addColor(new Color(color));
		}

		for (int color : fadeColors) {
			firework.addFadeColor(new Color(color));
		}

		return firework;
	}
}