package com.CC.Commands.Party;

import com.CC.General.onStartup;
import static org.bukkit.ChatColor.*;
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
        plugin.getParties().addParty(partyName, player);
        if(plugin.getParties().getParty(partyName) != null){
        	player.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully created the party ").append(DARK_GREEN).append(partyName).toString());
        }else{
        	player.sendMessage(new StringBuilder(RED.toString()).append("A party with that name already exists. Please choose another.").toString());
        }
        
	}
}