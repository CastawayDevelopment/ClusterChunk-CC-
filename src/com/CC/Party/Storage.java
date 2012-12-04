package com.CC.Party;
 
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
 
public class Storage {
 
    public HashMap<String, Party> parties = new HashMap<String, Party>();
    
    public Party getParty(String name)
    {
        // Can return null, be aware
        return parties.get(name);
    }
    
    public void addParty(String name, Player leader)
    {
        if(!parties.containsKey(name))
        {
        	Party party = new Party();
            parties.put(name, party);
            party.setName(name);
            party.setLeader(leader);
        }
        else
        {
            //player.sendMessage("party already exists");
        }
    }
    
    private void removeParty(String name)
    {
        if(parties.containsKey(name))
        {
            parties.remove(name);
            //player.sendMessage("party removed");
        }
    }
    
    public void disbandParty(Player from, Party party){
    	if(from.equals(party.getLeader())){
    		from.sendMessage(ChatColor.GREEN + "You have succesfully disbanded your faction");
    		for(String s : party.getMembers()){
    			if(Bukkit.getServer().getPlayer(s) != null){
    				Player p = Bukkit.getServer().getPlayer(s);
    				p.sendMessage(ChatColor.RED + "Your faction has been disbanded");
    			}
    		}
    		removeParty(party.getName());
    	}else{
    		from.sendMessage(ChatColor.RED + "You cannot disband this faction!");
    	}
    	
    }
    //Can return null
    public Party getParty(Player player){
    	Party party = null;
    	for(Party p : parties.values()){
    		if(p.getMembers().contains(player.getName())){
    			party = p;
    		}
    		
    	}
    	
    	return party;
    	
    }
        
}