package com.CC.General;




import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CC.Lobby.LobbyListener;
import com.CC.Arenas.GameManager;
import com.CC.Commands.*;

	
 public class onStartup extends JavaPlugin implements Listener 
 {
		 
		 
		
	 	public final LobbyListener ll = new LobbyListener(this);
        public GameManager gm;
		 
        public onStartup()
        {
            
        }
         
        @Override
        public void onEnable() 
        {
            getCommand("party").setExecutor(new PartyCommands());
            gm = new GameManager();
            PluginManager pm = getServer().getPluginManager();
            //System.out.println("Registering LobbyListener");
            pm.registerEvents(ll, this);
            getLogger().info("Plugin Is Enabled");
            getServer().getPluginManager().registerEvents(this,this);
        }

        @Override
        public void onDisable() 
        {
            getLogger().info("Plugin Is Disabled");	
        }	 
        
        public GameManager getGameManager()
        {
            return this.gm;
        }
		 
 }
	
