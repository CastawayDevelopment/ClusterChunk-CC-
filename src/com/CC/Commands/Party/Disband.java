package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import com.CC.Party.PartyStorage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Disband
{

    private PartyStorage partymanager;

    public Disband(ClusterChunk plugin)
    {
        partymanager = plugin.getParties();

    }

    public void disbandParty(Player from)
    {
        if (partymanager.getParty(from) != null)
        {
            partymanager.disbandParty(from, partymanager.getParty(from));
        }
        else
        {
            from.sendMessage(ChatColor.RED + "You are not currently a leader of any parties");
        }



    }
}
