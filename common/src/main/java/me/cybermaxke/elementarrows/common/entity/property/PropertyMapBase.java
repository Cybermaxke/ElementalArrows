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
package me.cybermaxke.elementarrows.common.entity.property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

public class PropertyMapBase implements PropertyMap {
	protected final static long serialVersionUID = -1613924583300886267L;
	protected final Map<String, Serializable> map;

	protected PropertyMapBase(Map<String, Serializable> map) {
		this.map = map;
	}

	@Override
	public <T extends Serializable> boolean has(Property<T> key) {
		Preconditions.checkNotNull(key);
		return this.map.containsKey(key.getKey());
	}

	@Override
	public <T extends Serializable> T get(Property<T> key) {
		Preconditions.checkNotNull(key);
		String key0 = key.getKey();

		if (this.map.containsKey(key)) {
			return (T) this.map.get(key0);
		} else {
			return key.getDefaultValue();
		}
	}

	@Override
	public <T extends Serializable> T set(Property<T> key, T value) {
		Preconditions.checkNotNull(key);

		T old = this.get(key);
		this.map.put(key.getKey(), value);

		return old;
	}

	@Override
	public <T extends Serializable> T setIfAbsent(Property<T> key, T value) {
		String key0 = key.getKey();

		if (this.map.containsKey(key0)) {
			return (T) this.map.get(key0);
		}

		this.map.put(key0, value);
		return key.getDefaultValue();
	}

	@Override
	public <T extends Serializable> boolean remove(Property<T> key) {
		Preconditions.checkNotNull(key);
		return this.map.remove(key.getKey()) != null;
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public Map<String, Serializable> getMap() {
		return new HashMap<String, Serializable>(this.map);
	}

	@Override
	public Map<String, Serializable> getDelegate() {
		return this.map;
	}

}