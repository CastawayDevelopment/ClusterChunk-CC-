package com.CC.Commands.Party;

import com.CC.General.onStartup;
import com.CC.Party.Storage;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Disband {
	
	private onStartup plugin;
	private Storage partymanager;
	
	public Disband(onStartup plugin){
		this.plugin = plugin;
		partymanager = plugin.getParties();
		
	}
	
	public void disbandParty(Player from){
		if(partymanager.getParty(from) != null){
			partymanager.disbandParty(from, partymanager.getParty(from));
		}else{
			from.sendMessage(ChatColor.RED + "You are not currently a leader of any parties");
		}
		
		
		
	}

}
