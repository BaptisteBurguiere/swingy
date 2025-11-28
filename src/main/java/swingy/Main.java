package swingy;

import swingy.Controller.Game;
import swingy.Controller.SaveManager;
import swingy.Model.GameMap;
import swingy.Model.GameMap.Direction;
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
			// while (true)
			// {
			// 	switch (view.DisplayMainView(map, hero))
			// 	{
			// 		// case DISPLAY_EQUIPMENT:
			// 		// 	view.DisplayEquipment(hero);
			// 		// 	break;
	
			// 		// case DISPLAY_STATISTICS:
			// 		// 	view.DisplayHero(hero);
			// 		// 	break;
	
			// 		case DISPLAY_HELP:
			// 			break;
					
			// 		case MOVE_LEFT:
			// 			map.MoveHero(Direction.LEFT);
			// 			break;
	
			// 		case MOVE_RIGHT:
			// 			map.MoveHero(Direction.RIGHT);
			// 			break;
	
			// 		case MOVE_UP:
			// 			map.MoveHero(Direction.UP);
			// 			break;
	
			// 		case MOVE_DOWN:
			// 			map.MoveHero(Direction.DOWN);
			// 			break;
	
			// 		default:
			// 			break;
			// 	}
			// }

			view.DisplayCreateHeroClass();

			// int res = view.DisplayChooseSave(save_manager.GetSaveFile());
			// System.out.println(String.format("%d", res));
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
    }
}