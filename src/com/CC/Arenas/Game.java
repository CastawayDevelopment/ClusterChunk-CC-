package com.CC.Arenas;

import com.CC.Enums.Team;
import com.CC.General.ClusterChunk;
import com.CC.General.User;
import java.util.ArrayList;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import static org.bukkit.ChatColor.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.scheduler.BukkitRunnable;

public class Game
{

    String name;
    ArrayList<String> redTeam;
    ArrayList<String> blueTeam;
    public boolean regenerated;
    public boolean started;
    private boolean ended;
    private GameManager gm;
    int TimeofGame;
    int WarningTime;
    ClusterChunk plugin;
    public int Countdown;
    public int GameTimer;

    public Game(String gameName, ClusterChunk instance)
    {
        plugin = instance;
        this.name = gameName;
        TimeofGame = plugin.getGameTime();
        WarningTime = plugin.getWarningTime();
        started = false;
        gm = plugin.getGameManager();
        redTeam = new ArrayList<String>();
        blueTeam = new ArrayList<String>();
    }

    public String getName()
    {
        return this.name;
    }

    public ArrayList<String> getPlayers()
    {
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(redTeam);
        ret.addAll(blueTeam);
        return ret;
    }

    public Team getTeam(Player p)
    {
        return getTeam(p.getName());
    }

    public Team getTeam(String name)
    {
        if (redTeam.contains(name))
        {
            return Team.RED;
        }
        else if (blueTeam.contains(name))
        {
            return Team.BLUE;
        }
        return Team.NONE;
    }

    public ArrayList<String> getRedTeam()
    {
        return redTeam;
    }

    public ArrayList<Player> getRedTeamPlayers()
    {
        ArrayList<Player> player = new ArrayList<Player>();
        for (String s : redTeam)
        {
            Player p = Bukkit.getPlayer(s);
            player.add(p);
        }
        return player;
    }

    public ArrayList<String> getBlueTeam()
    {
        return blueTeam;
    }

    public ArrayList<Player> getBlueTeamPlayers()
    {
        ArrayList<Player> player = new ArrayList<Player>();
        for (String s : blueTeam)
        {
            Player p = Bukkit.getPlayer(s);
            player.add(p);
        }
        return player;
    }

    public boolean removePlayer(String string)
    {
        Team team = getTeam(string);
        if (team.equals(Team.BLUE))
        {
            blueTeam.remove(string);
            gm.removePlayerFromGame(string);
            if (blueTeam.isEmpty() && !this.ended)
            {
                gm.endGame(this.name, Team.RED);
            }
            return true;
        }
        else if (team.equals(Team.RED))
        {
            redTeam.remove(string);
            gm.removePlayerFromGame(string);
            if (redTeam.isEmpty() && !this.ended)
            {
                gm.endGame(this.name, Team.BLUE);
            }
            return true;
        }
        return false;

    }

    public boolean removePlayer(Player player)
    {
        return removePlayer(player.getName());

    }
    
    public void setEnded(boolean flag)
    {
        this.ended = flag;
    }

    public void setRegenerated(boolean trueorfalse)
    {
        regenerated = trueorfalse;
    }

    public void addRedPlayer(String playername)
    {
        Player player = Bukkit.getServer().getPlayer(playername);
        redTeam.add(playername);
        player.sendMessage(new StringBuilder(RED.toString()).append("You have succesfully join the red team!").toString());
        gm.playerJoinGame(playername, this.name);
    }

    public void addBluePlayer(String playername)
    {
        Player player = Bukkit.getServer().getPlayer(playername);
        blueTeam.add(playername);
        player.sendMessage(new StringBuilder(BLUE.toString()).append("You have succesfully join the blue team!").toString());
        gm.playerJoinGame(playername, this.name);
    }

    public Location getRedSpawn() throws Exception
    {
        World w = Bukkit.getWorld(this.name);
        if(w == null)
        {
            throw new Exception("World not loaded");
        }
        return new Location(w, -866, 143, -762);
    }
    //Just for now until the actual spawn locations are found;

    /**
     * When the new spawn locations are found it will be World == Arena Name and
     * than the location of the spawn for the current team
     */
    public Location getBlueSpawn() throws Exception
    {
        World w = Bukkit.getWorld(this.name);
        if(w == null)
        {
            throw new Exception("World not loaded");
        }
        return new Location(w, -936, 143, -762); 
    }
    //Just for now until the actual spawn locations are found;

    /**
     * When the new spawn locations are found it will be World == Arena Name and
     * then the location of the spawn for the current team
     */
    public void startGameCountdown()
    {
        Location b, r;
        try
        {
            b = this.getBlueSpawn();
            r = this.getRedSpawn();
        }
        catch(Exception ex)
        {
            plugin.getLogger().log(Level.WARNING, "One of the spawnpoints was not set in {0}", this.name);
            return;
        }
        
        Player player;
        
        for(String p : this.getBlueTeam())
        {
            player = Bukkit.getPlayer(p);
            if(player != null)
            {
                player.teleport(b, TeleportCause.PLUGIN);
                User user = plugin.getUserManager().getUser(p);
                user.addTimeOnBlue();
                user.changeLatestGame(this.getName());
                plugin.getUserManager().updatePlayer(p, "stats");
                player = null;
            }
        }
        
        for(String p : this.getRedTeam())
        {
            player = Bukkit.getPlayer(p);
            if(player != null)
            {
                player.teleport(r, TeleportCause.PLUGIN);
                User user = plugin.getUserManager().getUser(p);
                user.addTimeOnRed();
                user.changeLatestGame(this.getName());
                plugin.getUserManager().updatePlayer(p, "stats");
                player = null;
            }
        }
        
        new BukkitRunnable()
        {
            int count = WarningTime;

            public void run()
            {


                if (count == WarningTime)
                {
                    sendMessageAll(ChatColor.GREEN + "" + WarningTime / 60 + " minutes until the game starts!");
                }

                if (count == WarningTime / 2)
                {
                    sendMessageAll(ChatColor.GREEN + "" + WarningTime / 60 / 2 + " minute until the game starts!");
                }

                if (count == 30 || count == 20 || (count < 11 && count > 1))
                {
                    sendMessageAll(ChatColor.GREEN + "" + count + " seconds until game starts!");
                }

                if (count == 1)
                {
                    sendMessageAll(ChatColor.GREEN + "" + count + " second until game starts!");
                }

                if (count == 0)
                {
                    sendMessageAll(ChatColor.GREEN + "The game has now started!");
                    started = true;
                    startGameTimer();
                    cancel();
                }
                count--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void startGameTimer()
    {
        GameTimer = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            int count = TimeofGame;

            public void run()
            {


                if (count == TimeofGame / 2)
                {
                    sendMessageAll(ChatColor.GREEN + "Halftime! " + TimeofGame / 60 / 2 + " minutes until the game ends!");
                }

                if (count / 60 == 2)
                {
                    sendMessageAll(ChatColor.GREEN + "2 minutes until the game ends!");
                }

                if (count == 30 || count == 20 || (count < 11 && count > 1))
                {
                    sendMessageAll(ChatColor.GREEN + "" + count + " seconds until game ends!");
                }

                if (count == 1)
                {
                    sendMessageAll(ChatColor.GREEN + "" + count + " second until game ends!");
                }

                if (count == 0)
                {
                    sendMessageAll(ChatColor.RED + "The game has ended");
                    gm.teleportToSpawn(getRedTeamPlayers());
                    gm.teleportToSpawn(getBlueTeamPlayers());
                    started = false;
                    gm.endGame(name, getWinningTeam());
                    Bukkit.getScheduler().cancelTask(GameTimer);
                }
                count--;
            }
        }, 0L, 20L);
    }

    public Team getWinningTeam()
    {
        if (getBlueTeamPlayers().size() > getRedTeamPlayers().size())
        {
            return Team.BLUE;
        }
        else if (getBlueTeamPlayers().size() < getRedTeamPlayers().size())
        {
            return Team.RED;
        }
        else
        {
            return Team.NONE;
        }
    }

    public void sendMessageAll(String string)
    {
        System.out.println(" sendMessage All");

        for (String s : getPlayers())
        {
            //System.out.println(plugin.getServer());
            //System.out.println(Bukkit.getServer());
            if (plugin.getServer().getPlayer(s) == null)
            {
                System.out.println("Player " + s + " is null");
                continue;
            }
            Bukkit.getPlayer(s).sendMessage(string);
        }
    }

    public void sendMessageBlue(String string)
    {
        for (Player p : getBlueTeamPlayers())
        {
            p.sendMessage(string);
        }
    }

    public void sendMessageRed(String string)
    {
        for (Player p : getRedTeamPlayers())
        {
            p.sendMessage(string);
        }
    }
}
