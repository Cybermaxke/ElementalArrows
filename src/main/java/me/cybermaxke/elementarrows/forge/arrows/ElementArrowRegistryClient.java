package me.cybermaxke.elementarrows.forge.arrows;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowInitEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ElementArrowRegistryClient extends ElementArrowRegistry {

	@Override
	public void register(int data, ElementArrow arrow) {
		super.register(data, arrow);

		/**
		 * Initialize on the client.
		 */
		arrow.onClientInit(new ArrowInitEvent(EArrowMod.mod.itemArrow, EArrowMod.mod.itemBow, data));
	}

}