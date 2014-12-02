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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ArrowRegistryBase implements ArrowRegistry {
	private final BiMap<ElementArrow, Short> map = HashBiMap.create();

	@Override
	public void register(int data, ElementArrow arrow) {
		short data0 = (short) data;

		Preconditions.checkState(arrow != null, "The arrow cannot be null!");
		Preconditions.checkState(data > 0, "The data value has to be greater then zero!");
		Preconditions.checkState(!this.map.containsKey(arrow), "The arrow is already registered!");
		Preconditions.checkState(!this.map.containsValue(data0), "Duplicate arrow id! (" + data + ")");

		this.map.put(arrow, data0);

		arrow.handle(new EventInitialize(data));
	}

	@Override
	public ElementArrow find(int data) {
		return this.map.inverse().get((short) data);
	}

	@Override
	public Set<Short> keys() {
		return new HashSet<Short>(this.map.values());
	}

	@Override
	public Iterator<Entry<Short, ElementArrow>> iterator() {
		return new HashMap<Short, ElementArrow>(this.map.inverse()).entrySet().iterator();
	}

}