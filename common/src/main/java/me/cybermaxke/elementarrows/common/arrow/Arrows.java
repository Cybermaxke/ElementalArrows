/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.common.arrow;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class Arrows {
	private static ArrowRegistry registry;

	/**
	 * Registers a new arrow with a specific data value.
	 * 
	 * @param data the data value
	 * @param arrow the arrow
	 */
	public static void register(int data, ElementArrow arrow) {
		registry.register(data, arrow);
	}

	/**
	 * Searches for a arrow with a specific data value.
	 * 
	 * @param data the data value
	 * @return the arrow
	 */
	public static ElementArrow find(int data) {
		return registry.find(data);
	}

	/**
	 * Gets a set with all the data values of the registered arrows.
	 * 
	 * @return the set
	 */
	public static Set<Short> keys() {
		return registry.keys();
	}

	/**
	 * Gets a iterator with all the arrows and data values.
	 * 
	 * @return the iterator
	 */
	public static Iterator<Entry<Short, ElementArrow>> iterator() {
		return registry.iterator();
	}

}