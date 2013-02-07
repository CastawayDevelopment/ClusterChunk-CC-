package com.CC.Commands.Party;

import com.CC.General.onStartup;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help 
{
	
    
    public Help(onStartup p)
    {
        
    }

	public void help(Player player){
            
		player.sendMessage(ChatColor.DARK_GRAY + "===========" + ChatColor.DARK_RED + "[" + ChatColor.DARK_AQUA + "Party Commands" + ChatColor.DARK_RED + "]" + ChatColor.DARK_GRAY + "===========");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Create <party name>" + ChatColor.GREEN  + " - Create a party with the specified name");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Disband " + ChatColor.GREEN  + " - Disband your party");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Help " + ChatColor.GREEN  + "- Take a guess <3");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Invite <player name> " + ChatColor.GREEN  + "- Invite a player to your closed party");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party AcceptInvite " + ChatColor.GREEN  + "- Join a party you were invited to");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Leave " + ChatColor.GREEN  + "- Leave the party you are currently in");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Start <red/blue> " + ChatColor.GREEN  + "- Start a match with your party on red or blue team");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Status " + ChatColor.GREEN  + "- Get the status of your party");
		player.sendMessage(ChatColor.DARK_GREEN + "/Party Versus <PartyName> " + ChatColor.GREEN  + "- Challenge another party");
		player.sendMessage(ChatColor.DARK_GRAY + "===========" + ChatColor.DARK_RED + "[" + ChatColor.DARK_AQUA + "Party Commands" + ChatColor.DARK_RED + "]" + ChatColor.DARK_GRAY + "===========");
		
		
	}
}
