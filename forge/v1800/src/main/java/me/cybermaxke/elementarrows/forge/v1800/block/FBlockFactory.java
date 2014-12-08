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
package me.cybermaxke.elementarrows.forge.v1800.block;

import java.util.Map;
import java.util.WeakHashMap;

import com.google.common.base.Preconditions;

import net.minecraft.block.Block;
import me.cybermaxke.elementarrows.common.block.BlockFactory;

public class FBlockFactory implements BlockFactory {
	private final Map<Block, FBlockType> blocks = new WeakHashMap<Block, FBlockType>();

	@Override
	public FBlockType typeById(String id) {
		Preconditions.checkNotNull(id);
		Block block = (Block) Block.blockRegistry.getObject(id);
		Preconditions.checkNotNull(block, "Unknown block type! (" + id + ")");

		return this.of(block);
	}

	@Override
	public FBlockType typeById(int internalId) {
		Block block = (Block) Block.blockRegistry.getObjectById(internalId);
		Preconditions.checkNotNull(block, "Unknown block type! (" + internalId + ")");

		return this.of(block);
	}

	public FBlockType of(Block block) {
		if (this.blocks.containsKey(block)) {
			return this.blocks.get(block);
		}
		
		FBlockType type = new FBlockType(block);
		this.blocks.put(block, type);

		return type;
	}

}