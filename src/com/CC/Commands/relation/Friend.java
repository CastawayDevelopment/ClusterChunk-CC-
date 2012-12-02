/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands.relation;

import com.CC.General.User;
import com.CC.General.onStartup;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
			player1.sendMessage(ChatColor.RED + "Cant add yourself to your own friends list");
			return;
		}
		User user1 = this.plugin.getUserManager().getUser(player1);
		Player p2 = Bukkit.getPlayer(player2);

		if (p2 == null)
		{
			player1.sendMessage(ChatColor.RED + "This player is offline!");
			return;
		}

		// To fix case isues
		player2 = p2.getName();

		User user2 = this.plugin.getUserManager().getUser(p2);
		if (user1 == null || user2 == null)
		{
			player1.sendMessage(ChatColor.RED + "Something went wrong while accessing the friend list of the player you specified");
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
				player1.sendMessage(ChatColor.RED + player2 + " is already on your friends list");
			}
			else
			{
				if (user2.getEnemies().contains(playerName))
				{
					player1.sendMessage(ChatColor.RED + "Unable to send friend request because youre on his enemy list, however, he is removed from your list!");
				}
				else
				{
					player1.sendMessage(ChatColor.GREEN + "You resended you're friend request to " + player2 + "!");
					/*
					 * Resend friend request
					 */
					p2.sendMessage(ChatColor.GOLD + playerName + " has sent you a friend request, do /friend " + playerName + " to accept it");
				}
			}

		}
		else
		{
			/*
			 * New friend request
			 */
			addToList(user1.getFriends(), player2);
			player1.sendMessage(ChatColor.GREEN + "You has sended a friend request to " + player2 + "!");
			p2.sendMessage(ChatColor.GOLD + playerName + " has send you're a friend request, do /friend " + playerName + " to accept it");

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
