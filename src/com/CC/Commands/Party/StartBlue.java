package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.Arenas.Team;
import com.CC.General.onStartup;
import com.CC.Party.Party;

public class StartBlue
{

    private onStartup plugin;

    public StartBlue(onStartup p)
    {
        this.plugin = p;
    }

    public void start(Player player)
    {
        if (plugin.getParties().getParty(player) != null)
        {
            Party party = plugin.getParties().getParty(player);
            if (party.getLeader().equals(player))
            {
                if (party.getMembers().size() == 4)
                {
                    player.sendMessage(plugin.getLobbies().startGameParty(party, Team.BLUE));
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "There are not enough players in your party");
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "You are not a leader of this party");
            }
        }
        else
        {
            player.sendMessage(ChatColor.RED + "You are not in any parties");
        }
    }
}
