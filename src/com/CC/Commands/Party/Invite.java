package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.onStartup;

public class Invite 
{
    private onStartup plugin;
    
    public Invite(onStartup p)
    {
        this.plugin = p;
    }

    public void invitePlayer(Player from, Player invited){
    	if(plugin.getParties().getParty(from) != null){
    		plugin.getParties().getParty(from).invitePlayer(from, invited);
    		
    	}else{
    		from.sendMessage(ChatColor.RED + "You are not currently in a party");
    		
    	}
    }
}
