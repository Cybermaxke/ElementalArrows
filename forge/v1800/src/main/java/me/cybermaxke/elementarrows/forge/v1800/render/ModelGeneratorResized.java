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
package me.cybermaxke.elementarrows.forge.v1800.render;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.google.common.base.Preconditions;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWitch;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGeneratorResized {
	private final Map<ModelBase, ModelBase> cache = new WeakHashMap<ModelBase, ModelBase>();

	/**
	 * The extra offset of the model.
	 */
	private final float offset;

	public ModelGeneratorResized(float offset) {
		this.offset = offset;
	}

	/**
	 * Gets the resized model for the model.
	 * 
	 * @param model the model
	 * @return the resized model
	 */
	public ModelBase get(ModelBase model) {
		Preconditions.checkNotNull(model);
		ModelBase model0 = this.cache.get(model);

		if (model0 != null) {
			/**
			 * Update the model positions, etc.
			 */
			newUpdate(model0, model);

			return model0;
		}

		model0 = newModel(model);

		/**
		 * Get all the renderers of the new model.
		 */
		List<ModelRenderer> renders = model0.boxList;

		for (ModelRenderer render : renders) {
			List<ModelBox> boxes0 = render.cubeList;
			List<ModelBox> boxes1 = new ArrayList<ModelBox>();

			for (ModelBox box0 : boxes0) {
				float x0 = box0.posX1 - this.offset;
				float y0 = box0.posY1 - this.offset;
				float z0 = box0.posZ1 - this.offset;

				int x1 = (int) ((box0.posX2 - box0.posX1) + this.offset * 2f);
				int y1 = (int) ((box0.posY2 - box0.posY1) + this.offset * 2f);
				int z1 = (int) ((box0.posZ2 - box0.posZ1) + this.offset * 2f);

				int tw = (int) render.textureWidth;
				int th = (int) render.textureHeight;

				boxes1.add(new ModelBox(render, tw, th, x0, y0, z0, x1, y1, z1, 0f));
			}

			render.cubeList = boxes1;
		}

		/**
		 * Update the model positions, etc.
		 */
		newUpdate(model0, model);

		this.cache.put(model, model0);
		return model0;
	}

	static void newUpdate(ModelBase model0, ModelBase model1) {
		List<ModelRenderer> renders0 = model0.boxList;
		List<ModelRenderer> renders1 = model1.boxList;

		model0.isChild = model1.isChild;
		model0.isRiding = model1.isRiding;
		model0.swingProgress = model1.swingProgress;

		for (int i = 0; i < renders0.size(); i++) {
			ModelRenderer render0 = renders0.get(i);
			ModelRenderer render1 = renders1.get(i);

			render0.isHidden = render1.isHidden;
			render0.mirror = render1.mirror;
			render0.showModel = render1.showModel;
			render0.rotationPointX = render1.rotationPointX;
			render0.rotationPointY = render1.rotationPointY;
			render0.rotationPointZ = render1.rotationPointZ;
			render0.rotateAngleX = render1.rotateAngleX;
			render0.rotateAngleY = render1.rotateAngleY;
			render0.rotateAngleZ = render1.rotateAngleZ;
			render0.textureHeight = render1.textureHeight;
			render0.textureWidth = render1.textureWidth;
		}
	}

	static ModelBase newModel(ModelBase model) {
		Class<?> type = model.getClass();

		try {
			if (type == ModelVillager.class) {
				return new ModelVillager(0f);
			} else if (type == ModelWitch.class) {
				return new ModelWitch(0f);
			}

			Constructor<?> c = type.getConstructor();
			c.setAccessible(true);

			return (ModelBase) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}