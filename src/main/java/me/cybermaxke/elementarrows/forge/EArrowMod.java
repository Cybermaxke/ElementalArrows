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
package me.cybermaxke.elementarrows.forge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLModDisabledEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "elementArrows", name = "ElementalArrows", version = "1.1.1", acceptableRemoteVersions = "*")
public final class EArrowMod {

	@SidedProxy(
			clientSide = "me.cybermaxke.elementarrows.forge.ModInitClient",
			serverSide = "me.cybermaxke.elementarrows.forge.ModInitCommon"
	)
	public static ModInitCommon mod;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		EArrowMod.mod.onPreInit(event.getSuggestedConfigurationFile().getParentFile().getParentFile());
	}

	@EventHandler
	public void onDisable(FMLModDisabledEvent event) {
		EArrowMod.mod.onDisable();
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event) {
		EArrowMod.mod.onInit();
	}

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		EArrowMod.mod.onPostInit();
	}

}