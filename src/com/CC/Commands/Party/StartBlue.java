package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.Enums.Team;
import com.CC.General.ClusterChunk;
import com.CC.Messages.MessageUtil;
import com.CC.Party.Party;
import com.CC.Party.PartyBattle;

public class StartBlue
{

    private ClusterChunk plugin;

    public StartBlue(ClusterChunk p)
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
                if (party.getMembers().size() == ClusterChunk.TEAM_SIZE)
                {
                    //player.sendMessage(plugin.getLobbies().startGameParty(party, Team.BLUE));
                    PartyBattle pb = new PartyBattle(party, null);
                    pb.setPreferredTeam(Team.BLUE);
                    this.plugin.queueVersus.add(pb);
                    this.plugin.queueBlue.add(ClusterChunk.PARTY);
                    party.broadcast(MessageUtil.parseWarning("Your party has been added to the blue team waiting list", new Object[]{}));
                }
                else
                {
                    player.sendMessage(MessageUtil.parseWarning("There are not enough players in your party", new Object[]{}));
                }
            }
            else
            {
                player.sendMessage(MessageUtil.parseWarning("You are not a leader of this party", new Object[]{}));
            }
        }
        else
        {
            player.sendMessage(MessageUtil.parseWarning("You are not in any parties", new Object[]{}));
        }
    }
}
