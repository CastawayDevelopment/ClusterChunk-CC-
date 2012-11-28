package com.CC.Party;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Party {
	
    private String PartyName;
    private String PartyStatus;
    private String Leader;
    private ArrayList<String> Members = new ArrayList<String>(); // Including the leader
    private ArrayList<String> Invited = new ArrayList<String>();
    private boolean Open;
 
    public void setName(String string){
    	PartyName = string;
    }
    
    public String getName(){
    	return this.PartyName;
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
    	if(Invited.contains(added.getName())){
    		if(!Open){
    			added.sendMessage(ChatColor.GRAY  + "Sorry "+ added + ", but the party you are trying to join is currently closed");
    			added.sendMessage(ChatColor.GRAY + "This is due to the fact that the party you are trying to join has 4 members already");
    			return false;
    		}
    			
    		
    			Members.add(added.getName());
    			Invited.remove(added.getName());
    			added.sendMessage(ChatColor.GREEN + "You have successfully joined " + ChatColor.DARK_GREEN+ PartyName);
    			if(Members.size() == 4){
    				Open = false;
    			}
    			return true;
    		}else{
    			added.sendMessage(ChatColor.RED + "You have not been invited to "+ PartyName + " therefore you may not join");
    			return false;
    		}
    	
    		
    	}
    
    public void playerQuit(Player player){
     if(!player.getName().equals(Leader)){
    	player.sendMessage(ChatColor.GREEN + "You have succesfully left your party");
    	Members.remove(player.getName());
    	for(String s: Members){
    		if(Bukkit.getServer().getPlayer(s) != null){
    			Player member = Bukkit.getServer().getPlayer(s);
    		member.sendMessage(ChatColor.DARK_GRAY + player.getName() + ChatColor.GRAY + " has left your party");
    		}
    		if(Members.size() < 4){
    			Open = true;
    		}
    	}
     }else{
    	 player.sendMessage(ChatColor.GRAY + "You must give ownership to someone else before you leave your party");
    	 player.sendMessage(ChatColor.GRAY + "You may also disband the party");
     }
    	
    }
    
    //From leader
    public boolean removeMember(Player from, Player removed){
    	if(from.getName().equals(Leader)){
    		removed.sendMessage(ChatColor.DARK_GRAY + from.getName()+ ChatColor.GRAY + " has removed you from the party");
    		from.sendMessage(ChatColor.GREEN + "You have succesfully removed "+ ChatColor.DARK_GREEN + removed.getName() + ChatColor.GREEN + " from your party!");
    		Members.remove(removed.getName());
    		if(Members.size() < 4){
    			Open = true;
    		}
    		return true;
    	}else{
    		from.sendMessage(ChatColor.RED + "Only "+ ChatColor.DARK_RED +  Leader + ChatColor.RED + " can remove players from your party");
    		return false;
    	}
    }
    
    public boolean invitePlayer(Player from, Player invited){
    if(from.getName().equals(Leader)){
    	if(Members.size() < 4){
    		if(Invited.contains(invited.getName())){
    			from.sendMessage(ChatColor.GRAY + "You have already invited " + ChatColor.DARK_GRAY + invited.getName());
    			return false;
    		}else{
    			invited.sendMessage("You have been invited to "+ PartyName);
    			Invited.add(invited.getName());
    			return true;
    		}
    	}else{
    		from.sendMessage(ChatColor.RED + "You already have 4 members in your party");
    		return false;
    		}
    	}else{
    		from.sendMessage(ChatColor.RED + "You may not invite people to the party because you are not the owner");
    		return false;
    	}
    }
    
    public boolean newOwnerShip(Player sender, Player newplayer){
    	if(sender.getName().equals(Leader)){
    		Leader = newplayer.getName();
    		newplayer.sendMessage(ChatColor.DARK_GREEN + sender.getName()+ ChatColor.DARK_GREEN  +" has given you ownership of the party");
    		sender.sendMessage(ChatColor.GRAY + "You have traded ownership of the party over to " + ChatColor.DARK_GRAY + newplayer.getName());
    		return true;
    	}else{
    		sender.sendMessage(ChatColor.RED + "You are not owner of your party, therefore you can not give it to someone");
    		return false;
    	}
    }
    
    public ArrayList<String> getMembers(){
    	return this.Members;
    }
    
    
    
 
}