package com.CC.Arenas;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.WorldGeneration.WorldGeneration;

public class GameManager
{

    private HashMap<String, String> players;
    private HashMap<String, Game> games;

    public GameManager()
    {
        this.games = new HashMap<String, Game>();
        this.players =  new HashMap<String, String>();
    }
    
    public Game getGame(String name)
    {
        return this.games.get(name);
    }
    
    public boolean createGame(String name)
    {
        if(this.games.containsKey(name))
        {
            return false;
        }
        Game g = new Game();
        this.games.put(name, g);
        return true;
    }
    
    public boolean removeGame(String name)
    {
        if(!this.games.containsKey(name))
        {
            return false;
        }
        this.games.remove(name);
        return true;
    }
    
    public Game getGameByPlayer(Player p)
    {
        return getGameByPlayer(p.getName());
    }
    
    /*
    *   Can return null!
    **/
    public Game getGameByPlayer(String pname)
    {
        if(players.containsKey(pname))
        {
            return games.get(players.get(pname));
        }
        return null;
    }
    
    public boolean isInGame(Player peter){
    	if(players.containsKey(peter.getName())) return true;
    	return false;
        
    }
    
    public boolean isInGame(String string){
    	if(players.containsKey(string)) return true;
    	return false;
        
    }
    
    private boolean endGame(Game game, Team team){
    	for(String p : game.getPlayers()){
    		Player player = Bukkit.getPlayer(p);
    		//Put in method to teleport to lobby world 
    	}
    	if(team.equals(Team.BLUE)){
    		for(Player p : game.getBlueTeamPlayers()){
    			p.sendMessage("Congratulations ! Your team has won ");
    		}
    		for(Player p : game.getRedTeamPlayers()){
    			p.sendMessage("Sorry you lost");
    		}
    		return true;
    	}else{
    		for(Player p : game.getBlueTeamPlayers()){
    			p.sendMessage("Sorry you lost");
    		}
    		for(Player p : game.getRedTeamPlayers()){
    			p.sendMessage("Congratulations ! Your team has won");
    		}
    		games.remove(game);
    		return true;
    		
    	}
    }
    //Also regenerates arena :D 
    public boolean endGame(String gameName, Team winningTeam){
    	if(!games.containsKey(gameName)) return false;
    		if(endGame(getGame(gameName), winningTeam)){
    			Bukkit.getServer().broadcastMessage(ChatColor.GRAY+ gameName + ChatColor.GREEN + " is being regenerated. You may experience lag");
    			WorldGeneration.newMap(gameName);
    			return true;
    		}else{
    			return false;
    	}
    }
    
}