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
package me.cybermaxke.elementarrows.common.property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.Preconditions;

public class DirtyPropertyHashMap extends PropertyHashMap implements DirtyPropertyMap {
	private final static long serialVersionUID = 4065133922738223781L;
	private final transient Set<String> dirty = new HashSet<String>();

	public DirtyPropertyHashMap(Map<String, Serializable> map) {
		super(map);
	}

	public DirtyPropertyHashMap() {
		super();
	}

	@Override
	public <T extends Serializable> boolean isDirty(Property<T> key) {
		Preconditions.checkNotNull(key);
		return this.dirty.contains(key.getKey());
	}

	@Override
	public <T extends Serializable> void setDirty(Property<T> key) {
		Preconditions.checkNotNull(key);
		this.dirty.add(key.getKey());
	}

	@Override
	public <T extends Serializable> boolean getDirtyAndReset(Property<T> key) {
		Preconditions.checkNotNull(key);
		return this.dirty.remove(key.getKey());
	}

	@Override
	public <T extends Serializable> T set(Property<T> key, T value) {
		T old = super.set(key, value);
		if (!Objects.deepEquals(old, value)) {
			this.dirty.add(key.getKey());
		}
		return old;
	}

	@Override
	public <T extends Serializable> T setIfAbsent(Property<T> key, T value) {
		String key0 = key.getKey();
		if (!this.map.containsKey(key0)) {
			this.dirty.add(key0);
		}
		return super.set(key, value);
	}

	@Override
	public <T extends Serializable> boolean remove(Property<T> key) {
		boolean flag = super.remove(key);
		if (flag) {
			this.dirty.add(key.getKey());
		}
		return flag;
	}

	@Override
	public Map<String, Serializable> getDirtyMap() {
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		for (String key : this.dirty) {
			map.put(key, (Serializable) this.map.get(key));
		}
		return map;
	}

	@Override
	public Map<String, Serializable> getDirtyMapAndReset() {
		Map<String, Serializable> map = this.getDirtyMap();
		this.dirty.clear();
		return map;
	}

}