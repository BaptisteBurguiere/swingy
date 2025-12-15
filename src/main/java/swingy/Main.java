package swingy;

import swingy.Controller.Game;

public class Main {
    public static void main(String[] args)
	{
		try
		{
			Game game = Game.GetInstance();
	
			game.ChooseSave();
			game.Start();

			System.exit(0);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
    }
}