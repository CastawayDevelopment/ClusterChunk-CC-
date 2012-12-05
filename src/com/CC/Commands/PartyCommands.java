package com.CC.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import com.CC.Party.*;
import com.CC.Commands.Party.*;
import com.CC.General.onStartup;

public class PartyCommands implements CommandExecutor
{

    private Help help;
    private Leave leave;
    private Status status;
    private StartRed startRed;
    private StartBlue startBlue;
    private Join join;
    private Create create;
    private Disband disband;

    public PartyCommands(onStartup plugin)
    {
        help = new Help(plugin);
        leave = new Leave(plugin);
        status = new Status(plugin);
        startRed = new StartRed(plugin);
        startBlue = new StartBlue(plugin);
        join = new Join(plugin);
        create = new Create(plugin);
        disband = new Disband(plugin);
    }

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) { 
		if(sender instanceof Player) 
        {
			Player player = (Player)sender;
			if (cmd.getName().equalsIgnoreCase("party")) 
            {
				if (args.length == 1)
                {
                    if (args[0].equalsIgnoreCase("help")) 
                    {

                        help.help(player);
                    } 
                    else if (args[0].equalsIgnoreCase("leave"))
                    {

                        leave.leave(player);
                    }
                    else if (args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("status"))
                    {

                        status.status(player);
                    } 
                    else
                    {
                        return false;
                    }
                    return true;
                }
                else if(args.length == 2)
                {
                    if (args[0].equalsIgnoreCase("start"))
                    {

                        if (args.length == 2 && args[1].equalsIgnoreCase("red"))
                        {
                        	
                            startRed.start(player);
                        }
                        else if (args.length == 2 && args[1].equalsIgnoreCase("blue"))
                        {
                        	
                            startBlue.start(player);
                        }
                    }
                   
                    else if (args[0].equalsIgnoreCase("join"))
                    {
                    	if (args.length == 2)
                    	{
                    		
                    		String partyName = args[1];
                    		partyName.toLowerCase();
                    		
                            join.join(player, partyName);
                    	}
                    }
                    else if (args[0].equalsIgnoreCase("create"))
                    {
                    	
                    	String partyName = args[1];
                    	
                        create.create(player, partyName);
                    }
                    else if (args[0].equalsIgnoreCase("Disband")){
                    	disband.Disbandparty(player);
                    }
                    
                }
			}
			
			
			// instance of check because NOT all senders are players. Simply typecasting to player would break on console cmd.
		}
        else
        {
			// Tho, wtf is happening here? not much ;D
		}
		return false;
	}
	
}

