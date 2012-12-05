package com.CC.Commands.Party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.CC.General.onStartup;

public class Create 
{
    private onStartup plugin;
    
    public Create(onStartup p)
    {
        this.plugin = p;
    }

	public void create(Player player, String partyName) 
    {
        plugin.getParties().addParty(partyName, player);
        if(plugin.getParties().getParty(partyName) != null){
        	player.sendMessage(ChatColor.GREEN + "You have succesfully created the party " + ChatColor.DARK_GREEN + partyName);
        }else{
        	player.sendMessage(ChatColor.RED + "A party with that name already exists. Please choose another.");
        }
        
	}
}