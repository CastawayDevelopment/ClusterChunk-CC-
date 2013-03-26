package com.CC.Party;

import com.CC.Enums.Team;

/**
 *
 * @author DarkSeraphim
 */
public class PartyBattle 
{
    
    private Party a;
    private Party b;
    
    private Team preferred;
    
    public PartyBattle(Party challenger, Party challengee)
    {
        this.a = challenger;
        this.b = challengee;
    }
    
    public void setPreferredTeam(Team t)
    {
        this.preferred = t;
    }
    
    public Team getPreferredTeam()
    {
        return this.preferred;
    }
    
    public Party getChallenger()
    {
        return this.a;
    }
    
    public Party getChallengee()
    {
        return this.b;
    }

}
