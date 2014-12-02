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
package me.cybermaxke.elementarrows.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MapSerializer {

	/**
	 * Writes the map to a byte array.
	 * 
	 * @param map the data map
	 * @return the byte array
	 * @throws IOException
	 */
	public static byte[] toByteArray(Map<String, Serializable> map) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gos = new GZIPOutputStream(baos);
		ObjectOutputStream oos = new ObjectOutputStream(gos);

		oos.writeObject(map);
		oos.flush();
		oos.close();

		return baos.toByteArray();
	}

	/**
	 * Reads the map from the byte array.
	 * 
	 * @param bytes the byte array
	 * @return the data map
	 * @throws IOException
	 */
	public static Map<String, Serializable> fromByteArray(byte[] bytes) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		GZIPInputStream gis = new GZIPInputStream(bais);
		ObjectInputStream ois = new ObjectInputStream(gis);

		Map<String, Serializable> map = null;

		try {
			map = (Map<String, Serializable>) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ois.close();
		return map;
	}

}