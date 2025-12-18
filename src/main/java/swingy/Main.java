package swingy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import swingy.Controller.Game;

public class Main {
    public static void main(String[] args)
	{
		try
		{
			Game game = Game.GetInstance();
	
			if (game.IsPantheon())
				game.ChooseStart();
			else
			{
				game.ChooseSave();
				game.Start();
			}

			System.exit(0);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
    }
}