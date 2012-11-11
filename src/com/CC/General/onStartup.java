package com.CC.General;



import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CC.Signs.JoinSignListener;
import com.CC.Signs.StatusSignListener;

	
	 public class onStartup extends JavaPlugin implements Listener {
		 
		 
		 public final JoinSignListener jsl = new JoinSignListener ();
		 public final StatusSignListener ssl = new StatusSignListener ();
		 
		 
		 
		 @Override
			public void onEnable() {
			 	PluginManager pm = getServer().getPluginManager();
			 	pm.registerEvents(jsl, this);
			 	pm.registerEvents(ssl, this);
				getLogger().info("Plugin Is Enabled");
				getServer().getPluginManager().registerEvents(this,this);
			}

			@Override
			public void onDisable() {
				getLogger().info("Plugin Is Disabled");	
			}
		
		 
		 
		 
		 
		 
		 
		 
		 
 }
	
