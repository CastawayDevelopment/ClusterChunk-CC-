package com.CC.General;

import com.CC.Arenas.GameManager;
import com.CC.Commands.*;
import com.CC.Listeners.*;
import com.CC.Messages.PlayerMessages;
import com.CC.MySQL.MySQL;
import com.CC.Party.Party;
import com.CC.Party.PartyBattle;
import com.CC.Party.PartyStorage;
import com.CC.WorldGeneration.WorldGeneration;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ClusterChunk extends JavaPlugin implements Listener
{

    public static boolean debugmode = true;
    public LobbyListener ll;
    public PlayerAuthListener pal;
    private GameManager gm;
    private PartyStorage parties;
    private MySQL con;
    public Logger log;
    private UserManager um;
    private WorldGeneration worldgen;
    private PlayerMessages messages;
    private GameMechanicsListener gml;
    private StaffCommands sc;
    int TimeofGame;
    int WarningTime;
    
    public LinkedList<String> queueBlue = new LinkedList<String>();
    public LinkedList<String> queueRed = new LinkedList<String>();
    public LinkedList<PartyBattle> queueVersus = new LinkedList<PartyBattle>();
    public Set<String> partyInQueue = new HashSet<String>();
    
    public static final String PARTY = "ClusterChunkDummy";
    
    public static final int TEAM_SIZE = 2;

    public ClusterChunk()
    {
    }

    @Override
    public void onEnable()
    {
        this.log = this.getLogger();
        parties = new PartyStorage();
        um = new UserManager(this);
        pal = new PlayerAuthListener(this);
        messages = new PlayerMessages();
        //gm needs to be made before worldgen
        gm = new GameManager(this);
        //also worldgen needs to be made before gm
        worldgen = new WorldGeneration(this);
        gm.setWorldGenerator(worldgen);
        ll = new LobbyListener(this);
        gml = new GameMechanicsListener(this);
        sc = new StaffCommands(this);
        getCommand("party").setExecutor(new PartyCommands(this));
        CommandExecutor relationsCommand = new RelationCommand(this);
        getCommand("friend").setExecutor(relationsCommand);
        getCommand("enemy").setExecutor(relationsCommand);
        getCommand("relation").setExecutor(relationsCommand);
        getCommand("endgame").setExecutor(sc);
        getCommand("rep").setExecutor(sc);
        getCommand("kick").setExecutor(sc);
        PluginManager pm = getServer().getPluginManager();
        //System.out.println("Registering LobbyListener");
        pm.registerEvents(ll, this);
        pm.registerEvents(pal, this);
        pm.registerEvents(this, this);
        pm.registerEvents(gml, this);
        getLogger().info("Plugin Is Enabled");
        // Config :P. Not sure if correct, I usually use the not-by-bukkit-implemented-way
        getConfig().options().copyDefaults(true);
        saveConfig();
        String host = getConfig().getString("mysql.host");
        String port = getConfig().getString("mysql.port"); // Yes this is actually a String
        String database = getConfig().getString("mysql.database");
        String username = getConfig().getString("mysql.username");
        String password = getConfig().getString("mysql.password");
        TimeofGame = getConfig().getInt("GameSettings.TimePerGame", 600);
        getConfig().set("GameSettings.TimePerGame", TimeofGame);
        WarningTime = getConfig().getInt("GameSettings.TimeBeforeGameStarts", 120);
        getConfig().set("GameSettings.TimeBeforeGameStarts", WarningTime);
        saveConfig();

        // Establishing the MySQL connection            
        con = new MySQL(log, "[CC]", host, port, database, username, password);
        try
        {
            con.open();
            if (!con.checkConnection())
            {
                throw new SQLException("MySQL server did not respond. Please confirm that the server is up!");
            }

            if (!createTables())
            {
                throw new SQLException("Failed to create tables: cause unknown"); // Either this is thrown or the function throws one from inside
            }
        }
        catch (SQLException ex)
        {
            log.log(Level.SEVERE, "Failed to connect to MySQL server with credentials '{0}@{1}' using database '{2}'", new Object[]{username, host, database});
            log.log(Level.INFO, "Exception thrown: {0}", ex.getMessage());
            log.info("Disabling plugin...");
            // Our plugin is MySQL dependant right :3?
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }


    }

    @Override
    public void onDisable()
    {
        if (con != null && con.checkConnection())
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

    public LobbyListener getLobbies()
    {
        return this.ll;
    }

    public PartyStorage getParties()
    {
        return this.parties;
    }

    public MySQL getConnection()
    {
        return this.con;
    }

    public UserManager getUserManager()
    {
        return this.um;
    }

    public WorldGeneration getWorldGenerator()
    {
        return this.worldgen;
    }

    // INCOMPLETE, ITS JUST A PRESET
    private boolean createTables()
    {
        String players = new StringBuilder("CREATE TABLE players ( id INT NOT NULL AUTO_INCREMENT,")
                .append(" PRIMARY KEY(id),")
                .append(" name VARCHAR(16) NOT NULL")
                .append(");").toString();
        String reputation = new StringBuilder("CREATE TABLE reputation ( player_id INT NOT NULL UNIQUE,")
                .append(" reputation FLOAT(4,2) NOT NULL,")
                .append(" FOREIGN KEY(player_id)")
                .append("    REFERENCES players(id)")
                .append(");").toString();
        String stats = new StringBuilder("CREATE TABLE stats     ( player_id INT NOT NULL UNIQUE,")
                .append(" points INT DEFAULT 0,")
                .append(" kills INT DEFAULT 0,")
                .append(" deaths INT DEFAULT 0,")
                .append(" onRed INT DEFAULT 0,")
                .append(" onBlue INT DEFAULT 0,")
                .append(" FOREIGN KEY(player_id)")
                .append("    REFERENCES players(id)")
                .append(");").toString();
        // Nakama :3
        String relation = new StringBuilder("CREATE TABLE relation ( player_id INT NOT NULL UNIQUE,")
                .append(" rel_id INT NOT NULL,")
                .append(" isfoe BOOLEAN DEFAULT FALSE,")
                .append(" FOREIGN KEY(player_id)")
                .append("    REFERENCES players(id),")
                .append(" FOREIGN KEY(rel_id)")
                .append("    REFERENCES players(id)")
                .append(");").toString();
        if (!getConnection().checkTable("players"))
        {
            getConnection().createTable(players);
        }
        if (!getConnection().checkTable("reputation"))
        {
            getConnection().createTable(reputation);
        }
        if (!getConnection().checkTable("stats"))
        {
            getConnection().createTable(stats);
        }
        if (!getConnection().checkTable("relation"))
        {
            getConnection().createTable(relation);
        }

        return getConnection().checkTable("players") && getConnection().checkTable("reputation")
                && getConnection().checkTable("stats") && getConnection().checkTable("relation");
    }

    public PlayerMessages getMessages()
    {
        return this.messages;
    }

    public int getGameTime()
    {
        return this.TimeofGame;
    }

    public int getWarningTime()
    {
        return this.WarningTime;
    }
    
    /**
     * First {TEAM_SIZE} players are in the first team, 
     * second {TEAM_SIZE} players are in the second team
     */
    public List<String> assembleTeams()
    {
        if(this.queueBlue.isEmpty()) return new ArrayList<String>();
        List<String> team = new ArrayList<String>();
        String first = this.queueBlue.poll();
        int dummyFreq = Collections.frequency(this.queueBlue, PARTY);
        
        boolean blueWasParty = false;
        
        if(first.equals(PARTY))
        {
            fetchParty(team, false);
            if(team.size() == 2*ClusterChunk.TEAM_SIZE)
            {
                return team;
            }
            blueWasParty = true;
        }
        else
        {
            team.add(first);
            if(this.queueBlue.size() - dummyFreq >= ClusterChunk.TEAM_SIZE - 1)
            {
                List<String> removed = new ArrayList<String>();
                for(String p : this.queueBlue)
                {
                    if(team.size() >= ClusterChunk.TEAM_SIZE) break;
                    
                    if(!p.equals(PARTY))
                    {
                        team.add(p);
                        removed.add(p);
                    }
                }
                this.queueBlue.removeAll(removed);
            }
            else
            {
                this.queueBlue.addFirst(first);
                team.clear();
                return team;
            }
        }
        
        int sizeNow = team.size();
        
        if(this.queueRed.isEmpty()) return new ArrayList<String>();
        
        first = this.queueRed.peek();
        dummyFreq = Collections.frequency(this.queueRed, PARTY);
        
        if(first.equals(PARTY))
        {
            this.queueRed.poll();
            fetchParty(team, true);
            if(team.size() == 2*ClusterChunk.TEAM_SIZE)
            {
                return team;
            }
            else if(team.size() == sizeNow)
            {
                if(this.queueRed.size() - dummyFreq >= ClusterChunk.TEAM_SIZE)
                {
                    List<String> removed = new ArrayList<String>();
                    for(String p : this.queueRed)
                    {
                        if(team.size() >= ClusterChunk.TEAM_SIZE*2) break;

                        if(!p.equals(PARTY))
                        {
                            team.add(p);
                            removed.add(p);
                        }
                    }
                    this.queueRed.removeAll(removed);
                }
                else
                {
                    if(blueWasParty)
                    {
                        this.queueBlue.addFirst(PARTY);
                        Party party = this.getParties().getParty(team.get(0));
                        PartyBattle pb = new PartyBattle(party, null);
                        this.queueVersus.addFirst(pb);
                    }
                    else
                    {
                        for(String t : team) this.queueBlue.addFirst(t);
                    }
                    team.clear();
                    return team;
                }
            }
        }
        else
        {
            if(this.queueRed.size() - dummyFreq >= ClusterChunk.TEAM_SIZE)
            {
                List<String> removed = new ArrayList<String>();
                for(String p : this.queueRed)
                {
                    if(team.size() >= ClusterChunk.TEAM_SIZE*2) break;
                    
                    if(!p.equals(PARTY))
                    {
                        team.add(p);
                        removed.add(p);
                    }
                }
                this.queueRed.removeAll(removed);
            }
            else
            {
                if(blueWasParty)
                {
                    this.queueBlue.addFirst(PARTY);
                    Party party = this.getParties().getParty(team.get(0));
                    PartyBattle pb = new PartyBattle(party, null);
                    this.queueVersus.addFirst(pb);
                }
                else
                {
                    for(String t : team) this.queueBlue.addFirst(t);
                }
                team.clear();
                team.clear();
                return team;
            }
        }
        
        // Red now?
        return team;
    }
    
    private void fetchParty(List<String> team, boolean isRed)
    {
        PartyBattle pv = this.queueVersus.poll();
        if(pv != null)
        {
            Party a = pv.getChallenger();
            Party b = pv.getChallengee();
            if(b != null && !isRed)
            {
                team.addAll(b.getMembers());
            }
            else if(b != null)
            {
                return;
            }
            team.addAll(a.getMembers());
        }
    }
}
