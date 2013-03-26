package com.CC.Commands.Party;

import com.CC.General.ClusterChunk;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help
{

    public Help(ClusterChunk p)
    {
    }

    public void help(Player player)
    {

        player.sendMessage(new StringBuilder(ChatColor.DARK_GRAY.toString()).append("===========").append(ChatColor.DARK_RED).append("[").append(ChatColor.DARK_AQUA).append("Party Commands").append(ChatColor.DARK_RED).append("]").append(ChatColor.DARK_GRAY).append("===========").toString());
        player.sendMessage(ChatColor.DARK_GREEN + "/party create <party name>" + ChatColor.GREEN + " - Create a party with the specified name");
        player.sendMessage(ChatColor.DARK_GREEN + "/party disband " + ChatColor.GREEN + " - Disband your party");
        player.sendMessage(ChatColor.DARK_GREEN + "/party help " + ChatColor.GREEN + "- Take a guess <3");
        player.sendMessage(ChatColor.DARK_GREEN + "/party invite <player name> " + ChatColor.GREEN + "- Invite a player to your closed party");
        player.sendMessage(ChatColor.DARK_GREEN + "/party accept " + ChatColor.GREEN + "- Join a party you were invited to");
        player.sendMessage(ChatColor.DARK_GREEN + "/party kick <player name> " + ChatColor.GREEN + "- Kick the player from the party you are currently in - Leader only");
        player.sendMessage(ChatColor.DARK_GREEN + "/party leave " + ChatColor.GREEN + "- Leave the party you are currently in");
        player.sendMessage(ChatColor.DARK_GREEN + "/party start <red/blue> " + ChatColor.GREEN + "- Start a match with your party on red or blue team");
        player.sendMessage(ChatColor.DARK_GREEN + "/party status " + ChatColor.GREEN + "- Get the status of your party");
        player.sendMessage(ChatColor.DARK_GREEN + "/party versus <partyname> " + ChatColor.GREEN + "- Challenge another party");
        player.sendMessage(ChatColor.DARK_GRAY + "===========" + ChatColor.DARK_RED + "[" + ChatColor.DARK_AQUA + "Party Commands" + ChatColor.DARK_RED + "]" + ChatColor.DARK_GRAY + "===========");


    }
}
