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
package me.cybermaxke.elementalarrows.spout.plugin.utils;

import java.lang.reflect.Method;

import org.spout.api.component.entity.SceneComponent;
import org.spout.api.util.Parameter;

import org.spout.vanilla.component.entity.VanillaEntityComponent;

public class EntityUtils {
	private static Method SET_METADATA;

	private EntityUtils() {

	}

	/**
	 * Directly updating the position.
	 * @param scene
	 */
	public static void updateSnapshotPosition(SceneComponent scene) {
		try {
			Class<?> clazz = scene.getClass();

			Method method = clazz.getDeclaredMethod("copySnapshot", new Class[] {});
			method.setAccessible(true);
			method.invoke(scene, new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setting entity meta.
	 * @param entity
	 * @param parameters
	 */
	public static void setMetadata(VanillaEntityComponent entity, Parameter<?>... parameters) {
		try {
			SET_METADATA.invoke(entity, new Object[] { parameters });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static {
		for (Method m : VanillaEntityComponent.class.getDeclaredMethods()) {
			m.setAccessible(true);

			if (m.getName().equals("setMetadata")) {
				SET_METADATA = m;
			}
		}
	}
}