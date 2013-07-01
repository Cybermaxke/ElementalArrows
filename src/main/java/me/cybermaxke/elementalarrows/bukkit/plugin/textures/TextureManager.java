/**
 * 
 * This software is part of the ElementalArrows
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.bukkit.plugin.textures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.plugin.java.JavaPlugin;

import org.getspout.spoutapi.SpoutManager;

public class TextureManager {
	private static final String REGEX = ".*\\.(txt|yml|xml|png|jpg|ogg|midi|wav|zip)$";
	private static File folder;

	public TextureManager(JavaPlugin plugin) {
		try {
			Method m = JavaPlugin.class.getDeclaredMethod("getFile", new Class[] {});
			m.setAccessible(true);

			File jfile = (File) m.invoke(plugin, new Object[] {});
			JarFile jar = new JarFile(jfile);

			File dfolder = plugin.getDataFolder();
			if (!dfolder.exists()) {
				dfolder.mkdirs();
			}

			folder = new File(dfolder + File.separator + "Textures");
			if (!folder.exists()) {
				folder.mkdirs();
			}

			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();

				File file = new File(dfolder, name);
				if (!isInFolder(folder, file)) {
					continue;
				}

				if (!file.exists() && name.matches(REGEX)) {
					InputStream is = jar.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(file);

					while (is.available() > 0) {
						fos.write(is.read());
					}

					fos.close();
					is.close();
				}
			}

			for (File file : folder.listFiles()) {
				if (file.getName().matches(REGEX)) {
					SpoutManager.getFileManager().addToCache(plugin, file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean isInFolder(File folder, File file) {
		File parentfolder = file.getParentFile();
		while (parentfolder != null) {
			if (parentfolder.equals(folder)) {
				return true;
			}
			parentfolder = parentfolder.getParentFile();
		}
		return false;
	}

	public static File getTexture(String name) {
		return new File(folder, name);
	}
}