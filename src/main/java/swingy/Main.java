package swingy;

import swingy.Controller.Combat;
import swingy.Controller.Game;
import swingy.Controller.SaveManager;
import swingy.Model.CombatResult;
import swingy.Model.GameMap;
import swingy.Model.GameMap.Direction;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.Model.Item;
import swingy.Model.ItemFactory;
import swingy.Model.Villain;
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
						MoveHero(save_manager, view, hero, map, Direction.LEFT);
						break;

					case MOVE_RIGHT:
						MoveHero(save_manager, view, hero, map, Direction.RIGHT);
						break;

					case MOVE_UP:
						MoveHero(save_manager, view, hero, map, Direction.UP);
						break;

					case MOVE_DOWN:
						MoveHero(save_manager, view, hero, map, Direction.DOWN);
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

	public static void MoveHero(SaveManager save_manager, SwingView view, Hero hero, GameMap map, Direction direction) throws Exception
	{
		switch (map.MoveHero(direction)) {
			case FIGHT:
				Villain villain = map.GetCurrentVillain();
				StartCombat(save_manager, map, view, hero, villain, false);
				break;

			default:
				break;
		}
	}

	public static void StartCombat(SaveManager save_manager, GameMap map, SwingView view, Hero hero, Villain villain, boolean is_boss) throws Exception
	{
		Combat combat = new Combat(hero, villain, is_boss);
		view.DisplayStartCombat(hero, villain, is_boss);
		CombatResult result = combat.Start(view);

		if (result.hero_win)
		{
			view.DisplayVillainDied(villain);
			view.GetUserInput();

			int xp_gained = Game.CalculateXpGained(villain, hero);
			hero.GainExperience(xp_gained);
			view.DisplayXpGained(xp_gained);
			view.GetUserInput();
			
			if (hero.GetExperience() >= hero.GetExperienceNeeded())
			{
				hero.LevelUp();
				view.DisplayLevelUp(hero);
				view.GetUserInput();
			}

			Item drop = ItemFactory.GenerateItem(hero);
			if (drop != null)
			{
				boolean is_equip = false;
				while (!is_equip)
				{
					switch (view.DiplayEquipItem(drop))
					{
						case EQUIP_ITEM:
							hero.EquipItem(drop);	
							is_equip = true;					
							break;
						
						case LEAVE_ITEM:
							is_equip = true;
							break;
					
						default:
							break;
					}
				}
			}

			save_manager.Save();

			if (map.IsRoomEmpty() && !is_boss)
			{
				map.SpawnChest();
				view.DisplayChestSpawn();
				view.GetUserInput();
			}
		}
		else if (result.flee)
		{
			save_manager.Save();
			map.GoToLastPosition();
			view.GetUserInput();
		}
		else
		{
			save_manager.DeleteHero(hero);
			view.DisplayYouDied();
			view.GetUserInput();
			System.exit(0);
		}
	}
}