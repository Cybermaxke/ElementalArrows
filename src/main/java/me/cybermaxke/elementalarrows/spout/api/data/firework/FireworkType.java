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

import java.util.HashMap;
import java.util.Map;

public enum FireworkType {
	BALL(0),
	BALL_LARGE(1),
	STAR(2),
	CREEPER(3),
	BURST(4);

	private static Map<Integer, FireworkType> ids = new HashMap<Integer, FireworkType>();

	private int id;
	private FireworkType(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static FireworkType getType(int id) {
		return ids.get(id);
	}

	static {
		for (FireworkType type : FireworkType.values()) {
			ids.put(type.getId(), type);
		}
	}
}