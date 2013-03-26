package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Accept
{

    private ClusterChunk plugin;

    public Accept(ClusterChunk plugin)
    {
        this.plugin = plugin;
    }

    public void accept(String partyName, Player invited)
    {

        try
        {
            plugin.getParties().getParty(partyName).addMember(invited);
        }
        catch (Exception e)
        {
            invited.sendMessage(ChatColor.RED + "The party you are trying to join does not exist");
        }
    }
}
