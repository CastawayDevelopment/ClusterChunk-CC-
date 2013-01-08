package com.CC.Commands.Party;

import com.CC.General.onStartup;
import com.CC.Party.Party;
import org.bukkit.entity.Player;

public class Join 
{
    private onStartup plugin;
    
    public Join(onStartup p)
    {
        this.plugin = p;
    }

    public void join(Player player, String partyName)
    {
        player.sendMessage("Join workin");
        Party party = plugin.getParties().getParty(partyName);
        if(party != null)
        {
            party.addMember(player);
        }
    }
}