package com.CC.Party;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

import com.CC.General.onStartup;

public class Party {
	
    private String PartyName;
    private onStartup plugin;
    private String PartyStatus;
    private String Leader;
    private ArrayList<String> Members = new ArrayList<String>(); // Including the leader
    public HashMap<String, String> Invited = new HashMap<String, String>();
    private boolean Open;
    private boolean ingame;
 
    public void setName(String string){
    	PartyName = string;
    }
    
    public String getName(){
    	return this.PartyName;
    }
    
    public boolean inGame(){
    	return this.ingame;
    }
    
    public void setInGame(boolean bo){
    	this.ingame = bo;
    }
    
    /**
     * Not sure what party status is but it will
     * go here :D 
     */
    
    public void setLeader(Player player){
    	Leader = player.getName();
    	Members.add(player.getName());
    }
    
    public Player getLeader(){
    	Player player = Bukkit.getServer().getPlayer(Leader);
    	return player;
    }
    
    public boolean addMember(Player added){
    	if(Invited.containsKey(added.getName())){
    		if(!Open){
    			added.sendMessage(new StringBuilder(GRAY.toString()).append("Sorry ").append(added).append(", but the party you are trying to join is currently closed").toString());
    			added.sendMessage(new StringBuilder(GRAY.toString()).append("This is due to the fact that the party you are trying to join has 4 members already").toString());
    			return false;
    		}
    			
    		
    			Members.add(added.getName());
    			Invited.remove(added.getName());
    			added.sendMessage(new StringBuilder(GREEN.toString()).append("You have successfully joined ").append(DARK_GREEN).append(PartyName).toString());
    			if(Members.size() == 4){
    				Open = false;
    			}
    			return true;
    		}else{
    			added.sendMessage(new StringBuilder(RED.toString()).append("You have not been invited to ").append(PartyName).append(" therefore you may not join").toString());
    			return false;
    		}
    	
    		
    	}
    
    public void playerQuit(Player player){
     if(!player.getName().equals(Leader)){
    	player.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully left your party").toString());
    	Members.remove(player.getName());
    	for(String s: Members){
    		if(Bukkit.getServer().getPlayer(s) != null){
    			Player member = Bukkit.getServer().getPlayer(s);
                        member.sendMessage(new StringBuilder(DARK_GRAY.toString()).append(player.getName()).append(GRAY).append( " has left your party").toString());
    		}
    		if(Members.size() < 4){
    			Open = true;
    		}
    	}
     }else{
    	 player.sendMessage(new StringBuilder(GRAY.toString()).append("You must give ownership to someone else before you leave your party").toString());
    	 player.sendMessage(new StringBuilder(GRAY.toString()).append("You may also disband the party").toString());
     }
    	
    }
    
    //From leader
    public boolean removeMember(Player from, Player removed){
    	if(from.getName().equals(Leader)){
    		removed.sendMessage(new StringBuilder(DARK_GRAY.toString()).append(from.getName()).append(GRAY).append(" has removed you from the party").toString());
    		from.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully removed ").append(DARK_GREEN).append(removed.getName()).append(GREEN).append(" from your party!").toString());
    		Members.remove(removed.getName());
    		if(Members.size() < 4){
    			Open = true;
    		}
    		return true;
    	}else{
    		from.sendMessage(new StringBuilder(RED.toString()).append("Only ").append(DARK_RED).append(Leader).append(RED).append(" can remove players from your party").toString());
    		return false;
    	}
    }
    
    public boolean invitePlayer(Player from, Player invited){
    if(from.getName().equals(Leader)){
    	if(Members.size() < 4){
    		if(Invited.containsKey(invited.getName())){
    			from.sendMessage(new StringBuilder(GRAY.toString()).append("You have already invited ").append(DARK_GRAY).append(invited.getName()).toString());
    			return false;
    		}else{
    			invited.sendMessage(new StringBuilder("You have been invited to ").append(PartyName).toString());
    			invited.sendMessage(ChatColor.GREEN + "To join " + ChatColor.DARK_GREEN + plugin.getParties().getParty(from).getName() + ChatColor.GREEN +", type" + ChatColor.DARK_GREEN + " /party accept " + PartyName);
    			Invited.put(invited.getName(), PartyName);
    			return true;
    		}
    	}else{
    		from.sendMessage(new StringBuilder(RED.toString()).append("You already have 4 members in your party").toString());
    		return false;
    		}
    	}else{
    		from.sendMessage(new StringBuilder(RED.toString()).append("You may not invite people to the party because you are not the owner").toString());
    		return false;
    	}
    }
    
    public boolean newOwnerShip(Player sender, Player newplayer){
    	if(sender.getName().equals(Leader)){
    		Leader = newplayer.getName();
    		newplayer.sendMessage(new StringBuilder(DARK_GREEN.toString()).append(sender.getName()).append(DARK_GREEN).append(" has given you ownership of the party").toString());
    		sender.sendMessage(new StringBuilder(GRAY.toString()).append("You have traded ownership of the party over to ").append(DARK_GRAY).append(newplayer.getName()).toString());
    		return true;
    	}else{
    		sender.sendMessage(new StringBuilder(RED.toString()).append("You are not owner of your party, therefore you can not give it to someone").toString());
    		return false;
    	}
    }
    
    public ArrayList<String> getMembers(){
    	return this.Members;
    }
    
    
    
 
}