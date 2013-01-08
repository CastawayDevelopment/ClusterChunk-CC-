package com.CC.Commands.Party;

import org.bukkit.entity.Player;

import com.CC.General.onStartup;

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
