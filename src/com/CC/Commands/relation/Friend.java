/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands.relation;

import com.CC.General.User;
import com.CC.General.onStartup;
import java.util.List;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

/**
 * 
 * @author Fernando
 */
public class Friend
{
	private final onStartup plugin;

	public Friend(onStartup plugin)
	{
		this.plugin = plugin;
	}

	public void add(Player player1, String player2)
	{
		if (player1.getName().equalsIgnoreCase(player2))
		{
			player1.sendMessage(new StringBuilder(RED.toString()).append("You can't add yourself to your own friends list").toString());
			return;
		}
		User user1 = this.plugin.getUserManager().getUser(player1);
		Player p2 = Bukkit.getPlayer(player2);

		if (p2 == null)
		{
			player1.sendMessage(new StringBuilder(RED.toString()).append("This player is offline!").toString());
			return;
		}

		// To fix case isues
		player2 = p2.getName();

		User user2 = this.plugin.getUserManager().getUser(p2);
		if (user1 == null || user2 == null)
		{
			player1.sendMessage(new StringBuilder(RED.toString()).append("Something went wrong while accessing the friend list of the player you specified").toString());
			return;
		}

		// Remove the enemy binding to fix bugs
		user1.getEnemies().remove(player2);

		String playerName = player1.getName();
		if (user1.getFriends().contains(playerName))
		{
			if (user2.getFriends().contains(playerName))
			{
				/*
				 * Other player is already on your friend list
				 */
				player1.sendMessage(new StringBuilder(RED.toString()).append(player2).append(" is already on your friends list").toString());
			}
			else
			{
				if (user2.getEnemies().contains(playerName))
				{
					player1.sendMessage(new StringBuilder(RED.toString()).append("Unable to send friend request because you're on his/her enemy list, however, they are removed from your list!").toString());
				}
				else
				{
					player1.sendMessage(new StringBuilder(GREEN.toString()).append("You have resent your friend request to ").append(player2).append("!").toString());
					/*
					 * Resend friend request
					 */
					p2.sendMessage(new StringBuilder(GOLD.toString()).append(playerName).append(" has sent you a friend request, do /friend ").append(playerName).append(" to accept it").toString());
				}
			}

		}
		else
		{
			/*
			 * New friend request
			 */
			addToList(user1.getFriends(), player2);
			player1.sendMessage(new StringBuilder(GREEN.toString()).append("You have sent a friend request to ").append(player2).append("!").toString());
			p2.sendMessage(new StringBuilder(GOLD.toString()).append(playerName).append(" has sent you a friend request, do /friend ").append(playerName).append(" to accept it").toString());

		}
	}

	private <T> void addToList(List<T> list, T value)
	{
		if (!list.contains(value))
		{
			list.add(value);
		}
	}
}
