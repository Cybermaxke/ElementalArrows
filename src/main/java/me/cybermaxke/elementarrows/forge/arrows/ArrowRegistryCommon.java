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
package me.cybermaxke.elementarrows.forge.arrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.cybermaxke.elementarrows.forge.EArrowMod;
import me.cybermaxke.elementarrows.forge.arrows.ElementArrow.ArrowInitEvent;

import com.google.common.base.Preconditions;

public class ArrowRegistryCommon {
	private final Map<ElementArrow, Short> arrows = new HashMap<ElementArrow, Short>();
	private final ElementArrow[] arrows0 = new ElementArrow[Short.MAX_VALUE];

	/**
	 * Registers a new elemental arrow with a specific data value.
	 * 
	 * @param data the data value
	 * @param arrow the arrow
	 */
	public void register(int data, ElementArrow arrow) {
		short data0 = (short) data;

		Preconditions.checkState(arrow != null, "The arrow cannot be NULL!");
		Preconditions.checkState(data > 0, "The data value has to be greater then ZERO!");
		Preconditions.checkState(!this.arrows.containsKey(arrow), "The arrow is already registered!");
		Preconditions.checkState(!this.arrows.containsValue(data0), "Duplicate arrow id! (" + data + ")");

		this.arrows.put(arrow, data0);
		this.arrows0[data0] = arrow;

		arrow.onInit(new ArrowInitEvent(EArrowMod.mod.itemArrow, EArrowMod.mod.itemBow, data));
	}

	/**
	 * Gets the elemental arrow using it's data value.
	 * 
	 * @param data the data value
	 * @return the arrow
	 */
	public ElementArrow fromData(int data) {
		return this.arrows0[data];
	}

	/**
	 * Gets a collection with all the registered data values.
	 * 
	 * @return the data values
	 */
	public Collection<Short> dataValues() {
		return this.arrows.values();
	}

	/**
	 * Gets a collection with all the registered data values.
	 * 
	 * @return the data values
	 */
	public Short[] dataValuesArray() {
		return this.arrows.values().toArray(new Short[] {});
	}

	/**
	 * Gets the array with all the arrows. Where the index is
	 * the data value of the arrow. The object can be NULL.
	 * 
	 * @return the arrows array
	 */
	public ElementArrow[] array() {
		return this.arrows0;
	}

}