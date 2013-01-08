package com.CC.General;

import com.CC.MySQL.MySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UserManager 
{
    
        private onStartup main;
	HashMap<String, User> players;
	
	public UserManager(onStartup main)
        {
            players = new HashMap<String, User>();
            this.main = main;
	}
	
	public boolean createUser(Player player)
        {
            if(players.containsKey(player.getName())) 
            {
                return false;
            }
            User user = new User(player);
            players.put(player.getName(), user);
            return true;
	}
	
	public User getUser(Player player){
		if(players.containsKey(player.getName())){
			return this.players.get(player.getName());
		}else{
			return null;
		}
	}
	
	public void loadPlayers(ArrayList<String> playerNames)
        {
		//Get all of the PlayerNames from MySQL DataBase
		for(String s : playerNames){
			
			loadPlayer(s);
		}
		
	}
	
	private void loadPlayer(String player)
        {
            User user = players.get(player);
            if(user == null)
            {
                Player p = Bukkit.getPlayer(player);
                if(p == null || !createUser(p))
                {
                    // Player is not online or failed creating
                    return;
                }
                else
                {
                    user = players.get(player);
                    if(user == null) // Second check to be absolutely sure
                    {
                        // Definitely something wrong, with either the plugin or the player is just offline
                        return;
                    }
                }
            }
        
            // Initializing variables, setting reputation to 10 (as standard, might change)
            int points = 0,reputation = 10,deaths = 0,kills = 0,red = 0,blue = 0;

            ArrayList<String> friends = new ArrayList<String>();
            ArrayList<String> enemies = new ArrayList<String>();
            
            boolean isNew = false;
            
            try
            {
                PreparedStatement _players = main.getConnection().prepare("SELECT `id` FROM players WHERE name = ?;");
                _players.setString(1, player);
                ResultSet players = _players.executeQuery();
                if(players.next())
                {
                    // Update
                    int id = players.getInt("id"); // Might want to store this for faster processing later on
                    PreparedStatement _rep = main.getConnection().prepare("SELECT `reputation` FROM `reputation` WHERE player_id = ?;");
                    _rep.setInt(1, id);
                    ResultSet rep = _rep.executeQuery();
                    if(rep.next())
                    {
                        reputation = rep.getInt("reputation");
                    }
                    else
                    {
                    }
                    rep.close(); // Freeing the memory, just in case
                    PreparedStatement _stats = main.getConnection().prepare("SELECT `points` AS p, `kills` AS k, `deaths` AS d, `onRed` AS r, `onBlue` AS b FROM `stats` WHERE player_id = ?;");
                    _stats.setInt(1, id);
                    ResultSet stats = _stats.executeQuery();
                    if(stats.next())
                    {
                        points = stats.getInt("p");
                        deaths = stats.getInt("d");
                        kills = stats.getInt("k");
                        red = stats.getInt("r");
                        blue = stats.getInt("b");
                    }
                    stats.close();
                    
                    String friends_q = new StringBuilder("SELECT `rel_id` as fid, `isfoe` FROM `friends` ")
                                        .append("WHERE id = ? INNER JOIN `friends` ")
                                        .append("ON friends.player_id = (SELECT `player_id` FROM `friends` WHERE `rel_id` = fid AND player_id = ?);").toString();
                    
                    PreparedStatement _rfriends = main.getConnection().prepare(friends_q);
                    _rfriends.setInt(1, id);
                    _rfriends.setInt(2, id);
                    
                    ResultSet rfriends = _rfriends.executeQuery();
                    if(rfriends.next())
                    {
                        do
                        {
                            try
                            {
                                int fid = rfriends.getInt("fid");
                                PreparedStatement _friend = main.getConnection().prepare("SELECT `name` FROM `players` WHERE id = ?;");
                                _friend.setInt(1, fid);
                                ResultSet friend = _friend.executeQuery();
                                String name = friend.getString("name");
                                if(rfriends.getBoolean("isfoe"))
                                {
                                    enemies.add(name);
                                }
                                else
                                {
                                    friends.add(name);
                                }
                            }
                            catch(SQLException exc)
                            {
                                // Error,  but ignore it. You can log it if you want though
                            }

                        }while(rfriends.next());
                    }
                }
                else
                {
                    isNew = true;
                }
            }
            catch(SQLException ex)
            {
                // log it. It failed!
                // Actually, might not log it
            }
            user.changePoints(points);
            user.changeReputation(reputation);
            user.setDeaths(deaths);
            user.setFriendsList(friends);
            //user.setFriendRequestsPendingList(/*Get from MySQL*/); // Remove this from User.java
            user.setEnemiesList(enemies);
            user.setKills(kills);
            user.setTimeOnBlue(blue);
            user.setTimesOnRed(red);
            if(isNew)
            {
                main.log.info("Uploading new player data to MySQL");
                try
                {
                    PreparedStatement stmt = main.getConnection().prepare("INSERT INTO players(`name`) VALUES (?)");
                    stmt.setString(1, player);
                    stmt.executeUpdate();
                }
                catch(SQLException ex)
                {
                    main.log.info("Failed to add new player");
                    return;
                }
                updatePlayer(user, "stats");
                updatePlayer(user, "reputation");
                updatePlayer(user, "relation");
            }
	}
	
    // Should be on onQuit and onDisable, just saying
	public void savePlayers()
    {
        for(User p : players.values())
        {
            
        }
    }
    
    /*
    *  DO NOT USE THIS, UNFINISHED and some useless stuff to let me push it
    **//*
    private void savePlayer(User user)
    {
        int p = user.getPoints();
        int rep = user.getReputation();
        int d = user.getDeaths();
        ArrayList<String> f = user.getFriends();
        ArrayList<String> e = user.getEnemies();
        int k = user.getKills();
        int b = user.getTimesPlayedOnBlueTeam();
        int r = user.getTimesPlayedOnRedTeam();
        // Initializing variables, setting reputation to 10 (as standard, might change)   
        try
        {
            ResultSet players = main.getConnection().query("SELECT `id` FROM players WHERE name = '"+player+"'");
            if(players.next())
            {
                // Update
                int id = players.getInt("id"); // Might want to store this for faster processing later on
                try
                {
                    main.getConnection().query("UPDATE `reputation` SET `reputation` = "+rep+" WHERE player_id = "+id+";");
                }
                catch(SQLException ex)
                {
                    try
                    {
                        main.getConnection().query("INSERT INTO `reputation`(`player_id`, `reputation`) VALUES("+rep+", "+id+");");
                    }
                    catch(SQLException exc)
                    {
                         // Totally failed
                         main.log.severe(exc.getMessage());
                    }
                }
                
                
                
                ResultSet friends = main.getConnection().query("SELECT `rel_id` as fid, `isfoe` FROM `friends` "
                                                              +"WHERE id = "+id+" INNER JOIN `friends` "
                                                              +"ON friends.player_id = (SELECT `player_id` FROM `friends` WHERE `rel_id` = fid AND player_id = "+id+");");
                if(friends.next())
                {
                    do
                    {
                        try
                        {
                            int fid = friends.getInt("fid");
                            ResultSet friend = main.getConnection().query("SELECT `name` FROM `players` WHERE id = "+ifd+";");
                            String name = friend.getString("name");
                            if(friends.getBoolean("isfoe"))
                            {
                                enemies.add(name);
                            }
                            else
                            {
                                friends.add(name);
                            }
                        }
                        catch(SQLException exc)
                        {
                            // Error,  but ignore it. You can log it if you want though
                        }
                        
                    }while(friends.next());
                }
            }
            else
            {
                // not found, insert
            }
        }
        catch(SQLException ex)
        {
            // log it. It failed!
            // Actually, might not log it
        }
	}
	*/
        
        public void updatePlayer(Player p, final String table)
        {
            User u = getUser(p);
            if(u != null)
            {
                updatePlayer(u, table);
            }
        }
        
        public void updatePlayer(final User user, final String table)
        {
            final MySQL con = main.getConnection();
            new BukkitRunnable()
            {
       
                public void run()
                {
                    try
                    {
                        PreparedStatement _player = con.prepare("SELECT * FROM players WHERE name = ?");
                        _player.setString(1, user.getPlayer().getName());
                        ResultSet player = _player.executeQuery();
                        if(!player.next())
                        {
                            return;
                        }
                        
                        int id = player.getInt("id");
                        
                        if(table.equals("reputation"))
                        {
                            int rep = user.getReputation();
                            ResultSet repset = con.query(new StringBuilder("SELECT * FROM `reputation` WHERE `player_id` = ").append(id).append(";").toString());
                            if(repset.next())
                            {
                                con.query(new StringBuilder("UPDATE `reputation` SET `reputation` = ").append(rep).append(" WHERE player_id = ").append(id).append(";").toString());
                            }
                            else
                            {
                                con.query(new StringBuilder("INSERT INTO `reputation`(`player_id`,`reputation`) VALUES(").append(id).append(", ").append(rep).append(");").toString());
                            }
                            repset.close();
                        }
                        else if(table.equals("stats"))
                        {
                            int p = user.getPoints();
                            int k = user.getKills();
                            int d = user.getDeaths();
                            int r = user.getTimesPlayedOnRedTeam();
                            int b = user.getTimesPlayedOnBlueTeam();
                            ResultSet statset = con.query(new StringBuilder("SELECT * FROM `stats` WHERE `player_id` = ").append(id).append(";").toString());
                            if(statset.next())
                            {
                                con.query(new StringBuilder("UPDATE `stats` SET points = ").append(p).append(", kills = ").append(k).append(", deaths = ").append(d).append(", onRed = ").append(r).append(", onBlue = ").append(b).append(" WHERE player_id = ").append(id).append(";").toString());
                            }
                            else
                            {
                                con.query(new StringBuilder("INSERT INTO `stats`(`player_id`,`points`, `kills`, `deaths`, `onRed`, `onBlue`) VALUES(").append(id).append(",").append(p).append(",").append(k).append(",").append(d).append(",").append(r).append(",").append(b).append(");").toString());   
                            }
                            statset.close();
                        }
                        else if(table.equals("relation"))
                        {
                            // Not yet implemented
                        }
                        else
                        {
                            // Table not found
                        }
                    }
                    catch(SQLException ex)
                    {
                        main.log.info("Failed to update "+table);
                        ex.printStackTrace();
                    }
                }
            }.runTaskLaterAsynchronously(main, 0L);
        }
}
