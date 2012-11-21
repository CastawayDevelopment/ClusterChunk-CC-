package com.CC.Party;
 
import java.util.HashMap;
 
public class Storage {
 
    public HashMap<String, Party> parties = new HashMap<String, Party>();
    
    public Party getParty(String name)
    {
        // Can return null, be aware
        return parties.get(name);
    }
    
    public void addParty(String name)
    {
        if(!parties.containsKey(name))
        {
            parties.put(name, new Party());
        }
        else
        {
            //player.sendMessage("party already exists");
        }
    }
    
    public void removeParty(String name)
    {
        if(parties.containsKey(name))
        {
            parties.remove(name);
            //player.sendMessage("party removed");
        }
    }
        
}