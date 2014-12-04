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
package me.cybermaxke.elementarrows.common.json;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

public class Json {
	private static JsonFactory factory = new JsonFactoryBase();

	/**
	 * Gets the gson instance.
	 * 
	 * @return the gson
	 */
	public static Gson getGson() {
		return factory.getGson();
	}

	/**
	 * Reads a object of a specific type from a file with json content.
	 * 
	 * @param is the input stream
	 * @param type the class type
	 * @return the object
	 * @throws IOException
	 */
	public static <T> T of(File file, Class<T> type) throws IOException {
		return factory.of(file, type);
	}

	/**
	 * Reads a object of a specific type from a file with json content.
	 * 
	 * @param is the input stream
	 * @param type the class type
	 * @param defaultValue the default value
	 * @return the object
	 * @throws IOException
	 */
	public static <T, V extends T> T of(File file, Class<T> type, V defaultValue) throws IOException {
		return factory.of(file, type, defaultValue);
	}

}