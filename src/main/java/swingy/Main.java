package swingy;

import swingy.Controller.Game;
import swingy.Controller.SaveManager;
import swingy.Model.GameMap;
import swingy.Model.GameMap.Direction;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.View.Console.ConsoleView;
import swingy.View.Gui.SwingView;

public class Main {
    public static void main(String[] args)
	{
		try
		{
			// Game game = Game.GetInstance();
	
			// game.ChooseSave();
			// game.Start();

			// SaveManager	save_manager = SaveManager.GetInstance();
			// Hero hero = save_manager.GetSave(0);
			// GameMap map = new GameMap(hero);
			// SwingView view = new SwingView();
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

			// view.DisplayCreateHeroClass();

			// System.out.println(view.DisplayCreateHeroName());

			// int res = view.DisplayChooseSave(save_manager.GetSaveFile());
			// System.out.println(String.format("%d", res));

			SaveManager save_manager = SaveManager.GetInstance();
			SwingView view = new SwingView();

			int save_slot = view.DisplayChooseSave(save_manager.GetSaveFile());

			Hero hero = save_manager.GetSave(save_slot);

			if (hero == null)
			{
				Hero.Class Class = view.DisplayCreateHeroClass();
				String name = view.DisplayCreateHeroName();

				hero = HeroFactory.NewHero(Class, name);
				save_manager.SetSave(save_slot, hero);
				save_manager.Save();
			}

			GameMap map = new GameMap(hero);

			boolean is_running = true;

			while (is_running)
			{
				switch (view.DisplayMainView(map, hero))
				{
					case DISPLAY_HELP:
						break;
					
					case MOVE_LEFT:
						map.MoveHero(Direction.LEFT);
						break;

					case MOVE_RIGHT:
						map.MoveHero(Direction.RIGHT);
						break;

					case MOVE_UP:
						map.MoveHero(Direction.UP);
						break;

					case MOVE_DOWN:
						map.MoveHero(Direction.DOWN);
						break;

					case QUIT:
						is_running = false;
						break;

					default:
						break;
				}
			}

			System.exit(0);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
    }
}