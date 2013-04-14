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
        	StringBuilder players = new StringBuilder(ChatColor.GREEN + "Players: " + ChatColor.DARK_GREEN +
                                             party.getMembers().size() + ChatColor.GREEN + "/"+ClusterChunk.TEAM_SIZE+"\n");
                for(String member : party.getMembers()) players.append(member).append(", ");
                String ps = players.toString();
                if(ps.endsWith(", ")) ps = ps.substring(0, ps.length() - 2);
                player.sendMessage(ps);
        	player.sendMessage(ChatColor.GREEN + "Leader: " + party.getLeader().getName());
        	player.sendMessage("Pending invites");
        	player.sendMessage(ChatColor.GREEN + "======================================");
        }                                                                                 
    }
}
