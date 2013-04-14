package com.CC.Party;

import com.CC.General.ClusterChunk;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class Party
{

    private String name;
    //private String PartyStatus;
    private String leader;
    private List<String> members = new ArrayList<String>(); // Including the leader
    public List<String> invited = new ArrayList<String>();
    private boolean open;
    private boolean ingame;
    
    public Party(String name, String leader)
    {
        this.name = name;
        this.leader = leader;
        this.members.add(leader);
        this.ingame = false;
        this.open = true;
    }

    public void setName(String string)
    {
        name = string;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean inGame()
    {
        return this.ingame;
    }

    public void setInGame(boolean bo)
    {
        this.ingame = bo;
    }

    /**
     * Not sure what party status is but it will go here :D
     */
    /*public String getStatus()
    {
        StringBuilder stat = new StringBuilder("Party leader: ").append(this.Leader).append("\n")
                .append("Party members: ");
        for (String mem : this.Members)
        {
            if (!mem.equals(this.Leader))
            {
                stat.append(" * ").append(mem).append("\n");
            }
        }
        stat.append("This party is currently: ").append(this.inGame() ? ChatColor.RED : ChatColor.GREEN).append(this.inGame() ? "" : "not").append(" ingame");
        return stat.toString();
    }*/

    public void setLeader(Player player)
    {
        String l = player.getName();
        if(this.members.contains(l))
        {
            leader = player.getName();   
        }
    }

    public Player getLeader()
    {
        Player player = Bukkit.getServer().getPlayer(leader);
        return player;
    }

    public boolean addMember(Player added)
    {
        if (invited.contains(added.getName()))
        {
            if (!open)
            {
                added.sendMessage(new StringBuilder(GRAY.toString()).append("Sorry ").append(added.getDisplayName()).append(", but the party you are trying to join is currently closed").toString());
                added.sendMessage(new StringBuilder(GRAY.toString()).append("This is due to the fact that the party you are trying to join has ").append(ClusterChunk.TEAM_SIZE).append(" members already").toString());
                return false;
            }
            members.add(added.getName());
            invited.remove(added.getName());
            added.sendMessage(new StringBuilder(GREEN.toString()).append("You have successfully joined ").append(DARK_GREEN).append(name).toString());
            if (members.size() == ClusterChunk.TEAM_SIZE)
            {
                open = false;
            }
            return true;
        }
        else
        {
            added.sendMessage(new StringBuilder(RED.toString()).append("You have not been invited to ").append(name).append(" therefore you may not join").toString());
            return false;
        }
    }

    public void playerQuit(Player player)
    {
        if (!player.getName().equals(leader))
        {
            player.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully left your party").toString());
            members.remove(player.getName());
            for (String s : members)
            {
                if (Bukkit.getServer().getPlayer(s) != null)
                {
                    Player member = Bukkit.getServer().getPlayer(s);
                    member.sendMessage(new StringBuilder(DARK_GRAY.toString()).append(player.getName()).append(GRAY).append(" has left your party").toString());
                }
                if (members.size() < 4)
                {
                    open = true;
                }
            }
        }
        else
        {
            player.sendMessage(new StringBuilder(GRAY.toString()).append("You must give ownership to someone else before you leave your party").toString());
            player.sendMessage(new StringBuilder(GRAY.toString()).append("You may also disband the party").toString());
        }

    }

    //From leader
    public boolean removeMember(Player from, Player removed)
    {
        if (from.getName().equals(leader))
        {
            removed.sendMessage(new StringBuilder(DARK_GRAY.toString()).append(from.getName()).append(GRAY).append(" has removed you from the party").toString());
            from.sendMessage(new StringBuilder(GREEN.toString()).append("You have succesfully removed ").append(DARK_GREEN).append(removed.getName()).append(GREEN).append(" from your party!").toString());
            members.remove(removed.getName());
            if (members.size() < 4)
            {
                open = true;
            }
            return true;
        }
        else
        {
            from.sendMessage(new StringBuilder(RED.toString()).append("Only ").append(DARK_RED).append(leader).append(RED).append(" can remove players from your party").toString());
            return false;
        }
    }

    public boolean invitePlayer(Player from, Player invited)
    {
        if (from.getName().equals(leader))
        {
            if (members.size() < 4)
            {
                if (this.invited.contains(invited.getName()))
                {
                    from.sendMessage(new StringBuilder(GRAY.toString()).append("You have already invited ").append(DARK_GRAY).append(invited.getName()).toString());
                    return false;
                }
                else
                {
                    invited.sendMessage(new StringBuilder(ChatColor.DARK_AQUA.toString()).append("You have been invited to ").append(ChatColor.YELLOW).append(name).toString());
                    invited.sendMessage(ChatColor.GREEN + "To join " + ChatColor.DARK_GREEN + this.getName() + ChatColor.GREEN + ", type" + ChatColor.YELLOW + " /party accept " + ChatColor.LIGHT_PURPLE + name);
                    this.invited.add(invited.getName());
                    return true;
                }
            }
            else
            {
                from.sendMessage(new StringBuilder(RED.toString()).append("You already have 4 members in your party").toString());
                return false;
            }
        }
        else
        {
            from.sendMessage(new StringBuilder(RED.toString()).append("You may not invite people to the party because you are not the owner").toString());
            return false;
        }
    }

    public boolean declareLeader(Player sender, Player newplayer)
    {
        if (sender.getName().equals(leader))
        {
            leader = newplayer.getName();
            newplayer.sendMessage(new StringBuilder(DARK_GREEN.toString()).append(sender.getName()).append(DARK_GREEN).append(" has given you ownership of the party").toString());
            sender.sendMessage(new StringBuilder(GRAY.toString()).append("You have traded ownership of the party over to ").append(DARK_GRAY).append(newplayer.getName()).toString());
            return true;
        }
        else
        {
            sender.sendMessage(new StringBuilder(RED.toString()).append("You are not the leader of your party, therefore you can not declare someone else the leader").toString());
            return false;
        }
    }
    
    public void versus(Party other, ClusterChunk plugin)
    {
        PartyBattle pb = new PartyBattle(this, other);
        plugin.queueVersus.add(pb);
        plugin.queueBlue.add(ClusterChunk.PARTY);
    }

    public List<String> getMembers()
    {
        return this.members;
    }
    
    public void broadcast(String message)
    {
        Player player;
        for(String member : getMembers())
        {
            player = Bukkit.getPlayerExact(member);
            if(player != null) player.sendMessage(message);
        }
    }
    
    public boolean equals(Party other)
    {
        return this.name.equals(other.name);
    }
    
    public boolean allOnline()
    {
        for(String member : getMembers())
        {
            if(Bukkit.getPlayer(member) == null) return false;
        }
        return true;
    }
}