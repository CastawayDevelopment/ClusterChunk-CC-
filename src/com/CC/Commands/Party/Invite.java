package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.ClusterChunk;
import com.CC.Listeners.LobbyListener.*;

public class Invite
{

    private ClusterChunk plugin;

    public Invite(ClusterChunk p)
    {
        this.plugin = p;
    }

    public void invitePlayer(Player from, Player invited)
    {
        if (plugin.getParties().getParty(from) == null)
        {
            from.sendMessage(ChatColor.RED + "You are not currently in a party");
            return;
        }
        if (plugin.getLobbies().inLobby(invited))
        {
            from.sendMessage(ChatColor.RED + "Sorry, but you cannot invite a player that is queued into a game.");
            return;
        }
        if (plugin.getGameManager().isInGame(invited))
        {
            from.sendMessage(ChatColor.RED + "Sorry, but you cannot invite a player that is in a game.");
            return;
        }

        if (plugin.getParties().getParty(invited) != null)
        {
            from.sendMessage(ChatColor.RED + "Sorry, but '" + ChatColor.YELLOW + invited.getName() + ChatColor.RED + "' is already in a party.");
            return;
        }
        from.sendMessage(ChatColor.GREEN+"Invitation sent to "+ChatColor.YELLOW+invited.getName());
        plugin.getParties().getParty(from).invitePlayer(from, invited);

    }
}
