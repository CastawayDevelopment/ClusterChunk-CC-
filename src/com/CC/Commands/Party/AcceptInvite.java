package com.CC.Commands.Party;

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

	public void accept(Player from, Player invited) {
		plugin.getParties().getParty(from).addMember(invited);
			
			
			
			
			
			
		}
		
		
		
	}
    

