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
package me.cybermaxke.elementarrows.forge.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class UtilFields {

	private UtilFields() {}

	/**
	 * Finds the object of a declared field with a specific type in the
	 * target class. With a specific index.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @param targetObject the target object you want to retrieve the object of
	 * @param index the field index
	 * @return the object
	 */
	public static Object findFieldAndGet(Class<?> target, Class<?> fieldType, Object targetObject, int index) {
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				if (index == 0) {
					try {
						return field.get(targetObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				index--;
			}
		}
		return null;
	}

	/**
	 * Finds the objects of all declared fields with a specific type in the
	 * target class. With a specific index.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @param targetObject the target object you want to retrieve the object of
	 * @return the objects
	 */
	public static Object[] findFieldsAndGet(Class<?> target, Class<?> fieldType, Object targetObject) {
		List<Object> list = new ArrayList<Object>();
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				try {
					list.add(field.get(targetObject));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list.toArray(new Object[] {});
	}

	/**
	 * Finds a declared field of a specific type in the target class. With a specific index.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @param index the field index
	 * @return the field
	 */
	public static Field findField(Class<?> target, Class<?> fieldType, int index) {
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				if (index == 0) {
					return field;
				}
				index--;
			}
		}
		return null;
	}

	/**
	 * Finds all the declared fields of a specific type in the target class.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @return the fields
	 */
	public static Field[] findFields(Class<?> target, Class<?> fieldType) {
		List<Field> list = new ArrayList<Field>();
		for (Field field : target.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType().isAssignableFrom(fieldType)) {
				list.add(field);
			}
		}
		return list.toArray(new Field[] {});
	}

}