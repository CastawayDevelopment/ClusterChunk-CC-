package com.CC.WorldGeneration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.CC.Arenas.Game;
import com.CC.Arenas.GameManager;
import com.CC.General.onStartup;

public class WorldGeneration {
	private onStartup plugin;
	private GameManager gamemanager;

public WorldGeneration(onStartup instance){
		plugin = instance;
		gamemanager = plugin.getGameManager();
	}
	//Make sure there is the default world located at /BaseMap/BaseMap
	
	public boolean newMap(String MapName, Game game) {
		if(MapName.startsWith(".")) return false; //Check that the map name is not "..", Could delete server
		
		File baseMap = new File("./BaseMap/BaseMap");
		
		File newMapDir = new File("./" + MapName);
		File newRegionsDir = new File(newMapDir,  "region");
		if(!Bukkit.unloadWorld(MapName, false) && Bukkit.getWorld(MapName) != null) return false;

		if (newMapDir.exists())
			deleteMap(newMapDir);
		if(onStartup.debugmode){
			System.out.println("Deleting  " + MapName);
		}
		
		newRegionsDir.mkdirs();
		
		try {
		if(onStartup.debugmode){
			System.out.println("Copying level.dat for " + MapName);
		}
			copyFile(new File(baseMap, "level.dat"), 
					new File(newMapDir, "level.dat"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		File[] regionFiles = new File(baseMap, "region").listFiles();
		for(File region : regionFiles){
			File newRegion = new File(newRegionsDir, region.getName());
			try {
				if(onStartup.debugmode){
					System.out.println("Copying region files for " + MapName);
				}
				copyFile(region, newRegion);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("New World Creator1");
		WorldCreator sdfsd = WorldCreator.name(MapName);
		//S/ystem.out.println("Creating world1");
		sdfsd.createWorld();
		//System.out.println("World created1");
		game.setRegenerated(true);
		//System.out.println("Blue start");
		for(Player p : game.getBlueTeamPlayers()){
			//System.out.println(p.getName());
			p.teleport(game.getBlueSpawn(MapName));
			
		}
		//System.out.println("Blue end");
		//System.out.println("Red start");
		for(Player p : game.getRedTeamPlayers()){
			//System.out.println(p.getName());
			p.teleport(game.getRedSpawn(MapName));
		}
		gamemanager.startGameCount(game);
		//System.out.println("Red end");
		return true;
		}
	
	
	
	
	
	
	
	private void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	    if(onStartup.debugmode){
	    	System.out.println("Copying over " + sourceFile + " to " + destFile);
	    }
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	        
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	//Very dangerous... Do not give the wrong file :D 
	private void deleteMap(File dir) {
		File[] files = dir.listFiles();
		for(File d : files){
			if(d.isDirectory()){
				deleteMap(d);
			}
			d.delete();
		}
	}
	
	
	

}