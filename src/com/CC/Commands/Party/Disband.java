package com.CC.Commands.Party;

import org.bukkit.entity.Player;

import com.CC.General.onStartup;
import com.CC.Party.Storage;

public class Disband {
	
	private onStartup plugin;
	private Storage partymanager;
	
	public Disband(onStartup plugin){
		this.plugin = plugin;
		partymanager = plugin.getParties();
		
	}
	
	public void Disbandparty(Player from){
		if(partymanager.getParty(from) != null){
			partymanager.disbandParty(from, partymanager.getParty(from));
		}
		
		
		
	}

}
