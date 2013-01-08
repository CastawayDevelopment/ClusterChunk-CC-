/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands.relation;

import com.CC.General.User;
import com.CC.General.onStartup;
import java.util.List;
import static org.bukkit.ChatColor.*;
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
			player.sendMessage(new StringBuilder(RED.toString()).append("Unable to fetch the user data").toString());
			return;
		}
		List<String> enemies = user.getEnemies();
		player.sendMessage(new StringBuilder(GREEN.toString()).append("Enemies: (").append(enemies.size()).append(")").toString());
		player.sendMessage(enemies.toArray(empty));
		
		List<String> friends = user.getFriends();
		player.sendMessage(new StringBuilder(GREEN.toString()).append("Friend Requests (Pending and approved: (").append(friends.size()).append(")").toString());
		player.sendMessage(friends.toArray(empty));


	}
}
