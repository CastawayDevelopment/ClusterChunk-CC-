package com.CC.Party;

import org.bukkit.entity.Player;

public class Status 
{
    private onStartup plugin;
    
    public Status(onStartup p)
    {
        this.plugin = p;
    }
    
	public void status(Player player)
    {
        player.sendMessage("Status workin");	
        // Same as leave, them call the appropiate getStatus()
	}
}
