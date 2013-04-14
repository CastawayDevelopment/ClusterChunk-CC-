package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import com.CC.Messages.MessageUtil;
import org.bukkit.entity.Player;

public class Accept
{

    private ClusterChunk plugin;

    public Accept(ClusterChunk plugin)
    {
        this.plugin = plugin;
    }

    public void accept(String partyName, Player invited)
    {

        try
        {
            plugin.getParties().getParty(partyName).addMember(invited);
        }
        catch (Exception e)
        {
            invited.sendMessage(MessageUtil.parseWarning("The party <p>%s<r>does not exist", new Object[]{partyName}));
        }
    }
}
