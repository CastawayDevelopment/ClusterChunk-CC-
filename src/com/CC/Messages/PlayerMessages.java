package com.CC.Messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerMessages {
	
	public String noPermission(Player player){
		return ChatColor.GRAY + "Sorry " + player.getDisplayName() + ", only operators do this."; 
	}
	
	public String noPermissionCommand(Player player){
		return ChatColor.GRAY + "Sorry " + player.getDisplayName() + ", only operators can use this command."; 
	}
	
	public String worldDoesNotExist(Player player){
		return ChatColor.GRAY + "Sorry " + player.getDisplayName() + ", the world you have entered does not exist."; 
	}
	
	public String signCategoryDoesNotExist(String string){
		return ChatColor.GRAY + "The extension, "+ string + "is not valid please use: Join or Status"; 
	}
	public String teamColorException(String string){
		return ChatColor.GRAY + "Please use either Blue or Red not " + string; 
	}
	public String joinSignCreated(String string){
		return ChatColor.GRAY + "Congratulations! The join sign for the team " + string +  " has been created"; 
	}
	public String statusSignCreated(String string){
		return ChatColor.GRAY + "Congratulations! A status sign has been created"; 
	}
	public String gameEndedSuccessfully(){
		return ChatColor.GREEN + "You have successfully ended the game";
	}

}
