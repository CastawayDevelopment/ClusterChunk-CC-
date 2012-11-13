package com.CC.Messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerMessages {
	
	public static String noPermission(Player player){
		return ChatColor.GRAY + "Sorry " + player.getDisplayName() + ", only operators do this."; 
	}
	
	public static String noPermissionCommand(Player player){
		return ChatColor.GRAY + "Sorry " + player.getDisplayName() + ", only operators can use this command."; 
	}
	
	public static String worldDoesNotExist(Player player){
		return ChatColor.GRAY + "Sorry " + player.getDisplayName() + ", the world you have entered does not exist."; 
	}
	
	public static String signCategoryDoesNotExist(String string){
		return ChatColor.GRAY + "The extension, "+ string + "is not valid please use: Join or Status"; 
	}
	public static String teamColorException(String string){
		return ChatColor.GRAY + "Please use either Blue or Red not " + string; 
	}
	public static String joinSignCreated(String string){
		return ChatColor.GRAY + "Congratulations! The join sign for the team " + string +  " has been created"; 
	}
	public static String statusSignCreated(String string){
		return ChatColor.GRAY + "Congratulations! A status sign has been created"; 
	}

}
