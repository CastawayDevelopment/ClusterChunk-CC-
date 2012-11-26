package com.CC.General;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.CC.Arenas.Game;

public class User 
{
	String player;
	int PlayerPoints;
	int Reputation;
	int Kills;
	int Deaths;
	int timeOnRed;
	int timeOnBlue;
	String latestGame;
	ArrayList<String> friends;
	
	
	public User(Player p){
		friends = new ArrayList<String>();
		player = p.getName();
		
	}
	
	public int getPoints(){
		return PlayerPoints;
	}
	//For loading and Normal Use
	public void changePoints(int points){
		PlayerPoints = points;
	}
	
	public int getReputation(){
		return Reputation;
	}
	
	//For loading and normal use
	public void changeReputation(int changedReputation){
		Reputation = changedReputation;
	}
	
	public int getKills(){
		return Kills;
	}
	//For loading
	public void setKills(int amount){
		Kills = amount;
	}
	
	public void addKill(){
		int temp = Kills + 1;
		Kills = temp;
	}
	
	public int getDeaths(){
		return Deaths;
	}
	//For loading 
	public void setDeaths(int amount){
		Deaths = amount;
	}
	
	public void addDeath(){
		int temp = Deaths + 1;
		Deaths = temp;
	}
	
	public int getTimesPlayedOnRedTeam(){
		return timeOnRed;
	}
	
	public void addTimeOnRed(){
		int temp = timeOnRed + 1;
		timeOnRed = temp;
	}
	//For loading 
	public void setTimesOnRed(int times){
		timeOnRed = times;
	}
	
	public int getTimesPlayedOnBlueTeam(){
		return timeOnBlue;
	}
	
	public void addTimeOnBlue(){
		int temp = timeOnBlue + 1;
		timeOnBlue = temp;
	}
	//For loading 
	public void setTimeOnBlue(int times){
		timeOnBlue = times;
	}
	
	public String getLatestGame(){
		return latestGame;
	}
	//For loading normal use 
	public void changeLatestGame(String game){
		latestGame = game;
	}
	
	
	public ArrayList<String> getFriends(){
		return friends;
	}
	//for Loading 
	public void setFriendsList(ArrayList<String> friendslist){
		friends = friendslist;
	}
	
	public void addFriend(String friendName){
		friends.add(friendName);
	}
	
	public Player getPlayer(){
		return Bukkit.getServer().getPlayer(player);
	}

}
