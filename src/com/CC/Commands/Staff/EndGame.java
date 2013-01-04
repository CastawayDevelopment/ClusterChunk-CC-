package com.CC.Commands.Staff;

import org.bukkit.entity.Player;

import com.CC.Arenas.Game;
import com.CC.Arenas.GameManager;
import com.CC.General.onStartup;
import com.CC.Messages.PlayerMessages;

public class EndGame {
	
	
private onStartup plugin;
private GameManager gamemanager;
private PlayerMessages messages;
	
	public EndGame(onStartup instance){
		plugin = instance;
		gamemanager = plugin.getGameManager();
		messages = plugin.getMessages();
		
	}
	
	public boolean endGame(Player sender, Game game, String reason){
		System.out.println(reason);
		if(sender.hasPermission("ClusterChunk.Admin.endGame") || sender.hasPermission("ClusterChunk.Admin.*")){
			gamemanager.endGame(game.getName(), reason);
			sender.sendMessage(messages.gameEndedSuccessfully());
			return true;
		}else{
			sender.sendMessage(messages.noPermissionCommand(sender));
			return false;
		}
		
	}

}
