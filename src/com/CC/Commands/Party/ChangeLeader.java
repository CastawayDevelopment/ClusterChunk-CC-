package com.CC.Commands.Party;

import com.CC.General.onStartup;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class ChangeLeader
{

    private onStartup plugin;

    public ChangeLeader(onStartup instance)
    {
        plugin = instance;

    }

    public boolean ChangePartyLeader(Player sender, Player newOwner)
    {
        if (plugin.getParties().getParty(sender) == null)
        {
            sender.sendMessage(new StringBuilder(RED.toString()).append("You are not in a party").toString());
            return false;
        }
        else
        {
            return plugin.getParties().getParty(sender).declareLoader(sender, newOwner);
        }
    }
}
