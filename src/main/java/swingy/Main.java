package swingy;

import swingy.Controller.Game;
import swingy.Controller.SaveManager;
import swingy.Model.GameMap;
import swingy.Model.Hero;
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
			Hero hero = save_manager.GetSave(0);
			GameMap map = new GameMap(hero);
			SwingView view = new SwingView();
			view.DisplayMainView(map, hero);

			// int res = view.DisplayChooseSave(save_manager.GetSaveFile());
			// System.out.println(String.format("%d", res));
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
    }
}