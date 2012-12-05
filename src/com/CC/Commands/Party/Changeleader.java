package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.onStartup;

public class Changeleader {
	
	private onStartup plugin;
	
	public Changeleader (onStartup instance){
		plugin = instance;
		
	}
	
	public boolean ChangePartyLeader(Player sender, Player newOwner){
		if(plugin.getParties().getParty(sender) == null){
			sender.sendMessage(ChatColor.RED + "You are not in a party");
			return false;
		}else{
			return plugin.getParties().getParty(sender).newOwnerShip(sender, newOwner);
		}
	}

}
