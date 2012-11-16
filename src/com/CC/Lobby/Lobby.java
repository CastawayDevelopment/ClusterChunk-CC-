package com.CC.Lobby;

import java.util.HashMap;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Lobby implements Listener{
	public enum Team {
		 RED, BLUE;  
		}
	
	public static HashMap<Player, Team> quedplayers = new HashMap<Player, Team>();
	
	
	
	@EventHandler
	public void onQue(PlayerMoveEvent event){
		Player player = event.getPlayer();
		if(/*Check if a player is not in a game*/){
			if(quedplayers.containsKey(event.getPlayer())){
				if(onLobby(player)){
						quedplayers.remove(player);
						player.sendMessage("You have left the lobby, thus, unqueed.");
								}
							}else{
								if(onLobby(player)){
									Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
									if(block.getType() == Material.WOOL){
										byte blue = DyeColor.BLUE.getData();
										if( block.getData() == blue){
											player.sendMessage("You have been added to the blue team waiting list");
											quedplayers.put(player, Team.BLUE);
										}else{
											player.sendMessage("You have been added to the red team waiting list");
											quedplayers.put(player, Team.RED);
											}
											
										}
								}
							}
					}
		}
	
	
	public boolean onLobby(Player player){
		 Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		 if(block.getType() == Material.WOOL){
				byte red = DyeColor.RED.getData();
				byte blue = DyeColor.BLUE.getData();
				if(block.getData() == red || block.getData() == blue){
					return true;
					}else{
						return false;
					}
					
				}else{
					return false;
				}
			}
		}
	


