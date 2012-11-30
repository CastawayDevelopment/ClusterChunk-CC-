/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands;

import com.CC.Commands.relation.Foe;
import com.CC.Commands.relation.Friend;
import com.CC.Commands.relation.Relations;
import com.CC.General.onStartup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Manages the relation between users using /enemy and /friend
 * TODO: add cooldown
 * @author Fernando
 */
public class RelationCommand implements CommandExecutor
{
	private final onStartup plugin;
	private final Foe enemy;
	private final Friend friend;
	private final Relations relations;

	public RelationCommand(onStartup plugin)
	{
		this.plugin = plugin;
		enemy = new Foe(plugin);
		friend = new Friend(plugin);
		relations = new Relations(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] arg)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("friend"))
			{
				if (arg.length == 1)
				{
					friend.add(player, arg[0]);
					return true;
				}
				return false;

			}
			else if (cmd.getName().equalsIgnoreCase("enemy"))
			{
				if (arg.length == 1)
				{
					enemy.add(player, arg[0]);
					return true;
				}
				return false;
			}
			else if (cmd.getName().equalsIgnoreCase("relations"))
			{
				if (arg.length == 0)
				{
					relations.list(player);
					return true;
				}
				return false;
			}
			return false;

		}
		else
		{
			sender.sendMessage("Sorry, we didn't added a way for the console to add/remove friends and enemies for now, But why you should have enemies?");
			return true;
		}
	}
}
