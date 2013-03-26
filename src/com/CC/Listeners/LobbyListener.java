package com.CC.Listeners;

import com.CC.Arenas.Game;
import com.CC.Arenas.GameManager;
import com.CC.Enums.LobbyColour;
import com.CC.Enums.Team;
import com.CC.General.ClusterChunk;
import com.CC.General.User;
import com.CC.General.UserManager;
import com.CC.Party.Party;
import com.CC.Party.PartyBattle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyListener implements Listener
{
    private ClusterChunk plugin;
    private GameManager gamemanager;
    private UserManager usermanager;
    
    public LinkedList<String> queueBlue;
    public LinkedList<String> queueRed;
    public LinkedList<PartyBattle> queueVersus;
    public Set<String> partyInQueue;
    
    private final Random rnd = new Random();

    public LobbyListener(ClusterChunk instance)
    {
        plugin = instance;
        gamemanager = plugin.getGameManager();
        usermanager = plugin.getUserManager();
        queueBlue = plugin.queueBlue;
        queueRed = plugin.queueRed;
        queueVersus = plugin.queueVersus;
        partyInQueue = plugin.partyInQueue;
        //System.out.println("LobbyListener created.");
    }

    public boolean inLobby(Player s)
    {
        return this.queueBlue.contains(s.getName()) ||
                this.queueRed.contains(s.getName()) ||
                this.partyInQueue.contains(s.getName());
        //return quedplayers.containsKey(s);
    }

    @EventHandler
    public void onQueue(PlayerMoveEvent event)
    {

        Player player = event.getPlayer();
        if (plugin.getParties().getParty(player) != null)
        {
            return;
        }


        if (gamemanager.isInGame(player))
        {
            return;
        }


        if (!player.getLocation().getWorld().getName().equalsIgnoreCase("lobby"))
        {
            return;
        }
        
        //player.sendMessage("Step 1");
        Block block = event.getPlayer().getLocation().subtract(0, 3, 0).getBlock();
        if (queueRed.contains(player.getName()) || queueBlue.contains(player.getName()))
        {
            //player.sendMessage("Step 2");
            if (!onLobby(block))
            {
                //player.sendMessage("Step 3");
                queueRed.remove(player.getName());
                queueBlue.remove(player.getName());
                player.sendMessage(new StringBuilder(BOLD.toString()).append("You've been removed from the waiting list").toString());
                player.getInventory().setHelmet(null);
            }
            return;
        }
        
        //player.sendMessage("Step 1");
        if (onLobby(block))
        {
            //player.sendMessage("Step 3");
            byte data = block.getData();
            
            if (data == LobbyColour.WHITE.getData())
            {
                data = rnd.nextBoolean() ? LobbyColour.BLUE.getData(): LobbyColour.RED.getData();
            }
            
            if (data == LobbyColour.BLUE.getData())
            {
                //player.sendMessage("Step 4");
                player.sendMessage(new StringBuilder(BLUE.toString()).append("You have been added to the blue team waiting list").toString());
                this.queueBlue.addLast(player.getName());
                player.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 11));
                //System.out.println("" + quedplayers);
            }
            else
            {
                //player.sendMessage("Step 4");
                player.sendMessage(new StringBuilder(RED.toString()).append("You have been added to the red team waiting list").toString());
                this.queueRed.addLast(player.getName());
                player.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 14));
                //System.out.println("" + quedplayers);
            }
        }
    }
    
    
    
    public boolean onLobby(Block block)
    {
        if (block.getType() == Material.WOOL)
        {
            LobbyColour lc = LobbyColour.byData(block.getData());
            switch(lc)
            {
                case RED:
                case BLUE:
                case WHITE:
                    return true;
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if (inLobby(player))
        {
            queueRed.remove(player.getName());
            queueBlue.remove(player.getName());
            Party party = plugin.getParties().getParty(player);
            if(this.partyInQueue.contains(player.getName()))
            {
                for(String member : party.getMembers())
                {
                    this.partyInQueue.remove(member);
                }
                for(PartyBattle pb : this.queueVersus)
                {
                    if(pb.getChallengee() == party || pb.getChallenger() == party)
                    {
                        
                        int index = this.queueVersus.indexOf(pb);
                        this.queueVersus.remove(index);
                        int newindex = 0;
                        List<String> queueToPoll = this.queueBlue;
                        if(pb.getPreferredTeam() == Team.RED)
                        {
                            queueToPoll = this.queueRed;
                        }
                        for(String q : queueToPoll)
                        {
                            if(q.equals(ClusterChunk.PARTY))
                            {
                                if(index == 0)
                                {
                                    queueToPoll.remove(newindex);
                                    break;
                                }
                                index--;
                            }
                            newindex++;
                        }
                        break;
                    }
                }
            }
        }
    }

    /*@EventHandler
     public void noJump(PlayerMoveEvent event){
    	
     Player player = event.getPlayer();
     // player.sendMessage("Called!");
     if(quedplayers.containsKey(player)){
     //player.sendMessage("1");
     if(event.getFrom().getY() < event.getTo().getY()){
     event.setCancelled(true);
     player.getVelocity().setY(0);
     //player.sendMessage("2");
     }
     }
    		
     }*/

    /**
     * This will block the redstone lamps in the lobby world from turning off :D
     *
     */
    @EventHandler
    public void lampAlwaysOn(BlockRedstoneEvent event)
    {
        if (event.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("lobby"))
        {
            if (event.getBlock().getType() == Material.REDSTONE_LAMP_ON || event.getBlock().getType() == Material.REDSTONE_LAMP_OFF)
            {
                event.setNewCurrent(100);
            }
        }
    }
}
