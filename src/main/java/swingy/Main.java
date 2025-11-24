package swingy;

import swingy.Controller.Game;
import swingy.Controller.SaveManager;
import swingy.View.Gui.SwingView;

public class Main {
    public static void main(String[] args)
	{
		try
		{
			// Game game = Game.GetInstance();
	
			// game.ChooseSave();
			// game.Start();

			SaveManager	save_manager = SaveManager.GetInstance();
			SwingView view = new SwingView();
			view.DisplayChooseSave(save_manager.GetSaveFile());
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
    }
}