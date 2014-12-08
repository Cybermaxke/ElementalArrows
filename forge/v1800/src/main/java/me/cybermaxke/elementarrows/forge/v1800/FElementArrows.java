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
package me.cybermaxke.elementarrows.forge.v1800;

import java.io.File;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "elementArrows", name = "ElementalArrows", version = "1.0.0", acceptableRemoteVersions = "*")
public class FElementArrows {

	@SidedProxy(
			clientSide = "me.cybermaxke.elementarrows.forge.v1710.FProxyClient",
			serverSide = "me.cybermaxke.elementarrows.forge.v1710.FProxyCommon"
	)
	static FProxy instance;
	static File file;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		file = event.getSourceFile().getParentFile();
		instance.onPreInit(file);
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event) {
		instance.onInit(file);
	}

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		instance.onPostInit(file);
	}

}