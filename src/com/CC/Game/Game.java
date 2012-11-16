package com.CC.Game;

import java.util.HashMap;
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Game
{
	
			//Player  Arena   Team
	HashMap<Player, HashMap<String, Team>>  gameData =  new HashMap<Player, HashMap<String, Team>>();
    
    ArrayList<String> redTeam;
    ArrayList<String> blueTeam;
	
    public Game()
    {
        redTeam = new ArrayList<String>();
        blueTeam = new ArrayList<String>();
    }
    
    public ArrayList<String> getPlayers()
    {
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(redTeam);
        ret.addAll(blueTeam);
        return ret;
    }
    
    public Team getTeam(Player p)
    {
        return getTeam(p.getName());
    }
    
    public Team getTeam(String name)
    {
        if(redTeam.contains(name))
        {
            return Team.RED;
        }
        else if(blueTeam.contains(name))
        {
            return Team.BLUE;
        }
        return Team.NONE;
    }
    
    
}