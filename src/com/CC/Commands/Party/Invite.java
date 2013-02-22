package com.CC.Commands.Party;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.onStartup;
import com.CC.Listeners.LobbyListener.*;

public class Invite 
{
	
    private onStartup plugin;
    
    public Invite(onStartup p)
    {
        this.plugin = p;
    }

    public void invitePlayer(Player from, Player invited){
    	if(plugin.getParties().getParty(from) != null && plugin.getLobbies().inLobby(invited) == false && plugin.getGameManager().isInGame(invited) == false ){
    		plugin.getParties().getParty(from).invitePlayer(from, invited);
    			invited.sendMessage("To join " + from + "'s party, type" + ChatColor.RED + " /party accept" + ChatColor.WHITE + "or" + ChatColor.RED + "/party DENY");
    			
    			
    			
    	}else{
    		from.sendMessage(ChatColor.RED + "You are not currently in a party");
    		
    	}
    }
}
