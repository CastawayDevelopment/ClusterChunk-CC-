package com.CC.Party;

import org.bukkit.entity.Player;

public class Create 
{
    private onStartup plugin;
    
    public Create(onStartup p)
    {
        this.plugin = p;
    }

	public void create(Player player, String partyName) 
    {
		player.sendMessage("Create workin");
        plugin.getParties().addParty(partyName);
	}
}