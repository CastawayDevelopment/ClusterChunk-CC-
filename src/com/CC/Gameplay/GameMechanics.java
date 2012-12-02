package com.CC.Gameplay;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.CC.Arenas.Game;
import com.CC.Arenas.GameManager;
import com.CC.Arenas.Team;
import com.CC.General.User;
import com.CC.General.UserManager;
import com.CC.General.onStartup;

public class GameMechanics implements Listener{
	
private GameManager gamemanager;
private onStartup plugin;
private Game playergame;
private UserManager usermanager;
private HashMap<String, Integer> playertoscores = new HashMap<String, Integer>();
    public GameMechanics(onStartup instance) {
    	plugin = instance;
    	gamemanager = plugin.getGameManager();
    	usermanager = plugin.getUserManager();
	}
	
    
    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event){
    	if(event.getEntity() instanceof Player){
    		if(event.getDamager() instanceof Player){
    			User killer = usermanager.getUser((Player)event.getDamager());
    			killer.addKill();
    		}
    	}
    }
	
	@EventHandler
	public void spectateOnDeath(PlayerDeathEvent event){
			Player peter = event.getEntity();
			User player = usermanager.getUser(peter);
		if(gamemanager.isInGame(peter.getName())){
			player.addDeath();
			if(peter.getBedSpawnLocation() != null){
					playergame = gamemanager.getGameByPlayer(peter);
					Team playersteam = playergame.getTeam(peter);
					if(playersteam.equals(Team.BLUE)){
						for(Player p :playergame.getBlueTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has died fighting for your team");
						}
						for(Player p :playergame.getRedTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has been killed");
						}
					}else{
						for(Player p :playergame.getRedTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has died fighting for your team");
						}
						for(Player p :playergame.getBlueTeamPlayers()){
							p.sendMessage(ChatColor.GRAY+ peter.getName() + ChatColor.GREEN + " has been killed");
						}
				
					}
			}else{
				playergame = gamemanager.getGameByPlayer(peter);
				playergame.removePlayer(peter);
				//Something to teleport to dead box of the game :D 
				
				
			}
		}
	}
	
	@EventHandler
	public void noCraftBed(CraftItemEvent event){
		ItemStack result = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		if(gamemanager.isInGame(player)){
			if(result.getType() == Material.BED || result.getType() == Material.BED_BLOCK){
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	  public void onPlayerInteract(PlayerInteractEvent event)
	  {
	    if (event.isCancelled()) return;

	    Player player = event.getPlayer();
	    if ((event.getClickedBlock().getType() == Material.OBSIDIAN) && (event.getMaterial() == Material.FLINT_AND_STEEL))
	    {
	    	event.setCancelled(true);
	    	player.sendMessage(ChatColor.RED + "You may not create Nether portals !");
	    }
	           
	    
	  }

	  @EventHandler(priority=EventPriority.LOWEST)
	  public void enderBlockInteract(PlayerInteractEvent event)
	  {
	    if (event.isCancelled()) return;

	   
	    Player player = event.getPlayer();
	    if ((event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME) && (event.getMaterial() == Material.EYE_OF_ENDER))
	    {
	      
	        event.setCancelled(true);
	        player.sendMessage(ChatColor.RED + "You may not create End portals !");
	      
	    }
	  }
	  
	  @EventHandler
	  public void onBlockBreak(BlockBreakEvent event){
		  Player peter = event.getPlayer();
		  if(gamemanager.isInGame(peter)){
			  if(event.getBlock().getType() == Material.GLASS){
				  peter.sendMessage(ChatColor.RED + "Glass inclosed structures are off limits!");
				  event.setCancelled(true);
			  }
		  }else{
			  event.setCancelled(true);
		  }
	  }
	  
	  
	  //Game Scoring Mechanics 
	public int calculateScoreOnKill(Player killer, Player killed){
		User killedcurrent = usermanager.getUser(killed);
		User killercurrent = usermanager.getUser(killer);
		int killedcurrentpoints = killedcurrent.getPoints();
		int killercurrentpoints = killercurrent.getPoints();
		if(killedcurrentpoints > 4){
			killedcurrent.changePoints(killedcurrentpoints - killedcurrentpoints/killercurrentpoints);	
		}
		//A bit random but thats the way it should be :D 
		int plusscore = (killedcurrentpoints/killercurrentpoints + 1)*killercurrentpoints/killedcurrentpoints + 3;
		killercurrent.changePoints(killercurrentpoints + plusscore);
		return 0;
	}
	
	
	//Out side of game
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		usermanager.createUser(player);
	}
	
	
	
	
	
	
	
	

}