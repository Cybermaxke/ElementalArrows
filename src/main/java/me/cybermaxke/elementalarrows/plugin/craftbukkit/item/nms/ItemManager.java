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
package me.cybermaxke.elementalarrows.plugin.craftbukkit.item.nms;

import org.bukkit.inventory.ItemStack;

import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.MaterialData;

import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;

import net.minecraft.server.v1_5_R2.Item;

public class ItemManager {

	public ItemManager() {
		int id = Item.BOW.id;
		Item.byId[id] = null;
		Item.byId[id] = new ItemElementalBow(5);
	}

	public static ArrowMaterial getMaterial(ItemStack item) {
		if (item == null) {
			return null;
		}

		SpoutItemStack i = new SpoutItemStack(item);
		if (i.isCustomItem()) {
			return (ArrowMaterial) (i.getMaterial() instanceof ArrowMaterial ? i.getMaterial() : null);
		}

		org.bukkit.inventory.ItemStack is = item.clone();
		int d = is.getDurability();
		if (d < 1000) {
			return null;
		}

		CustomItem m = MaterialData.getCustomItem(d);
		if (m == null || m instanceof ArrowMaterial) {
			return (ArrowMaterial) m;
		}

		return null;
	}
}