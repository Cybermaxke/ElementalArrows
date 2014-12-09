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
package me.cybermaxke.elementarrows.forge.v1710;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions("me.cybermaxke.elementarrows.forge.v1710.render.RenderClassTransformer")
public class FLoadingPlugin implements IFMLLoadingPlugin {

	/**
	 * Whether the code is obfuscated.
	 */
	public static boolean Obfuscated;

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { "me.cybermaxke.elementarrows.forge.v1710.render.RenderClassTransformer" };
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> arg0) {
		/**
		 * Why do we need to negate the boolean?
		 */
		Obfuscated = (Boolean) arg0.get("runtimeDeobfuscationEnabled");
	}

}