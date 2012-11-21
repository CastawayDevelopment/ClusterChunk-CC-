package com.CC.Party;

import org.bukkit.entity.Player;

public class StartRed 
{
    private onStartup plugin;
    
    public StartRed(onStartup p)
    {
        this.plugin = p;
    }

	public void start(Player player)
    {
		player.sendMessage("StartRed workin");
		//Check for open games
	}
}
