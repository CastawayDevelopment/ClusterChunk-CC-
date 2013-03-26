/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Commands.relation;

import com.CC.General.User;
import com.CC.General.ClusterChunk;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 
 * @author Fernando & DarkSeraphim
 */
public class Friend
{
	private final ClusterChunk plugin;

        private final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> requests = new ConcurrentHashMap<String, CopyOnWriteArrayList<String>>();
        
	public Friend(ClusterChunk plugin)
	{
		this.plugin = plugin;
	}

	public void add(Player player, final String targetName)
	{
            if (player.getName().equalsIgnoreCase(targetName))
            {
                    player.sendMessage(new StringBuilder(RED.toString()).append("You can't add yourself to your own friends list").toString());
                    return;
            }
            User user = this.plugin.getUserManager().getUser(player);
            Player targetPlayer = Bukkit.getPlayer(targetName);

            if (targetPlayer == null)
            {
                    player.sendMessage(new StringBuilder(RED.toString()).append("This player is offline!").toString());
                    return;
            }

            // To fix case isues. Might be added later in case we switch to Bukkit.matchPlayer(String)
            //targetName = targetPlayer.getName();
            String playerName = player.getName();

            User target = this.plugin.getUserManager().getUser(targetPlayer);
            if (user == null || target == null)
            {
                    player.sendMessage(new StringBuilder(RED.toString()).append("Something went wrong while accessing the friend list of the player you specified").toString());
                    return;
            }

            // No sudden friends
            if(user.getEnemies().contains(targetName) || target.getEnemies().contains(playerName))
            {
                player.sendMessage(new StringBuilder(RED.toString()).append("You cannot just friend your enemies like that. First make up!").toString());
                return;
            }
            
            if(user.getFriends().contains(targetName) && target.getFriends().contains(playerName))
            {
                // Other player is already on your friend list
                player.sendMessage(new StringBuilder(RED.toString()).append(targetName).append(" is already your friend").toString());
            }
            
            final List<String> curReq = getOpenRequests(playerName);
            List<String> curReqOther = getOpenRequests(targetName);
            

            if (curReqOther.contains(playerName))
            {
                // Resend friend request
                targetPlayer.sendMessage(new StringBuilder(GREEN.toString()).append(playerName).append(" has accepted your friend request").toString());
                targetPlayer.sendMessage(new StringBuilder(GREEN.toString()).append("You accepted ").append(targetName).append("'s friend request").toString());
                user.addFriend(targetName);
                target.addFriend(playerName);
            }
            else
            {
                //New friend request
                curReq.add(targetName);
                player.sendMessage(new StringBuilder(GREEN.toString()).append("You have sent a friend request to ").append(targetName).append("!").toString());
                targetPlayer.sendMessage(new StringBuilder(GOLD.toString()).append(playerName).append(" has sent you a friend request, do /friend ").append(playerName).append(" to accept it").toString());
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        curReq.remove(targetName);
                    }
                                                  // min  sec  tick
                }.runTaskLaterAsynchronously(plugin,  02 * 60 * 20L);
            }
	}
        
        /*
         * Anyone?
         */
        public List<String> getOpenRequests(String player)
        {
            if(!this.requests.containsKey(player))
            {
                this.requests.put(player, new CopyOnWriteArrayList<String>());
            }
            return this.requests.get(player);
        }
}
