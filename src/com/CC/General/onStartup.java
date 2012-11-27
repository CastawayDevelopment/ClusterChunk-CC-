package com.CC.General;

import java.util.logging.Logger;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CC.Lobby.LobbyListener;
import com.CC.Arenas.GameManager;
import com.CC.Commands.*;
import com.CC.Party.Storage;
import com.CC.WorldGeneration.WorldGeneration;
import com.CC.MySQL.MySQL;

	
 public class onStartup extends JavaPlugin implements Listener 
 {
		 
		 
		public static boolean debugmode;
	 	public LobbyListener ll;
        private GameManager gm;
        private Storage parties;
        private MySQL con;
        public Logger log;
        private UserManager um;
        private WorldGeneration worldgen;
		 
        public onStartup()
        {
            
        }
         
        @Override
        public void onEnable() 
        {
            this.log = this.getLogger();
            getCommand("party").setExecutor(new PartyCommands(this));
            gm = new GameManager();
            parties = new Storage();
            ll = new LobbyListener(this, gm);
            worldgen = new WorldGeneration(gm);
            PluginManager pm = getServer().getPluginManager();
            //System.out.println("Registering LobbyListener");
            pm.registerEvents(ll, this);
            getLogger().info("Plugin Is Enabled");
            getServer().getPluginManager().registerEvents(this,this);
            
            // Config :P. Not sure if correct, I usually use the not-by-bukkit-implemented-way
            getConfig().options().copyDefaults(true);
            saveConfig();
            String host = getConfig().getString("mysql.host");
            String port = getConfig().getString("mysql.port"); // Yes this is actually a String
            String database = getConfig().getString("mysql.database");
            String username = getConfig().getString("mysql.username");
            String password = getConfig().getString("mysql.password");
            
            // Establishing the MySQL connection            
            con = new MySQL(log, "[CC]", host, port, database, username, password);
            try 
            {
                con.open();
                if(!con.checkConnection())
                {
                    throw new SQLException("MySQL server did not respond. Please confirm that the server is up!");
                }
                
                if(!createTables())
                {
                    throw new SQLException("Failed to create tables: cause unknown"); // Either this is thrown or the function throws one from inside
                }
            } 
            catch (SQLException ex)
            {
                log.info("Failed to connect to MySQL server with credentials 'localhost@root' using database 'hw'");
                log.info("Exception thrown: "+ex.getMessage());
                log.info("Disabling plugin...");
                // Our plugin is MySQL dependant right :3?
                //Bukkit.getPluginManager().disablePlugin(this); NOT :D 
                return;
            }
            
            um = new UserManager(this);
        }

        @Override
        public void onDisable() 
        {
            if(con != null && con.checkConnection())
            {
                con.close();
                con = null; // removing any reference just in case
            }
            getLogger().info("Plugin Is Disabled");	
        }	 
        
        public GameManager getGameManager()
        {
            return this.gm;
        }
        
        public Storage getParties()
        {
            return this.parties;
        }
        
        public MySQL getConnection()
        {
            return this.con;
        }
	
        // INCOMPLETE, ITS JUST A PRESET
        private boolean createTables() throws SQLException
        {
            String players = "CREATE TABLE players ( id INT NOT NULL AUTO_INCREMENT,"
                                                 + " PRIMARY KEY(id),"
                                                 + " name VARCHAR(30) NOT NULL"
                                                 + ");";
            String reputation = "CREATE TABLE reputation ( player_id INT NOT NULL,"
                                                       + " reputation FLOAT(4,2) NOT NULL"
                                                       + " FOREIGN KEY(player_id)"
                                                       + "    REFERENCES players(id)"
                                                       + ");";      
            String stats = "CREATE TABLE stats     ( player_id INT NOT NULL,"
                                                 + " points INT DEFAULT 0,"
                                                 + " kills INT DEFAULT 0,"
                                                 + " deaths INT DEFAULT 0,"
                                                 + " onRed INT DEFAULT 0,"
                                                 + " onBlue INT DEFAULT 0,"
                                                 + " FOREIGN KEY(player_id)"
                                                 + "    REFERENCES players(id)"
                                                 + ");";
            // Not sure about friends though
            String friends = "CREATE TABLE friends ( player_id INT NOT NULL,"
                                                 + " friends VARCHAR(500),"
                                                 + " FOREIGN KEY(player_id)"
                                                 + "    REFERENCES players(id)"
                                                 + ");";
            
           return true; // Just so no errors are thrown while testing  
        }
 }
	
