package com.CC.Enums;

import org.bukkit.ChatColor;

/*
*   Pretty empty right?
**/
public enum Team 
{
        NONE(ChatColor.WHITE, "none"),
        RED(ChatColor.DARK_RED, "Red Team"),
        BLUE(ChatColor.DARK_BLUE, "Blue Team");
        
        private final String teamName;
        
        Team(ChatColor c, String n)
        {
            this.teamName = new StringBuilder(c.toString()).append(n).append(ChatColor.RESET).toString();
        }
        
        public String getName()
        {
            return this.teamName;
        }
}