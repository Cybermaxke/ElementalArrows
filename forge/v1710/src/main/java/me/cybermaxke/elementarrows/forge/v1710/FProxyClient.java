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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import me.cybermaxke.elementarrows.forge.v1710.entity.EntityElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityFactory;
import me.cybermaxke.elementarrows.forge.v1710.entity.FEntityFactoryClient;
import me.cybermaxke.elementarrows.forge.v1710.entity.render.RenderElementArrow;
import me.cybermaxke.elementarrows.forge.v1710.entity.render.RenderEntityIce;
import me.cybermaxke.elementarrows.forge.v1710.network.MessageInjectorClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FProxyClient extends FProxyCommon {

	@Override
	public void onInit(File file) {
		super.onInit(file);

		/**
		 * Initialize the client message injector.
		 */
		MessageInjectorClient injector = new MessageInjectorClient();
		injector.onInit();

		/**
		 * Register the custom arrow entity renderer.
		 */
		RenderingRegistry.registerEntityRenderingHandler(EntityElementArrow.class, new RenderElementArrow());

		/**
		 * Load the ice renderer.
		 */
		RenderEntityIce renderer = new RenderEntityIce();
		renderer.onInit();

		/**
		 * Remove the bow enchantments from the combat tab.
		 */
		CreativeTabs combat = CreativeTabs.tabCombat;
		EnumEnchantmentType[] types0 = combat.func_111225_m();

		if (types0 != null) {
			List<EnumEnchantmentType> types1 = new ArrayList<EnumEnchantmentType>();

			for (int i = 0; i < types0.length; i++) {
				if (types0[i] != EnumEnchantmentType.bow) {
					types1.add(types0[i]);
				}
			}

			combat.func_111229_a(types1.toArray(new EnumEnchantmentType[] {}));
		}
	}

	@Override
	protected FEntityFactory newEntities() {
		return new FEntityFactoryClient();
	}

}