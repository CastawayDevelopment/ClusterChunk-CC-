/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands.relation;

import com.CC.General.User;
import com.CC.General.ClusterChunk;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

/**
 *
 * @author Fernando
 */
public class Foe
{
	private final ClusterChunk plugin;

	public Foe(ClusterChunk plugin)
	{
		this.plugin = plugin;
	}

	private void removeFriendBindings(User user, User target)
	{
            Player userPlayer = user.getPlayer();
            Player targetPlayer = target.getPlayer();
            
            if (user.getFriends().contains(targetPlayer.getName()))
            {
                user.getFriends().remove(targetPlayer.getName());
                userPlayer.sendMessage(new StringBuilder(RED.toString()).append(targetPlayer.getName()).append(" has been removed from your friends list!").toString());
            }
            
            if (target.getFriends().contains(userPlayer.getName()))
            {
                target.getFriends().remove(userPlayer.getName());
                targetPlayer.sendMessage(new StringBuilder(RED.toString()).append(userPlayer.getName()).append(" has been removed from your friends list!").toString());
            }
	}

	public void add(Player player, String targetName)
	{
                String playerName = player.getName();
		if (playerName.equalsIgnoreCase(targetName))
		{
			player.sendMessage(new StringBuilder(RED.toString()).append("Cant add yourself to your own enemy list").toString());
			return;
		}
		User user = this.plugin.getUserManager().getUser(player);
		Player targetPlayer = Bukkit.getPlayer(targetName);

                if(targetPlayer == null)
                {
                    player.sendMessage(new StringBuilder(RED.toString()).append("This player is offline!").toString());
                    return;
                }
                
		// To fix case isues
		targetName = targetPlayer.getName();

		User target = this.plugin.getUserManager().getUser(targetPlayer);
                
                // Sanity check for already enemied players
                if(user.getEnemies().contains(targetName) && target.getEnemies().contains(playerName))
                {
                    player.sendMessage(new StringBuilder(RED.toString()).append("You are already enemies. Fight to your hearts content").toString());
                    return;
                }
                
		if (user == null)
		{
			player.sendMessage(new StringBuilder(RED.toString()).append("Something went wrong while accessing the enemy list of the player you specified").toString());
			return;
		}
		if (target != null)
		{
			// Remove any friendly status
			removeFriendBindings(user, target);
		}
		user.addEnemy(targetName);
                target.addEnemy(playerName);
		player.sendMessage(new StringBuilder(RED.toString()).append("Player ").append(targetName).append("is now your enemy").toString());
		targetPlayer.sendMessage(new StringBuilder(RED.toString()).append("Player ").append(playerName).append("has declared you his/her enemy").toString());
	}
}
