package com.CC.Commands.Party;

import com.CC.General.onStartup;
import com.CC.Party.Party;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Versus
{

    public onStartup plugin;

    public Versus(onStartup instance)
    {
        this.plugin = instance;
    }
    
    public void execute(Player player, String other)
    {
        Party thisP = this.plugin.getParties().getParty(player);
        Party otherP = this.plugin.getParties().getParty(other);
        if(thisP != null && thisP.equals(otherP))
        {
            thisP.versus(otherP);
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
