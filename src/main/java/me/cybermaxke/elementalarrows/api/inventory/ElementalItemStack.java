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
package me.cybermaxke.elementalarrows.api.inventory;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;

public class ElementalItemStack extends SpoutItemStack {

	public ElementalItemStack(int typeId, int amount, short data, ItemMeta meta) {
		super(typeId, amount, data, meta);
	}

	public ElementalItemStack(ItemStack item) {
		super(item);
	}

	public ElementalItemStack(int typeId) {
		super(typeId);
	}

	public ElementalItemStack(int typeId, short data) {
		super(typeId, data);
	}

	public ElementalItemStack(CustomItem item) {
		super(item);
	}

	public ElementalItemStack(CustomItem item, int amount) {
		super(item, amount);
	}

	public ElementalItemStack(CustomBlock block) {
		super(block);
	}

	public ElementalItemStack(CustomBlock block, int amount) {
		super(block, amount);
	}

	public ElementalItemStack(Material material) {
		super(material);
	}

	public ElementalItemStack(Material material, int amount) {
		super(material, amount);
	}

	@Override
	public Material getMaterial() {
		Material m = super.getMaterial();
		if (m instanceof CustomItem) {
			return m;
		}

		int d = this.getDurability();
		if (d < 1024) {
			return super.getMaterial();
		}

		CustomItem i = MaterialData.getCustomItem(d);
		return i == null ? super.getMaterial() : i;
	}

	@Override
	public boolean isCustomItem() {
		return this.getMaterial() instanceof CustomItem;
	}
}