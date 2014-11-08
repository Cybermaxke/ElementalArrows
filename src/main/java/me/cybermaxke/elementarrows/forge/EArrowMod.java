package me.cybermaxke.elementarrows.forge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLModDisabledEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "elementArrows", name = "ElementalArrows", version = "0.0.0")
public final class EArrowMod {

	@SidedProxy(
			clientSide = "me.cybermaxke.elementarrows.forge.ModInitClient",
			serverSide = "me.cybermaxke.elementarrows.forge.ModInitCommon"
	)
    public static ModInitCommon mod;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		EArrowMod.mod.onPreInit();
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