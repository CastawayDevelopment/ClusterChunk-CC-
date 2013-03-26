package com.CC.Party;

import java.util.HashMap;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class PartyStorage
{

    public HashMap<String, Party> parties = new HashMap<String, Party>();

    public Party getParty(String name)
    {
        // Can return null, be aware
        return parties.get(name);
    }

    public void createParty(String name, Player leader)
    {
        if (!parties.containsKey(name))
        {
            Party party = new Party(name, leader.getName());
            parties.put(name, party);
        }
        else
        {
            //player.sendMessage("party already exists");
        }
    }

    private void removeParty(String name)
    {
        if (parties.containsKey(name))
        {
            parties.remove(name);
            //player.sendMessage("party removed");
        }
    }

    public void disbandParty(Player from, Party party)
    {
        if (from.equals(party.getLeader()))
        {
            from.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully disbanded your party").toString());
            for (String s : party.getMembers())
            {
                if (Bukkit.getServer().getPlayer(s) != null)
                {
                    Player p = Bukkit.getServer().getPlayer(s);
                    p.sendMessage(new StringBuilder(RED.toString()).append("Your party has been disbanded").toString());
                }
            }
            removeParty(party.getName());
        }
        else
        {
            from.sendMessage(new StringBuilder(RED.toString()).append("Only the current leader can disband the party!").toString());
        }

    }
    //Can return null

    public Party getParty(Player player)
    {
        for (Party p : parties.values())
        {
            if (p.getMembers().contains(player.getName()))
            {
                return p;
            }

        }
        return null;

    }
}