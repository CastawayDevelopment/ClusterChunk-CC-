package com.CC.Commands.Party;

import org.bukkit.entity.Player;

import com.CC.General.onStartup;

public class StartBlue
{
    private onStartup plugin;
    
    public StartBlue(onStartup p)
    {
        this.plugin = p;
    }

	public void start(Player player)
    {
		player.sendMessage("StartBlue workin");
		//Check for open games
	}
}
