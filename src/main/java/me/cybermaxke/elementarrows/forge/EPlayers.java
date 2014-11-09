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
package me.cybermaxke.elementarrows.forge;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.cybermaxke.elementarrows.forge.network.MessageModInfo;

public final class EPlayers {
	private final Map<UUID, PlayerModInfo> info = new HashMap<UUID, PlayerModInfo>();

	/**
	 * Gets the mod info for the players unique id.
	 * 
	 * @param uniqueId the players unique id
	 * @return the mod info
	 */
	public PlayerModInfo getInfoFor(UUID uniqueId) {
		if (this.info.containsKey(uniqueId)) {
			return this.info.get(uniqueId);
		}

		PlayerModInfo info = new PlayerModInfo();
		this.info.put(uniqueId, info);

		return info;
	}

	/**
	 * Called when the mod response is received.
	 * 
	 * @param uniqueId the players unique id
	 * @param response the response message
	 */
	public void onReceive(UUID uniqueId, MessageModInfo message) {
		PlayerModInfo info = this.getInfoFor(uniqueId);

		info.installed = true;
		info.version = message.version;
	}

	public static class PlayerModInfo {
		/**
		 * The version of the installed mod.
		 */
		private String version;

		/**
		 * Whether the mod is installed.
		 */
		private boolean installed;

		/**
		 * Gets whether the player the mod has. (Installed)
		 * 
		 * @return has the mod
		 */
		public boolean has() {
			return this.installed;
		}

		/**
		 * Gets the version of the installed mod.
		 * 
		 * @return the version
		 */
		public String version() {
			return this.version;
		}

	}
}