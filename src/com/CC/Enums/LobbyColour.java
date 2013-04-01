/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CC.Enums;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.DyeColor;

/**
 *
 * @author s129977
 */
public enum LobbyColour
{

    RED(DyeColor.RED.getData()),
    BLUE(DyeColor.BLUE.getData()),
    WHITE(DyeColor.WHITE.getData()),
    NONE((byte)-1);
    
    
    private final byte data;

    LobbyColour(byte data)
    {
        this.data = data;
    }

    public byte getData()
    {
        return this.data;
    }
    private static final Map<Byte, LobbyColour> byData = new HashMap<Byte, LobbyColour>();

    static
    {
        for (LobbyColour lc : LobbyColour.values())
        {
            byData.put(lc.getData(), lc);
        }
    }

    public static LobbyColour byData(byte data)
    {
        LobbyColour lc = byData.get(data);
        return lc != null ? lc : LobbyColour.NONE;
    }
}
