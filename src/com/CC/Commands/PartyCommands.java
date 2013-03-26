package com.CC.Commands;

import com.CC.Commands.Party.*;
import com.CC.General.ClusterChunk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommands implements CommandExecutor
{

    private Help help;
    private Leave leave;
    private Status status;
    private StartRed startRed;
    private StartBlue startBlue;
    private Create create;
    private Disband disband;
    private Accept accept;
    private Invite invite;
    private ChangeLeader change;
    private Versus versus;
    private Kick kick;

    public PartyCommands(ClusterChunk plugin)
    {
        help = new Help(plugin);
        leave = new Leave(plugin);
        status = new Status(plugin);
        startRed = new StartRed(plugin);
        startBlue = new StartBlue(plugin);
        create = new Create(plugin);
        disband = new Disband(plugin);
        accept = new Accept(plugin);
        invite = new Invite(plugin);
        change = new ChangeLeader(plugin);
        versus = new Versus(plugin);
        kick = new Kick(plugin);


    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
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
                else if (args.length == 2)
                {
                    if (args[0].equalsIgnoreCase("start"))
                    {

                        if (args[1].equalsIgnoreCase("red"))
                        {

                            startRed.start(player);
                        }
                        else if (args[1].equalsIgnoreCase("blue"))
                        {

                            startBlue.start(player);
                        }
                        else
                        {
                            sender.sendMessage("Please choose either red or blue");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("create"))
                    {

                        String partyName = args[1];

                        create.create(player, partyName);
                    }
                    else if (args[0].equalsIgnoreCase("disband"))
                    {
                        disband.disbandParty(player);
                    }
                    else if (args[0].equalsIgnoreCase("changeleader"))
                    {
                        if (Bukkit.getPlayer(args[1]) != null)
                        {
                            change.ChangePartyLeader(player, Bukkit.getPlayer(args[1]));
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "The player '" + ChatColor.DARK_RED + args[1] + ChatColor.RED + "' is not online");
                        }

                    }
                    else if (args[0].equalsIgnoreCase("invite"))
                    {
                        if (Bukkit.getPlayer(args[1]) != null)
                        {
                            invite.invitePlayer(player, Bukkit.getPlayer(args[1]));
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "The player '" + ChatColor.DARK_RED + args[1] + ChatColor.RED + "' does not exist");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("kick"))
                    {
                        if (Bukkit.getPlayer(args[1]) != null)
                        {
                            kick.kickPlayer(player, Bukkit.getPlayer(args[1]));
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "The player '" + ChatColor.DARK_RED + args[1] + ChatColor.RED + "' does not exist");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("accept"))
                    {
                        accept.accept(args[1], player);
                    }
                    else if (args[0].equalsIgnoreCase("versus"))
                    {
                        this.versus.execute(player, args[1]);
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
