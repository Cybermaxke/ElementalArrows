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
package me.cybermaxke.elementarrows.common.util.lookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBiMap;

public class IntToString {
	private final BiMap<StringEntry, Integer> map = HashBiMap.create();

	/**
	 * Loads the entries from the input stream.
	 * 
	 * @param is the input stream
	 * @throws IOException
	 */
	public void load(InputStream is) throws IOException {
		Preconditions.checkNotNull(is);

		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		while ((line = br.readLine()) != null) {
			if (line.charAt(0) != '#') {
				int index = line.indexOf('=');

				String part0 = line.substring(0, index);
				String part1 = line.substring(index + 1, line.length());

				int id;

				try {
					id = Integer.parseInt(part0);
				} catch (NumberFormatException e) {
					throw new IOException("Error while reading line! (" + line + ")");
				}

				this.map.put(new StringEntry(part1), id);
			}
		}

		br.close();
	}

	/**
	 * Gets the name assigned to this id.
	 * 
	 * @param id the integer id
	 * @return the name
	 */
	public String get(int id) {
		StringEntry entry = this.map.inverse().get(id);

		if (entry != null) {
			return this.transform(entry.value);
		}

		return null;
	}

	/**
	 * Gets the integer id assigned to this name.
	 * 
	 * @param name the name
	 * @return the integer id
	 */
	public int get(String name) {
		Preconditions.checkNotNull(name);

		return this.map.containsKey(name) ? this.map.get(new StringEntry(name)): -1;
	}

	/**
	 * Gets a iterator that iterates through all the entries.
	 * 
	 * @return the iterator
	 */
	public Iterator<Entry<Integer, String>> entries() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		for (Entry<StringEntry, Integer> entry : this.map.entrySet()) {
			map.put(entry.getValue(), entry.getKey().value);
		}

		return map.entrySet().iterator();
	}

	/**
	 * Gets a set with all the integers.
	 * 
	 * @return the set
	 */
	public Set<Integer> intSet() {
		return new HashSet<Integer>(this.map.values());
	}

	/**
	 * Gets a set with all the strings.
	 * 
	 * @return the set
	 */
	public Set<String> stringSet() {
		return new HashSet<String>(Collections2.transform(this.map.keySet(), new Function<StringEntry, String>() {

			@Override
			public String apply(StringEntry input) {
				return input.value;
			}

		}));
	}

	/**
	 * Transforms the string that will be used for lookup.
	 * 
	 * @param string the string
	 * @return the transformed string
	 */
	protected String transform(String string) {
		return string.toLowerCase();
	}

	class StringEntry {
		String value;

		/**
		 * Creates a new string entry.
		 * 
		 * @param value the value
		 */
		StringEntry(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		@Override
		public int hashCode() {
			return transform(this.value).hashCode();
		}

	}

}