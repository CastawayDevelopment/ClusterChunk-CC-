package com.CC.WorldGeneration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class WorldGeneration {
	//Make sure there is the default world located at /BaseMap/BaseMap
	
	public static boolean newMap(String MapName) {
		if(MapName.startsWith(".")) return false; //Check that the map name is not "..", Could delete server
		File baseMap = new File("./BaseMap/BaseMap");
		
		File newMapDir = new File("./" + MapName);
		File newRegionsDir = new File(newMapDir,  "region");
		if(!Bukkit.unloadWorld(MapName, false) && Bukkit.getWorld(MapName) != null) return false;

		if (newMapDir.exists())
			deleteMap(newMapDir);
		
		newRegionsDir.mkdirs();
		
		try {
			copyFile(new File(baseMap, "level.dat"), 
					new File(newMapDir, "level.dat"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		File[] regionFiles = new File(baseMap, "region").listFiles();
		for(File region : regionFiles){
			File newRegion = new File(newRegionsDir, region.getName());
			try {
				copyFile(region, newRegion);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Bukkit.getServer().createWorld(new WorldCreator(MapName));
		return true;
		}
	
	
	
	
	
	
	
	private static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
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
	private static void deleteMap(File dir) {
		File[] files = dir.listFiles();
		for(File d : files){
			if(d.isDirectory()){
				deleteMap(d);
			}
			d.delete();
		}
	}
	
	
	

}
