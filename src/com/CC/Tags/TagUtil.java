package com.CC.Tags;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import org.bukkit.entity.Player;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class TagUtil
{

    /*
    *   Takes all players in a game and set the tag
    *    to the corresponding team colour
    *   @post the tag changed for all players in the game
    **/
    public static void setTeamTag(List<String> players)
    {
        for(String player : players)
        {
            Player p = Bukkit.getPlayer(player);
            if(p == null)
            {
                continue;
            }
            String name = p.getName();
            if(/*Implement team logic*/)
            {
                name = ChatColor.RED + name; // For red
            }
            else
            {
                name = ChatColor.BLUE + name; // For blue
            }
            
            setTag(p, name, players);
        }
    }

    /*
    *   Set the player's tag for the rest of the group
    *    to the corresponding colour
    *   @post the tag for the specified player is changed for all players in the game
    **/
    public static void setTag(Player p, String name, List<String> players)
    {
        if(p == null || name.equals(p.getName()) || players == null || players.isEmpty())
            return;
        Packet29DestroyEntity destroy = new Packet29DestroyEntity(p.getEntityId());
        Packet20NamedEntitySpawn create = new Packet20NamedEntitySpawn(((CraftHumanEntity)p).getHandle());
        if(name.length() > 16)
        {
            name = name.substring(0, 16);
        }
        create.b = name;
        
        for(String tname : players)
        {
            Player t = Bukkit.getPlayer(tname);
            if(t.equals(p) || t == null)
            {
                return;
            }
            ((CraftPlayer)t).getHandle().netServerHandler.sendPacket(destroy);
            ((CraftPlayer)t).getHandle().netServerHandler.sendPacket(create);
        }
    }

}