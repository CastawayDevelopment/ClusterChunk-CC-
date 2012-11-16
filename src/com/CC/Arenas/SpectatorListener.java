package com.CC.Arenas;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpectatorListener implements Listener{
	
 public static ArrayList<String> spectators = new ArrayList<String>();
	
	public static void addSpectator(Player player, Game game){
		for(String p: game.getPlayers()){
			Player pl = Bukkit.getPlayer(p);
			pl.hidePlayer(player);
		}
		spectators.add(player.getName());
		player.setFlying(true);
		player.setGameMode(GameMode.CREATIVE);
		player.sendMessage("You are succesfully spectating !");
		//Need something to teleport them in game
		return;
	}
	
	public static void PlayerLeavesGame(Player player){
		for(String name: spectators){
			Player unhide = Bukkit.getPlayer(name);
			player.showPlayer(unhide);
		}
	}
	public static void deleteSpectator(Player player, Game game){
		for(String p: game.getPlayers()){
			Player pl = Bukkit.getPlayer(p);
			pl.showPlayer(player);
		}
		spectators.remove(player.getName());
		player.sendMessage("You no longer spectating!");
		player.setFlying(false);
		player.setGameMode(GameMode.SURVIVAL);
		//Need something to teleport the player to the lobby world spawn gotoLobby(player);
		return;
	}
	
	public static boolean isSpectating(String string){
		if(spectators.contains(string)){
			return true;
		}else{
			return false;
		}
	}
	//There should be another listener in the plugin somewhere to stop the player from interacting with there inventory
	@EventHandler
	public void noDamage(EntityDamageByEntityEvent event){
			if(event.getDamager() instanceof Player){
				Player player = (Player)event.getDamager();
				if(isSpectating(player.getName())){
					player.sendMessage("You are not allowed to damage other players while spectating");
					event.setCancelled(true);	
					
				}
	
		}
	}		
			@EventHandler
			public void noInteract(PlayerInteractEvent event){
				if(spectators.contains(event.getPlayer().getName())){
					event.setCancelled(true);
					event.getPlayer().sendMessage("You cannot interact while spectating!");
				}
			
		
	}
			
			@EventHandler
			public void onChat(AsyncPlayerChatEvent event){
				if(spectators.contains(event.getPlayer().getName())){
					event.getPlayer().sendMessage("You cannot chat while ");
					event.setCancelled(true);
				}
			}
	
	

	

}