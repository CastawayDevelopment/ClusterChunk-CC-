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
	
	public void unloadPlayers(){
		for(int i = 0; i <= players.size(); i++){
			players.remove(players.get(i));
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
                                                 .append("WHERE id = ?;").toString();
                    
                    PreparedStatement _rfriends = main.getConnection().prepare(friends_q);
                    _rfriends.setInt(1, id);
                    
                    ResultSet rfriends = _rfriends.executeQuery();
                    while(rfriends.next())
                    {
                        try
                        {
                            int fid = rfriends.getInt("fid");
                            PreparedStatement _mutual = main.getConnection().prepare("SELECT `rel_id` FROM `friends` WHERE id = ?;");
                            _mutual.setInt(1, fid);
                            ResultSet mutual = _mutual.executeQuery();
                            if(!mutual.next())
                            {
                                // Not mutual
                                continue;
                            }
                            
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
        
    public void updatePlayer(Player p, final String table)
    {
        User u = getUser(p);
        if(u != null)
        {
            updatePlayer(u, table);
        }
    }

    public void updatePlayer(final User u, final String table)
    {
        final MySQL con = main.getConnection();
        final User user = u.clone();
        new BukkitRunnable()
        {

            public void run()
            {
                try
                {
                    PreparedStatement _player = con.prepare("SELECT * FROM players WHERE name = ?");
                    _player.setString(1, user.getName());
                    ResultSet player = _player.executeQuery();
                    if(!player.next())
                    {
                        return;
                    }

                    int id = player.getInt("id");

                    if(table.equals("reputation"))
                    {
                        int rep = user.getReputation();
                        con.query(new StringBuilder("UPDATE `reputation` SET `reputation` = ").append(rep).append(", player_id = ").append(id).append(";").toString());
                    }
                    else if(table.equals("stats"))
                    {
                        int p = user.getPoints();
                        int k = user.getKills();
                        int d = user.getDeaths();
                        int r = user.getTimesPlayedOnRedTeam();
                        int b = user.getTimesPlayedOnBlueTeam();
                        con.query(new StringBuilder("REPLACE `stats` SET points = ").append(p).append(", kills = ").append(k).append(", deaths = ").append(d).append(", onRed = ").append(r).append(", onBlue = ").append(b).append(", player_id = ").append(id).append(";").toString());                        
                    }
                    else if(table.equals("relation"))
                    {
                        ArrayList<String> friends = (ArrayList<String>) user.getFriends().clone();
                        ArrayList<String> enemies = (ArrayList<String>) user.getEnemies().clone();
                        PreparedStatement _cur = con.prepare("SELECT `rel_id`, `isfoe` FROM `relation` WHERE player_id = ?");
                        _cur.setInt(1, id);
                        ResultSet cur = _cur.executeQuery();
                        int rel_id;
                        String relname;
                        boolean isfoe;
                        PreparedStatement _relupdate = con.prepare("UPDATE `relation` SET isfoe = ? WHERE (player_id = ? AND rel_id = ?) OR (rel_id = ? AND player_id = ?)");
                        PreparedStatement _reldel = con.prepare("DELETE FROM `relation` WHERE (player_id = ? AND rel_id = ?) OR (rel_id = ? AND player_id = ?)");
                        PreparedStatement _relinsert = con.prepare("INSERT INTO `relation`(`player_id`, `rel_id`, `isfoe`) VALUES(?, ?, ?)");
                        PreparedStatement _getname = con.prepare("SELECT name FROM `players` WHERE id = ?");
                        PreparedStatement _getid = con.prepare("SELECT id FROM `players` WHERE name = ?");
                        
                        while(cur.next())
                        {
                            rel_id = cur.getInt(0);
                            _getname.setInt(0, rel_id);
                            ResultSet getname = _getname.executeQuery();
                            if(!getname.next())
                            {
                                continue;
                            }
                            relname = getname.getString(0);
                            isfoe = cur.getBoolean(1);
                            if((friends.contains(relname) && isfoe) || (enemies.contains(relname) && !isfoe))
                            {
                                // Update both
                                _relupdate.setBoolean(0, !isfoe);
                                _relupdate.setInt(1, id);
                                _relupdate.setInt(2, rel_id);
                                _relupdate.setInt(3, id);
                                _relupdate.setInt(4, rel_id);
                                _relupdate.executeUpdate();
                                friends.remove(relname);
                                enemies.remove(relname);
                            }
                            else
                            {
                                // Apparently, neither friends or enemies
                                _reldel.setInt(1, id);
                                _reldel.setInt(2, rel_id);
                                _reldel.setInt(3, id);
                                _reldel.setInt(4, rel_id);
                                _reldel.executeUpdate();
                            }
                        }
                        ArrayList<String> relations = new ArrayList<String>();
                        relations.addAll(friends);
                        relations.addAll(enemies);
                        for(String rel : relations)
                        {
                            _getid.setString(0, rel);
                            ResultSet getid = _getid.executeQuery();
                            if(!getid.next())
                            {
                                continue;
                            }
                            rel_id = getid.getInt(0);
                            isfoe = enemies.contains(rel);
                            _relinsert.setInt(0, id);
                            _relinsert.setInt(1, rel_id);
                            _relinsert.setBoolean(2, isfoe);
                            _relinsert.executeUpdate();
                            _relinsert.setInt(0, rel_id);
                            _relinsert.setInt(1, id);
                            _relinsert.setBoolean(2, isfoe);
                            _relinsert.executeUpdate();
                        }
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
