package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import com.CC.Party.Party;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Versus
{

    public ClusterChunk plugin;

    public Versus(ClusterChunk instance)
    {
        this.plugin = instance;
    }
    
    public void execute(Player player, String other)
    {
        Party thisP = this.plugin.getParties().getParty(player);
        Party otherP = this.plugin.getParties().getParty(other);
        if(thisP != null && thisP.equals(otherP))
        {
            if(thisP.getMembers().size() == 4)
            {
                if(otherP.getMembers().size() == 4)
                {
                    if(thisP.allOnline())
                    {
                        if(otherP.allOnline())
                        {
                            thisP.versus(otherP, plugin);
                        }
                        else
                        {
                            player.sendMessage(new StringBuilder(ChatColor.RED.toString()).append("The other party does not have sufficient member onlines!").toString());
                        }
                    }
                    else
                    {
                        player.sendMessage(new StringBuilder(ChatColor.RED.toString()).append("Your party does not have sufficient members online!").toString());
                    }
                }
                else
                {
                    player.sendMessage(new StringBuilder(ChatColor.RED.toString()).append("The other party does not have sufficient members!").toString());
                }
            }
            else
            {
                player.sendMessage(new StringBuilder(ChatColor.RED.toString()).append("Your party does not have sufficient members!").toString());
            }
        }
        else if(thisP == null)
        {
            player.sendMessage(new StringBuilder(ChatColor.RED.toString()).append("You have not joined a party!").toString());
        }
        else
        {
            player.sendMessage(new StringBuilder(ChatColor.RED.toString()).append("Party '").append(other).append("' does not exist!").toString());
        }
    }
}
