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
package me.cybermaxke.elementarrows.forge.v1800.arrow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import me.cybermaxke.elementarrows.common.arrow.ArrowRegistryBase;
import me.cybermaxke.elementarrows.common.arrow.ElementArrow;

@SideOnly(Side.CLIENT)
public class FArrowRegistryClient extends ArrowRegistryBase {

	@Override
	public void register(int data, ElementArrow arrow) {
		super.register(data, arrow);

		/**
		 * Create the resource location.
		 */
		ModelResourceLocation location = new ModelResourceLocation(arrow.getItemModel(), "inventory");

		/**
		 * Register the mesh to the item mesher.
		 */
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(Items.arrow, data, location);
	}

}