package com.CC.Arenas;

import com.CC.Enums.Team;
import com.CC.General.ClusterChunk;
import com.CC.WorldGeneration.WorldGeneration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager
{

    private HashMap<String, String> players;
    private HashMap<String, Game> games;
    private ClusterChunk plugin;
    private WorldGeneration worldgen;
    
    private LinkedList<String> ready;

    public GameManager(ClusterChunk instance)
    {
        plugin = instance;

        //worldgen = plugin.getWorldGenerator();
        this.games = new HashMap<String, Game>();
        this.players = new HashMap<String, String>();
        
        this.ready = new LinkedList<String>();
        
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                while(!ready.isEmpty())
                {
                    List<String> players = plugin.assembleTeams();
                    if(!players.isEmpty())
                    {
                        Game g = getGame(ready.pop());
                        int i = 0;
                        for(String player : players)
                        {
                            if(i >= (ClusterChunk.TEAM_SIZE)) g.addRedPlayer(player);
                            else g.addBluePlayer(player);
                            i++;
                        }
                        g.startGameCountdown();
                    }
                    else
                    {
                        break;
                    }
                }
                if(ready.isEmpty() && plugin.getGameManager().getGames().values().size() < plugin.getConfig().getInt("amount-of-games", 1))
                {
                    String newArena = plugin.getConfig().getString("game-world-prefix", "CC")+"_"+plugin.getGameManager().getGames().values().size()+1;
                    if(createGame(newArena))
                    {
                        plugin.getWorldGenerator().newMap(getGame(newArena));
                    }
                }
            }
        }.runTaskTimer(plugin, 40, 200);
    }

    public void setWorldGenerator(WorldGeneration world)
    {
        worldgen = world;
    }

    public Game getGame(String name)
    {
        return this.games.get(name);
    }

    public boolean isGame(String name)
    {
        return this.games.containsKey(name);
    }

    public boolean createGame(String name)
    {
        if (this.games.containsKey(name))
        {
            return false;
        }
        Game g = new Game(name, plugin);
        this.games.put(name, g);
        return true;
    }

    public void createMap(Game g)
    {
        worldgen.newMap(g);
    }

    public boolean removeGame(String name)
    {
        if (!this.games.containsKey(name))
        {
            return false;
        }
        this.games.remove(name);
        return true;
    }

    public Game getGameByPlayer(Player p)
    {
        return getGameByPlayer(p.getName());
    }

    /*
     *   Can return null!
     **/
    public Game getGameByPlayer(String pname)
    {
        if (players.containsKey(pname))
        {
            return games.get(players.get(pname));
        }
        return null;
    }

    public boolean isInGame(Player peter)
    {
        return peter != null && isInGame(peter.getName());
    }

    public boolean isInGame(String string)
    {
        if (players.containsKey(string))
        {
            if (games.containsKey(players.get(string)))
            {
                return true;
            }
        }
        return false;
    }
    
    private void endGame(final Game game, String reason)
    {
        game.setEnded(true);
        game.sendMessageAll(reason);
        teleportToSpawn(game.getBlueTeamPlayers());
        teleportToSpawn(game.getRedTeamPlayers());
        for (Player p : game.getBlueTeamPlayers())
        {
            game.removePlayer(p);
            p.sendMessage(reason);
        }

        for (Player p : game.getRedTeamPlayers())
        {
            game.removePlayer(p);
            p.sendMessage(reason);
        }
        game.setEnded(false);
        Bukkit.broadcastMessage(new StringBuilder(GRAY.toString()).append(game.getName()).append(GREEN).append(" is being regenerated. You may experience lag").toString());
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(!worldgen.newMap(game))
                {
                    Bukkit.broadcastMessage("An error has occurred while generating the new world. Please check the logs");
                }
            }
        }.runTaskLater(plugin, 5L);

    }

    public void endGame(String gameName, Team winningTeam)
    {
        endGame(getGame(gameName), winningTeam);
    }
    
    public void endGame(Game game, Team winningTeam)
    {
        String reason = new StringBuilder("The match has ended: ").append(winningTeam.getName()).append(" has won").toString();
        endGame(game, reason);
    }

    public void endGame(String gameName, String reason)
    {
        reason = new StringBuilder("Game has been ended by an administrator for the following reason: ").append(reason).toString();
        endGame(getGame(gameName), reason);
    }

    public ArrayList<Game> getOpenGames()
    {
        ArrayList<Game> opengames = new ArrayList<Game>();
        for (Game g : games.values())
        {
            if (g.getPlayers().isEmpty())
            {
                if (g.regenerated)
                {
                    opengames.add(g);
                    g.setRegenerated(false);
                }
            }
        }
        return opengames;
    }

    public HashMap<String, Game> getGames()
    {
        return games;
    }

    public void playerJoinGame(String string, String name)
    {
        players.put(string, name);
    }

    public void removePlayerFromGame(String string)
    {
        players.remove(string);
    }

    public void startGameCount(Game game)
    {
        game.startGameCountdown();
    }

    public void teleportToSpawn(ArrayList<Player> players)
    {
        Location loc = getLobby();
        for (Player p : players)
        {
            p.teleport(loc);
        }
    }
    
    public void setReady(Game g)
    {
        this.ready.addLast(g.getName());
    }
    
    public static Location getLobby()
    {
        return new Location(Bukkit.getWorld("lobby"), 330, 231, 332);
    }
}