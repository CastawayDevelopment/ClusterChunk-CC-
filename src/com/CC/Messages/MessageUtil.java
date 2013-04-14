package com.CC.Messages;

import org.bukkit.ChatColor;

/**
 *
 * @author DarkSeraphim
 */

/**
 * This is the beginning of our MessageUtil 
 * 
 * Inspired on Factions :3
 */
public class MessageUtil
{
    private static final String MSG = ChatColor.WHITE.toString();
    
    private static final String PARTY = ChatColor.LIGHT_PURPLE.toString();
    
    private static final String CMD = ChatColor.YELLOW.toString();
    
    private static final String WARNING = ChatColor.RED.toString();
    
    private static final String SUCCESS = ChatColor.GREEN.toString();
    
    public static String parseWarning(String msg, Object[] args)
    {
        return WARNING+String.format(parseColours(msg, WARNING), args);
    }
    
    public static String parseSuccess(String msg, Object[] args)
    {
        return SUCCESS+String.format(parseColours(msg, SUCCESS), args);
    }
    
    private static String parseColours(String msg, String reset)
    {
        return msg.replace("<p>", PARTY)
                  .replace("<c>", CMD)
                  .replace("<r>", reset);
    }
    
    
}
