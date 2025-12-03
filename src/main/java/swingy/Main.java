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