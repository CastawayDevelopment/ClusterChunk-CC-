package com.CC.Listeners;

import com.CC.General.User;
import com.CC.General.onStartup;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Fireblast709
 */
public class PlayerAuthListener implements Listener
{
    private onStartup main;
    
    public PlayerAuthListener(onStartup main)
    {
        this.main = main;
    }
    
    
    //Out side of game
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        main.log.info("Joining player: "+player.getName());
        if(main.getUserManager().createUser(player))
        {
            main.log.info("new user created, loading it into");
            ArrayList<String> p = new ArrayList<String>();
            p.add(player.getName());
            main.getUserManager().loadPlayers(p);
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        // TODO: remove the player from the invite HashMap
        // Might be left undone
    }

}
