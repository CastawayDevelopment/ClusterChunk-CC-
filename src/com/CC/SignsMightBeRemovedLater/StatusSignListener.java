package com.CC.SignsMightBeRemovedLater;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import com.CC.Messages.PlayerMessages;

public class StatusSignListener implements Listener{

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignCreate(SignChangeEvent sign){
		Player player = sign.getPlayer();
		
		if(sign.getLine(0).equalsIgnoreCase("[clusterchunk]") ||sign.getLine(0).equalsIgnoreCase("[cc]")){
			if(sign.getLine(2).equalsIgnoreCase("self")){
				if(sign.getLine(1).equalsIgnoreCase("status")){
					
						if(player.hasPermission("clusterchunk.sign.create.status")){
							player.sendMessage(PlayerMessages.statusSignCreated(sign.getLine(2)));
							sign.setLine(0, ChatColor.DARK_GRAY + "ClusterChunk");
							sign.setLine(1, ChatColor.DARK_PURPLE + "Status");
							sign.setLine(2, ChatColor.DARK_PURPLE + "Self");
							sign.getBlock().getState().update(true);
														
						}else{
							player.sendMessage(PlayerMessages.noPermission(player));
						}
				}else{
					player.sendMessage(PlayerMessages.signCategoryDoesNotExist(sign.getLine(1)));
				}
			}
				}
			
			}
		
	}
	
	

