package com.CC.General;



import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CC.SignsMightBeRemovedLater.JoinSignListener;
import com.CC.SignsMightBeRemovedLater.StatusSignListener;
import com.CC.Lobby.LobbyListener;
import com.CC.Game.GameManager;

	
 public class onStartup extends JavaPlugin implements Listener 
 {
		 
		 
		public final JoinSignListener jsl = new JoinSignListener ();
		public final StatusSignListener ssl = new StatusSignListener ();
        public final LobbyListener ll;
        private GameManager gm;
		 
        public onStartup()
        {
            ll = new LobbyListener(this);
        }
         
        @Override
        public void onEnable() 
        {
            gm = new GameManager();
            PluginManager pm = getServer().getPluginManager();
            pm.registerEvents(jsl, this);
            pm.registerEvents(ssl, this);
            pm.registerEvents(ll, this);
            getLogger().info("Plugin Is Enabled");
            getServer().getPluginManager().registerEvents(this,this);
        }

        @Override
        public void onDisable() 
        {
            getLogger().info("Plugin Is Disabled");	
        }	 
        
        public getGameManager()
        {
            return this.gm;
        }
		 
 }
	
