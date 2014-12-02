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
package me.cybermaxke.elementarrows.spigot.v1710.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fields {

	/**
	 * Finds a declared field of a specific type in the target class. With a specific index.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @param index the field index starting in the specified order
	 * @return the field
	 */
	public static Field findField(Class<?> target, Class<?> fieldType, int index) {
		return findField(target, fieldType, index, false);
	}

	/**
	 * Finds a declared field of a specific type in the target class. With a specific index.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @param index the field index starting in the specified order
	 * @param reverse whether you want to reverse the order of the fields
	 * @return the field
	 */
	public static Field findField(Class<?> target, Class<?> fieldType, int index, boolean reverse) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(target.getDeclaredFields()));

		if (reverse) {
			Collections.reverse(fields);
		}
		
		for (Field field : fields.toArray(new Field[] {})) {
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
		return findFields(target, fieldType, 0);
	}

	/**
	 * Finds all the declared fields of a specific type in the target class.
	 * 
	 * @param target the target class
	 * @param fieldType the field type
	 * @param depth the depth you want to check for underlying classes their fields
	 * @return the fields
	 */
	public static Field[] findFields(Class<?> target, Class<?> fieldType, int depth) {
		List<Field> list = new ArrayList<Field>();
		while (target != null && target != Object.class) {
			for (Field field : target.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.getType().isAssignableFrom(fieldType)) {
					list.add(field);
				}
			}
			target = target.getSuperclass();
			if (depth != -1 && depth-- == 0) {
				break;
			}
		}
		return list.toArray(new Field[] {});
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

}