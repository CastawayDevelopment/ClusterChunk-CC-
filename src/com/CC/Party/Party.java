package com.CC.Party;
import java.util.ArrayList;

public class Party {
 
    private String name;
    private String status;
    private ArrayList<String> members = new ArrayList<String>();
 
    public void Storage(String name) {
        this.name = name;
    }

 
    public void addMember(Player player) 
    {
        // Might use the Player to check if he is invited later :3
        if(!this.members.contains(player.getName())) 
        {
            this.members.add(player.getName());
        }

    }
    
    public String getStatus()
    {
        return this.status;
    }
 
}