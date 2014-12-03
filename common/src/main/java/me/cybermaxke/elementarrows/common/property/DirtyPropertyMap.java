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

public interface DirtyPropertyMap extends PropertyMap  {

	/**
	 * Gets whether the property key dirty is.
	 * 
	 * @param key the property key
	 * @return whether it's dirty
	 */
	<T extends Serializable> boolean isDirty(Property<T> key);

	/**
	 * Sets whether the property key dirty is.
	 * 
	 * @param key the property key
	 */
	<T extends Serializable> void setDirty(Property<T> key);

	/**
	 * Gets whether the property key dirty is and resets the state.
	 * 
	 * @param key the property key
	 * @return whether it's dirty
	 */
	<T extends Serializable> boolean getDirtyAndReset(Property<T> key);

	/**
	 * Gets a map with all the dirty values and keys.
	 * 
	 * @return the dirty map
	 */
	Map<String, Serializable> getDirtyMap();

	/**
	 * Gets a map with all the dirty values and keys and resets all the states.
	 * 
	 * @return the dirty map
	 */
	Map<String, Serializable> getDirtyMapAndReset();

}