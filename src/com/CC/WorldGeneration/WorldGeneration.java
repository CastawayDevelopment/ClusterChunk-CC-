package com.CC.WorldGeneration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.CC.Arenas.Game;
import com.CC.Arenas.GameManager;
import com.CC.General.ClusterChunk;
import java.util.logging.Level;

public class WorldGeneration
{

    private ClusterChunk plugin;
    private GameManager gamemanager;

    public WorldGeneration(ClusterChunk instance)
    {
        plugin = instance;
        gamemanager = plugin.getGameManager();
    }
    //Make sure there is the default world located at /BaseMap/BaseMap

    public boolean newMap(final Game game)
    {
        String MapName = game.getName();
        if (MapName.startsWith("."))
        {
            this.plugin.log.log(Level.SEVERE, "Map name is invalid");
            return false; //Check that the map name is not "..", Could delete server
        }
        File baseMap = new File("./BaseMap/BaseMap");

        File newMapDir = new File("./" + MapName);
        File newRegionsDir = new File(newMapDir, "region");
        if (!Bukkit.unloadWorld(MapName, false) && Bukkit.getWorld(MapName) != null)
        {
            this.plugin.log.log(Level.WARNING, "Could not unload the world");
            return false;
        }

        if (newMapDir.exists())
        {
            deleteMap(newMapDir);
        }
        if (ClusterChunk.debugmode)
        {
            System.out.println("Deleting  " + MapName);
        }

        newRegionsDir.mkdirs();

        try
        {
            if (ClusterChunk.debugmode)
            {
                System.out.println("Copying level.dat for " + MapName);
            }
            copyFile(new File(baseMap, "level.dat"),
                    new File(newMapDir, "level.dat"));
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }

        File[] regionFiles = new File(baseMap, "region").listFiles();
        for (File region : regionFiles)
        {
            File newRegion = new File(newRegionsDir, region.getName());
            try
            {
                if (ClusterChunk.debugmode)
                {
                    System.out.println("Copying region files for " + MapName);
                }
                copyFile(region, newRegion);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //System.out.println("New World Creator1");
        WorldCreator sdfsd = WorldCreator.name(MapName);
        //System.out.println("Creating world1");
        sdfsd.createWorld();
        //System.out.println("World created1");
        game.setRegenerated(true);
        //System.out.println("Blue start");

        loadedWorldDelay(game, MapName);
        //System.out.println("Red end");
        return true;
    }

    private void copyFile(File sourceFile, File destFile) throws IOException
    {
        if (!destFile.exists())
        {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try
        {
            if (ClusterChunk.debugmode)
            {
                System.out.println("Copying over " + sourceFile + " to " + destFile);
            }
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());

        }
        catch(Exception ex)
        {
            System.out.println("An error has occurred while moving files!");
            ex.printStackTrace();
        }
        finally
        {
            if (source != null)
            {
                source.close();
            }
            if (destination != null)
            {
                destination.close();
            }
        }
    }
    //Very dangerous... Do not give the wrong file :D 

    private void deleteMap(File dir)
    {
        File[] files = dir.listFiles();
        for (File d : files)
        {
            if (d.isDirectory())
            {
                deleteMap(d);
            }
            d.delete();
        }
    }

    private void loadedWorldDelay(final Game game, final String MapName)
    {
        try
        {
            game.getRedSpawn().getChunk().load(true);
            game.getBlueSpawn().getChunk().load(true);
            new BukkitRunnable()
            {
                public void run()
                {
                    // For later use, I commented this out
                    /*
                    for (Player p : game.getBlueTeamPlayers())
                    {
                        //System.out.println(p.getName());
                        p.teleport(game.getBlueSpawn(MapName));
                    }
                    //System.out.println("Blue end");
                    //System.out.println("Red start");
                    for (Player p : game.getRedTeamPlayers())
                    {
                        //System.out.println(p.getName());
                        p.teleport(game.getRedSpawn(MapName));
                    }

                    gamemanager.startGameCount(game);*/
                    plugin.getGameManager().setReady(game);
                }
            }.runTaskLater(plugin, 40L);
        }
        catch(Exception ex)
        {
            System.out.println("World was not loaded properly!");
        }
    }
}