package com.CC.Party;

import org.bukkit.entity.Player;

import com.CC.Party.Party;

public class Leave 
{
    private onStartup plugin;
    
    public Leave(onStartup p)
    {
        this.plugin = p;
    }

	public void leave(Player player)
    {
		player.sendMessage("Leave workin");
        // Let him leave, needs command
        // Might loop him, or have a general HashMap with players in parties for faster search
	}
}
