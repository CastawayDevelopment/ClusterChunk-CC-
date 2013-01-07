package com.CC.Arenas;

import java.util.HashMap;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.CC.General.onStartup;

public class Game
{
	
    String name;
    ArrayList<String> redTeam;
    ArrayList<String> blueTeam;
    boolean regenerated;
    public boolean started;
    private GameManager gm;
    int TimeofGame;
    int WarningTime;
    onStartup plugin;
	
    public Game(String name, onStartup instance)
    {
    	plugin = instance;
    	TimeofGame = plugin.getGameTime();
    	WarningTime = plugin.getWarningTime();
    	started = false;
    	gm = plugin.getGameManager();
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
    	Player player = Bukkit.getServer().getPlayer(playername);
    	redTeam.add(playername);
    	player.sendMessage(ChatColor.RED + "You have succesfully join the red team!");
    	gm.playerJoinGame(playername);
    }
    
    public void addBluePlayer(String playername){
    	Player player = Bukkit.getServer().getPlayer(playername);
    	blueTeam.add(playername);
    	player.sendMessage(ChatColor.BLUE + "You have succesfully join the blue team!");
    	gm.playerJoinGame(playername);
    }
    
    
    public Location getRedSpawn(String WorldName){
    	
    		return new Location(Bukkit.getServer().getWorld(WorldName), -866, 143, -762); 
    	}
    	 //Just for now until the actual spawn locations are found;
    	/**
    	 * When the new spawn locations are found it will be World == Arena Name and than the location of the spawn for the current team
    	 */
    
    public Location getBlueSpawn(String WorldName){
    	
    		return new Location(Bukkit.getServer().getWorld(WorldName), -936, 143, -762); 
    	}
    	 //Just for now until the actual spawn locations are found;
    	/**
    	 * When the new spawn locations are found it will be World == Arena Name and than the location of the spawn for the current team
    	 */
    
    
    public void timer(Boolean countdownorGamestart){
    	if(countdownorGamestart){
    		new BukkitRunnable() {
    			int i = WarningTime;
    			public void run() {
    	    	
    				if(i == WarningTime){
    					for(String s : getPlayers()){
    						plugin.getServer().getPlayer(s).sendMessage(ChatColor.GREEN + "" + WarningTime/60 + " minutes until the game begins");
    					}
    					i--;
    				}else if(i <= 10){ 
    	    	
    					countDown(" until the game begins", true);
    					this.cancel();
    				}else{
    					i--;
    				}
    	      	
    			}
    		}.runTaskTimer(plugin, 20, WarningTime*20);
    	}else{
    		new BukkitRunnable() {
    			int i = TimeofGame;
    			public void run() {
    	    	
    				if(i == TimeofGame/2){
    					for(String s : getPlayers()){
    						plugin.getServer().getPlayer(s).sendMessage(ChatColor.GREEN + "The game has reached half time there are " + TimeofGame/2 + " minutes left");
    					}
    					i--;
    				}else if(i <= 10){ 
    	    	
    					countDown(" until the game ends", false);
    					this.cancel();
    				}else{
    					i--;
    				}
    	      	
    			}
    		}.runTaskTimer(plugin, 20, WarningTime*20);
    		
    	}
    	
    }

	public void countDown(final String string, Boolean b) {
		
		new BukkitRunnable(){
			
			int i = 10;
			public void run(){
				
				for(String s : getPlayers()){
					Bukkit.getPlayer(s).sendMessage(ChatColor.GREEN + "" + i + string);
				}
				i--;
			}
		}.runTaskTimer(plugin, 20, 200);
		
		if(b){
			started = true;
			timer(false);
		}else{
			gm.endGame(name, getWinningTeam());
		}
    	
   }
	
	public Team getWinningTeam(){
		if(getBlueTeamPlayers().size() > getRedTeamPlayers().size()){
			return Team.BLUE;
		}else if(getBlueTeamPlayers().size() < getRedTeamPlayers().size()){
			return Team.RED;
		}else{
			return Team.NONE;
		}
	}
	
    
}   
    
    
    
