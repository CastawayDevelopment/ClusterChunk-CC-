package com.CC.Game;

public class GameManager
{

    public GameManager()
    {
        this.games = new HashMap<String, Game>();
    }
    
    private HashMap<String, Game> games;
    
    public Game getGame(String name)
    {
        return this.games.get(name);
    }
    
    public boolean createGame(String name)
    {
        if(this.games.containsKey(name))
        {
            return false;
        }
        Game g = new Game();
        this.games.put(name, g);
        return true;
    }
    
    public void removeGame(String name)
    {
        if(!this.games.containsKey(name))
        {
            return false;
        }
        this.games.remove(name);
        return true;
    }
    
}