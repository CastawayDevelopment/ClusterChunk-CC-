package com.CC.Commands.Party;

import com.CC.General.onStartup;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class Help 
{
	private onStartup plugin;
    
    public Help(onStartup p)
    {
        this.plugin = p;
    }

	public void help(Player player){
            
		player.sendMessage("");
		player.sendMessage(new StringBuilder(DARK_GRAY.toString()).append("===========").append(DARK_RED).append("[").append(DARK_AQUA).append("Party Commands").append(DARK_RED).append("]").append(DARK_GRAY).append("===========").toString());
		player.sendMessage("");
		player.sendMessage(new StringBuilder("/Party Create <party name>").append(" - ").append("Create a party with the specified name").toString());
		player.sendMessage(new StringBuilder("/Party Help").append(" - ").append("Take a guess <3").toString());
		player.sendMessage(new StringBuilder("/Party Invite <player name>").append(" - ").append("Invite a player to your closed party").toString());
		player.sendMessage(new StringBuilder("/Party Join").append(" - ").append("Join a party you were invited to").toString());
		player.sendMessage(new StringBuilder("/Party Leave").append(" - ").append("Leave the party you are currently in").toString());
		player.sendMessage(new StringBuilder("/Party Start <red/blue>").append(" - ").append("Start a match with your party on red or blue team").toString());
		player.sendMessage(new StringBuilder("/Party Status").append(" - ").append("Get the status of your party").toString());
		player.sendMessage("");
		player.sendMessage(new StringBuilder(DARK_GRAY.toString()).append("===========").append(DARK_RED).append("[").append(DARK_AQUA).append("Party Commands").append(DARK_RED).append("]").append(DARK_GRAY).append("===========").toString());
		player.sendMessage("");
		
	}
}
