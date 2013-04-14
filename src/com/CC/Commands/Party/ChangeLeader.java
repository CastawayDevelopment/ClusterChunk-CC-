package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class ChangeLeader
{

    private ClusterChunk plugin;

    public ChangeLeader(ClusterChunk instance)
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
            return plugin.getParties().getParty(sender).declareLeader(sender, newOwner);
        }
    }
}
