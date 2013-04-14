package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import com.CC.Party.Party;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class Create
{

    private ClusterChunk plugin;

    public Create(ClusterChunk p)
    {
        this.plugin = p;
    }

    public void create(Player player, String partyName)
    {
        if(plugin.getParties().getParty(player) != null)
        {
            player.sendMessage(new StringBuilder(RED.toString()).append("You already joined a party. Use ").append(YELLOW).append("/party leave").append(RED).append(" to leave your current party").toString());
            return;
        }
        
        Party created = plugin.getParties().createParty(partyName, player);
        if (created != null)
        {
            player.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully created the party ").append(DARK_GREEN).append(partyName).toString());
        }
        else
        {
            player.sendMessage(new StringBuilder(RED.toString()).append("A party with that name already exists. Please choose another.").toString());
        }

    }
}