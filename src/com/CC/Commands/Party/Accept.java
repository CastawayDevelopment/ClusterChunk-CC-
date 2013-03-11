package com.CC.Commands.Party;

import com.CC.General.onStartup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Accept
{

    private onStartup plugin;

    public Accept(onStartup plugin)
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
