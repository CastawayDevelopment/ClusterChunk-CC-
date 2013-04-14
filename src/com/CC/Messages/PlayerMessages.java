package com.CC.Messages;

import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;

public class PlayerMessages
{

    public String noPermission(Player player)
    {
        return new StringBuilder(GRAY.toString()).append("Sorry ").append(player.getDisplayName()).append(", only operators do this.").toString();
    }

    public String noPermissionCommand(Player player)
    {
        return new StringBuilder(GRAY.toString()).append("Sorry ").append(player.getDisplayName()).append(", only operators can use this command.").toString();
    }

    public String worldDoesNotExist(Player player)
    {
        return new StringBuilder(GRAY.toString()).append("Sorry ").append(player.getDisplayName()).append(", the world you have entered does not exist.").toString();
    }

    public String gameEndedSuccessfully()
    {
        return new StringBuilder(GREEN.toString()).append("You have successfully ended the game").toString();
    }
}
