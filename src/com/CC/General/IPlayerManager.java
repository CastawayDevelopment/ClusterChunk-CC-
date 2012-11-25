package com.CC.General;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class IPlayerManager {
	HashMap<String, IPlayer> players;
	
	public IPlayerManager(){
		players = new HashMap<String, IPlayer>();
	}
	
	public boolean createIPlayer(Player player){
		if(players.containsKey(player.getName())) return false;
			IPlayer iplayer = new IPlayer(player);
			players.put(player.getName(), iplayer);
			return true;
	}
	
	public void loadPlayers(ArrayList<String> playerNames){
		//Get all of the PlayerNames from MySQL DataBase
		for(String s : playerNames){
			
			loadPlayer(s);
		}
		
	}
	
	private void loadPlayer(String player){
		IPlayer iplayer = new IPlayer(Bukkit.getServer().getPlayer(player));
		iplayer.changeLatestGame(/*Get latest Game from MySQL*/);
		iplayer.changePoints(/*Get points from MySQL*/);
		iplayer.changeReputation(/*Get reputation from MySQL*/);
		iplayer.setDeaths(/*Get from MySQL*/);
		iplayer.setFriendsList(/*Get from MySQL*/);
		iplayer.setKills(/*Get from MySQL*/);
		iplayer.setTimeOnBlue(/*Get from MySQL*/);
		iplayer.setTimesOnRed(/*Get from MySQL*/);
	}
	
	public void savePlayers(){
		for(IPlayer p : players.values()){
			/**
			 * Make a chart for each IPlayer in MySQL containing the following information
			 * The player -- Use p.getPlayer();
			 * The LatestGame -- Use p.getLatestGame();
			 * The player's points -- Use p.getPoints();
			 * The player's reputation -- Use p.getReputation();
			 * The player's deaths -- Use p.getDeaths();
			 * The player's kills -- Use p.getKills();
			 * The player's friends list -- Use p.getFriends();
			 * The player's Times played on the blue team -- Use p.getTimesPlayedOnBlueTeam();
			 * The player's Times player on the red team -- Use p.getTimesPlayedOnRedTeam();
			 */
		}
	}
	
}
