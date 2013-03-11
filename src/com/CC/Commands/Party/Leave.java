package com.CC.Commands.Party;

import com.CC.General.onStartup;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Leave
{

    private onStartup plugin;

    public Leave(onStartup p)
    {
        this.plugin = p;
    }

    public void leave(Player player)
    {
        if (plugin.getParties().getParty(player) != null)
        {
            plugin.getParties().getParty(player).playerQuit(player);
        }
        else
        {
            player.sendMessage(ChatColor.RED + "You are not currently in a party");
        }

    }
}
