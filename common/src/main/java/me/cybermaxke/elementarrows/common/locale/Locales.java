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
package me.cybermaxke.elementarrows.common.locale;

import java.io.IOException;
import java.io.InputStream;

public class Locales {
	private static LocaleRegistry registry;

	/**
	 * Reads all the locale values from the input stream.
	 * 
	 * @param input the input stream
	 */
	public static void read(InputStream input) throws IOException {
		registry.read(input);
	}

	/**
	 * Reads all the locale values from the input stream.
	 * 
	 * @param input the input stream
	 * @param language the language
	 */
	public static void read(InputStream input, String language) throws IOException {
		registry.read(input, language);
	}

	/**
	 * Gets the translation for the path.
	 * 
	 * @param path the path
	 * @return the translation
	 */
	public static String get(String path) {
		return registry.get(path);
	}

	/**
	 * Gets the translation for the path with arguments.
	 * 
	 * @param path the path
	 * @param args the arguments
	 * @return the translation
	 */
	public static String get(String path, Object... args) {
		return registry.get(path, args);
	}

	/**
	 * Gets the translation for the path.
	 * 
	 * @param language the language
	 * @param path the path
	 * @return the translation
	 */
	public static String get(String language, String path) {
		return registry.get(language, path);
	}

	/**
	 * Gets the translation for the path with arguments.
	 * 
	 * @param language the language
	 * @param path the path
	 * @param args the arguments
	 * @return the translation
	 */
	public String get(String language, String path, Object... args) {
		return registry.get(language, path, args);
	}

}