package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import com.CC.Party.PartyStorage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Disband
{

    private PartyStorage parties;

    public Disband(ClusterChunk plugin)
    {
        parties = plugin.getParties();

    }

    public void disbandParty(Player from)
    {
        if (parties.getParty(from) != null)
        {
            parties.disbandParty(from, parties.getParty(from));
        }
        else
        {
            from.sendMessage(ChatColor.RED + "You are not currently a leader of any parties");
        }



    }
}
