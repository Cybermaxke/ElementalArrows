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
package me.cybermaxke.elementalarrows.spout.plugin.utils;

import java.awt.Color;

import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkEffect;
import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkType;

import org.spout.nbt.ByteTag;
import org.spout.nbt.CompoundMap;
import org.spout.nbt.IntArrayTag;

public class FireworkUtils {

	private FireworkUtils() {

	}

	public static CompoundMap getCompoundMap(FireworkEffect effect) {
		CompoundMap map = new CompoundMap();

		int[] colors = new int[effect.getColors().size()];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = effect.getColors().get(i).getRGB();
		}

		int[] fadeColors = new int[effect.getFadeColors().size()];
		for (int i = 0; i < fadeColors.length; i++) {
			fadeColors[i] = effect.getFadeColors().get(i).getRGB();
		}

		map.put(new ByteTag("Flicker", (byte) (effect.hasFlicker() ? 1 : 0)));
		map.put(new ByteTag("Trail", (byte) (effect.hasTrail() ? 1 : 0)));
		map.put(new ByteTag("Type", (byte) (effect.getType().getId())));
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