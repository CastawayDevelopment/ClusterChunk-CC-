package com.CC.Tags;

import com.CC.Arenas.Game;
import com.CC.Enums.Team;
import java.util.List;
import net.minecraft.server.v1_4_6.Packet20NamedEntitySpawn;
import net.minecraft.server.v1_4_6.Packet29DestroyEntity;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.craftbukkit.v1_4_6.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_4_6.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TagUtil
{
    /**
     * Changing this to TagAPI later
     * 
     */
    
    
    /*
    *   Takes all players in a game and set the tag
    *    to the corresponding team colour
    *   @post the tag changed for all players in the game
    **/
    public static void setTeamTag(Game g)
    {
        for(String player : g.getPlayers())
        {
            Player p = Bukkit.getPlayer(player);
            if(p == null)
            {
                continue;
            }
            String name = p.getName();
            if(g.getTeam(p) == Team.RED)
            {
                name = RED + name; // For red
            }
            else if(g.getTeam(p) == Team.BLUE)
            {
                name = BLUE + name; // For blue
            }
            else
            {
                continue;
            }
            
            setTag(p, name, g.getPlayers());
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
            ((CraftPlayer)t).getHandle().playerConnection.sendPacket(destroy);
            ((CraftPlayer)t).getHandle().playerConnection.sendPacket(create);
        }
    }

}