package com.CC.Arenas;

import java.util.HashMap;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Game
{
	
    String name;
    ArrayList<String> redTeam;
    ArrayList<String> blueTeam;
    boolean regenerated;
    private GameManager gm;
	
    public Game(GameManager instance, String name)
    {
    	gm = instance;
        redTeam = new ArrayList<String>();
        blueTeam = new ArrayList<String>();
    }
    
    public String getName(){
    	return this.name;
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
    		gm.removePlayerFromGame(string);
    		return true;
    	}else if (team.equals(Team.RED)){
    		redTeam.remove(string);
    		gm.removePlayerFromGame(string);
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean removePlayer(Player player){
    	Team team = getTeam(player.getName());
    	if(team.equals(Team.BLUE)){
    		blueTeam.remove(player.getName());
    		gm.removePlayerFromGame(player.getName());
    		return true;
    	}else if (team.equals(Team.RED)){
    		redTeam.remove(player.getName());
    		gm.removePlayerFromGame(player.getName());
    		return true;
    	}
    	return false;
    	
    	}
    
    public void setRegenerated(boolean trueorfalse){
    	regenerated = trueorfalse;
    }
    
    public void addRedPlayer(String playername){
    	redTeam.add(playername);
    	Player player = Bukkit.getServer().getPlayer(playername);
    	player.sendMessage(ChatColor.RED + "You have succesfully join the red team!");
    	gm.playerJoinGame(playername);
    }
    
    public void addBluePlayer(String playername){
    	blueTeam.add(playername);
    	Player player = Bukkit.getServer().getPlayer(playername);
    	player.sendMessage(ChatColor.BLUE + "You have succesfully join the blue team!");
    	gm.playerJoinGame(playername);
    }
    
    
    public Location getRedSpawn(){
    	return Bukkit.getServer().getWorld("lobby").getSpawnLocation(); //Just for now until the actual spawn locations are found;
    	/**
    	 * When the new spawn locations are found it will be World == Arena Name and than the location of the spawn for the current team
    	 */
    }
    public Location getBlueSpawn(){
    	return Bukkit.getServer().getWorld("lobby").getSpawnLocation(); //Just for now until the actual spawn locations are found;
    	/**
    	 * When the new spawn locations are found it will be World == Arena Name and than the location of the spawn for the current team
    	 */
    }
    
    	
   }
    
    
    
    
    
