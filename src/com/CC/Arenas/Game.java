package com.CC.Arenas;

import java.util.HashMap;
import java.util.ArrayList;

import org.bukkit.Bukkit;
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
    
    public ArrayList<String> getRedTeam(){
    	return redTeam;
    }
    
    public ArrayList<Player> getRedTeamPlayers(){
    	ArrayList<Player> player = new ArrayList<Player>();
    	for(String s : redTeam){
    		Player p = Bukkit.getPlayer(s);
    		player.add(p);
    	}
    	return player;
    }
    
    public ArrayList<String> getBlueTeam(){
    	return blueTeam;
    }
    
    public ArrayList<Player> getBlueTeamPlayers(){
    	ArrayList<Player> player = new ArrayList<Player>();
    	for(String s : blueTeam){
    		Player p = Bukkit.getPlayer(s);
    		player.add(p);
    	}
    	return player;
    }
    
    public boolean removePlayer(String string){
    	Team team = getTeam(string);
    	if(team.equals(Team.BLUE)){
    		blueTeam.remove(string);
    		return true;
    	}else if (team.equals(Team.RED)){
    		redTeam.remove(string);
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean removePlayer(Player player){
    	Team team = getTeam(player.getName());
    	if(team.equals(Team.BLUE)){
    		blueTeam.remove(player.getName());
    		return true;
    	}else if (team.equals(Team.RED)){
    		redTeam.remove(player.getName());
    		return true;
    	}
    	return false;
    	
    	}
    	
   }
    
    
    
    
    
