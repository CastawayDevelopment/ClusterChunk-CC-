/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands.relation;

import com.CC.General.User;
import com.CC.General.onStartup;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class Relations
{
	private final onStartup plugin;

	public Relations(onStartup plugin)
	{
		this.plugin = plugin;
	}

	public void list(Player player)
	{
		String[] empty = new String[0];
		User user = this.plugin.getUserManager().getUser(player);
		if (user == null)
		{
			player.sendMessage(ChatColor.RED + "Unable to fetch the user data");
			return;
		}
		List<String> enemies = user.getEnemies();
		player.sendMessage(ChatColor.GREEN+"Enemies: ("+enemies.size()+")");
		player.sendMessage(enemies.toArray(empty));
		
		List<String> friends = user.getFriends();
		player.sendMessage(ChatColor.GREEN+"Friend Requests (Pending and approved: ("+friends.size()+")");
		player.sendMessage(friends.toArray(empty));


	}
}
