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

import com.google.common.base.Preconditions;

public class Property<T extends Serializable> {
	private final T defaultValue;
	private final String key;

	/**
	 * Creates a new property with a specific key.
	 * 
	 * @param key the key
	 */
	protected Property(String key) {
		this(key, null);
	}

	/**
	 * Creates a new property with a specific key and default value.
	 * 
	 * @param key the key
	 * @param defaultValue the default value
	 */
	protected Property(String key, T defaultValue) {
		Preconditions.checkNotNull(key);

		this.defaultValue = defaultValue;
		this.key = key;
	}

	/**
	 * Gets the key of the property.
	 * 
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Gets the default value of the property.
	 * 
	 * @return the default value
	 */
	public T getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Creates a new property with a specific key and default value.
	 * 
	 * @param key the key
	 * @param defaultValue the default value
	 */
	public static <T extends Serializable> Property<T> of(String key, T defaultValue) {
		return new Property<T>(key, defaultValue);
	}

	/**
	 * Creates a new property with a specific key.
	 * 
	 * @param key the key
	 */
	public static <T extends Serializable> Property<T> of(String key) {
		return new Property<T>(key);
	}

}