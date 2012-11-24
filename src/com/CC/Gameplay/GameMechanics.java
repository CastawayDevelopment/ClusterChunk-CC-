package com.CC.Gameplay;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import com.CC.Arenas.Game;
import com.CC.Arenas.GameManager;
import com.CC.Arenas.SpectatorListener;
import com.CC.Arenas.Team;

public class GameMechanics implements Listener{
	
private GameManager gamemanager;
private Game playergame;
    
    public GameMechanics(GameManager instance) {
    	gamemanager = instance;
	}
	
	
	@EventHandler
	public void spectateOnDeath(PlayerDeathEvent event){
			Player peter = event.getEntity();
		if(gamemanager.isInGame(peter)){
			if(peter.getBedSpawnLocation() != null){
					SpectatorListener.addSpectator(peter, gamemanager.getGameByPlayer(peter));
					playergame = gamemanager.getGameByPlayer(peter);
					Team playersteam = playergame.getTeam(peter);
					if(playersteam.equals(Team.BLUE)){
						for(Player p :playergame.getBlueTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has died fighting for your team");
						}
						for(Player p :playergame.getRedTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has been killed");
						}
					}else{
						for(Player p :playergame.getRedTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has died fighting for your team");
						}
						for(Player p :playergame.getBlueTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has been killed");
						}
				
					}
			}else{
				playergame.removePlayer(peter);
				peter.kickPlayer(ChatColor.RED + "You have died and have no bed set.");
				playergame = gamemanager.getGameByPlayer(peter);
				
			}
		}
	}
	
	@EventHandler
	public void noCraftBed(CraftItemEvent event){
		ItemStack result = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		if(gamemanager.isInGame(player)){
			if(result.getType() == Material.BED || result.getType() == Material.BED_BLOCK){
				event.setCancelled(true);
				playergame = gamemanager.getGameByPlayer(player);
				ArrayList<String> players = playergame.getPlayers();
				for(String s : players){
					Player peter = Bukkit.getPlayer(s);
					if(peter == player) return;
						peter.sendMessage(ChatColor.GRAY + player.getName()+ ChatColor.GREEN+ " has tried to craft a bed");
					
				}
			}
		}
	}
	
	
	

}
