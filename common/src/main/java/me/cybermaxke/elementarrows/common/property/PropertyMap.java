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
import java.util.Map;

public interface PropertyMap extends Serializable {

	/**
	 * Gets the value of the property key.
	 * 
	 * @param key the attribute key
	 * @return the value
	 */
	<T extends Serializable> T get(Property<T> key);

	/**
	 * Sets the value of the property key and returns the old value.
	 * 
	 * @param key the property key
	 * @param value the value
	 * @return the old value
	 */
	<T extends Serializable> T set(Property<T> key, T value);

	/**
	 * Sets the value of the property key if absent and
	 * otherwise will it return the current value.
	 * 
	 * @param key the property key
	 * @param value the value
	 * @return the current value
	 */
	<T extends Serializable> T setIfAbsent(Property<T> key, T value);

	/**
	 * Gets whether the property key exists in this property map.
	 * 
	 * @param key the property key
	 * @return whether it exists
	 */
	<T extends Serializable> boolean has(Property<T> key);

	/**
	 * Removes the value of the property key and returns whether
	 * there actually was a value.
	 * 
	 * @param key the property key
	 * @return whether a value existed
	 */
	<T extends Serializable> boolean remove(Property<T> key);

	/**
	 * Gets the underlying map with all the keys and values.
	 * 
	 * @return the map
	 */
	Map<String, Serializable> getDelegate();

	/**
	 * Gets a map with all the keys and values.
	 * 
	 * @return the map
	 */
	Map<String, Serializable> getMap();

	/**
	 * Gets whether the property map empty is.
	 * 
	 * @return is empty
	 */
	boolean isEmpty();

}