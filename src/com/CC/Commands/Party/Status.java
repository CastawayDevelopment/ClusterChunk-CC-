package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.ClusterChunk;
import com.CC.Party.Party;

public class Status 
{
    private ClusterChunk plugin;
    
    public Status(ClusterChunk p)
    {
        this.plugin = p;
    }
    
    public void status(Player player)
    {
        if(plugin.getParties().getParty(player) != null){
        	Party party = plugin.getParties().getParty(player);
        	player.sendMessage(ChatColor.GREEN + "============" + ChatColor.DARK_GREEN + "[Party Status]" + ChatColor.GREEN + "============");
        	player.sendMessage(ChatColor.GREEN + "Players: " + ChatColor.DARK_GREEN + 
        			party.getMembers().size() + ChatColor.GREEN + "/4" 
        			+ party.getMembers().get(0) + ", " + party.getMembers().get(1) + ", " 
        			+ party.getMembers().get(2) + ", " + party.getMembers().get(3) + "");
        	player.sendMessage(ChatColor.GREEN + "Leader: " + party.getLeader().getName());
        	player.sendMessage("Pending invites");
        	player.sendMessage(ChatColor.GREEN + "======================================");
        }                                                                                 
    }
}
