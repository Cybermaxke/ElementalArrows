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
package me.cybermaxke.elementalarrows.spout.plugin.material;

import java.awt.Color;

import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkEffect;
import me.cybermaxke.elementalarrows.spout.api.data.firework.FireworkType;
import me.cybermaxke.elementalarrows.spout.api.material.ElementalItemMaterial;
import me.cybermaxke.elementalarrows.spout.plugin.component.entity.ElementFireworks;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.Action;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.block.BlockFace;

import org.spout.vanilla.material.VanillaMaterials;

/**
 * A firework to test the firework effects.
 */
@SuppressWarnings("unchecked")
public class ElementalFirework extends ElementalItemMaterial {

	public ElementalFirework(String name) {
		super(name, VanillaMaterials.FIREWORKS, 1);
	}

	@Override
	public void onInteract(Entity entity, Block block, Action type, BlockFace clickedface) {
		super.onInteract(entity, block, type, clickedface);
		if (type.equals(Action.RIGHT_CLICK)) {
			Point position = block.getPosition().add(0.0F, 1.0F, 0.0F);
			ElementFireworks fw = block.getWorld().createAndSpawnEntity(position, LoadOption.LOAD_ONLY, ElementFireworks.class).add(ElementFireworks.class);

			FireworkEffect fwe = new FireworkEffect(FireworkType.STAR);
			fwe.addColor(Color.YELLOW);
			fwe.addColor(Color.YELLOW.brighter());
			fwe.addColor(Color.YELLOW.darker());
			fwe.addColor(Color.ORANGE);
			fwe.addColor(Color.ORANGE.brighter());
			fwe.addColor(Color.ORANGE.darker());
			fwe.setFlicker(true);

			fw.addEffect(fwe);
			fw.setPower(2);
		}
	}
}