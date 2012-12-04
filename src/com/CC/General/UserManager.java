package com.CC.General;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
	
	private void loadPlayer(String player){
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
        
         try
        {
            ResultSet players = main.getConnection().query("SELECT `id` FROM players WHERE name = '"+player+"'");
            if(players.next())
            {
                // Update
                int id = players.getInt("id"); // Might want to store this for faster processing later on
                ResultSet rep = main.getConnection().query("SELECT `reputation` FROM `reputation` WHERE player_id = "+id+";");
                if(rep.next())
                {
                    reputation = rep.getInt("reputation");
                }
                else
                {
                }
                rep.close(); // Freeing the memory, just in case
                ResultSet stats = main.getConnection().query("SELECT `points` AS p, `kills` AS k, `deaths` AS d, `onRed` AS r, `onBlue` AS b FROM `stats` WHERE player_id = "+id+";");
                if(stats.next())
                {
                    points = stats.getInt("p");
                    deaths = stats.getInt("d");
                    kills = stats.getInt("k");
                    red = stats.getInt("r");
                    blue = stats.getInt("b");
                }
                stats.close();
                ResultSet rfriends = main.getConnection().query("SELECT `rel_id` as fid, `isfoe` FROM `friends` "
                                                              +"WHERE id = "+id+" INNER JOIN `friends` "
                                                              +"ON friends.player_id = (SELECT `player_id` FROM `friends` WHERE `rel_id` = fid AND player_id = "+id+");");
                if(rfriends.next())
                {
                    do
                    {
                        try
                        {
                            int fid = rfriends.getInt("fid");
                            ResultSet friend = main.getConnection().query("SELECT `name` FROM `players` WHERE id = "+fid+";");
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
                // not found
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
	}
	
    // Should be on onQuit and onDisable, just saying
	public void savePlayers()
    {
        for(User p : players.values())
        {
            //savePlayer(p);
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
}
