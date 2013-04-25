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
package me.cybermaxke.elementalarrows.plugin.cmd;

import me.cybermaxke.elementalarrows.api.config.ConfigHolder;
import me.cybermaxke.elementalarrows.api.material.ArrowMaterial;
import me.cybermaxke.elementalarrows.plugin.arrow.ArrowManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands implements CommandExecutor {
	private final Permission global = new Permission("elementalarrows.cmd", PermissionDefault.OP);
	private final Permission help = new Permission("elementalarrows.cmd.help", PermissionDefault.OP);
	private final Permission reload = new Permission("elementalarrows.cmd.reload", PermissionDefault.OP);

	private final JavaPlugin plugin;

	public Commands(JavaPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getCommand("ElementalArrows").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!this.hasPermission(sender, this.global)) {
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(this.plugin.getName() + ": Use '/ElementalArrows Help' to show the available commands.");
			return true;
		}

		if (args[0].equalsIgnoreCase("Help")) {
			if (!this.hasPermission(sender, this.help)) {
				return true;
			}

			sender.sendMessage("---------- " + this.plugin.getName() + " Help ----------");
			sender.sendMessage("'/ElementalArrows Help' - Show the available commands.");
			sender.sendMessage("'/ElementalArrows Reload' - Reload all the config files.");
			return true;
		}

		if (args[0].equalsIgnoreCase("Reload")) {
			if (!this.hasPermission(sender, this.reload)) {
				return true;
			}

			for (ArrowMaterial m : ArrowManager.getArrows()) {
				if (m instanceof ConfigHolder) {
					((ConfigHolder) m).reload();
				}
			}

			sender.sendMessage(this.plugin.getName() + ": The config files are succesfully reloaded.");
			return true;
		}

		sender.sendMessage(this.plugin.getName() + ": That command doesn't exist, use '/ElementalArrows Help' to show the available commands.");
		return true;
	}

	private boolean hasPermission(CommandSender sender, Permission perm) {
		if (sender.hasPermission(perm)) {
			return true;
		}
		sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command!");
		return false;
	}
}