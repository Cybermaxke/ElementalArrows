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
package me.cybermaxke.elementarrows.forge.network;

import me.cybermaxke.elementarrows.forge.EPlayers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public final class HandlerModInfo implements IMessageHandler<MessageModInfo, IMessage> {
	private final EPlayers players;

	public HandlerModInfo(EPlayers players) {
		this.players = players;
	}

	@Override
	public IMessage onMessage(MessageModInfo msg, MessageContext ctx) {
		this.players.onReceive(ctx.getServerHandler().playerEntity.getPersistentID(), msg);
		return null;
	}

}