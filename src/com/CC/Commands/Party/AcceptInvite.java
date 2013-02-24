package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.onStartup;
import com.CC.Commands.Party.Invite.*;

public class AcceptInvite 
{
    private onStartup plugin;
    
    public AcceptInvite(onStartup plugin)
    {
        this.plugin = plugin;
    }

    public boolean execute()
    {
        return false;
    }

	public void accept(String partyName, Player invited) {
		
		try{
			plugin.getParties().getParty(partyName).addMember(invited);
		}catch(Exception e){
			invited.sendMessage(ChatColor.RED + "The party you are trying to join does not exist");
		}
		
			
			
			
			
			
			
		}
		
		
		
	}
    

