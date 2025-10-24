package swingy.Controller;

import swingy.Model.CombatTurnResult;
import swingy.Model.GameMap;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.Model.ItemFactory;
import swingy.Model.Villain;
import swingy.Model.Entity;
import swingy.View.View;
import swingy.View.Console.ConsoleView;

import java.util.ArrayList;
import java.util.List;

import swingy.Model.CombatResult;
import swingy.Model.Item;
import swingy.Model.GameMap.Direction;

public class Game
{
	private static Game	_instance = null;
	private boolean		_is_running = false;

	private GameMap		_map;
	private Hero		_hero;
	private View		_view;
	private SaveManager	_save_manager;

	private final double XP_BASE = 4;
	private final double XP_GROWTH = 0.6;
	private final double XP_DIFF_SCALE = 10;
	private final double XP_MIN_MULT = 0.2;
	private final double XP_MAX_MULT = 2;
	private final double XP_SPREAD = 0.1;

	private Game() throws Exception
	{
		this._save_manager = SaveManager.GetInstance();
		this._view = new ConsoleView();
	}

	public static Game GetInstance() throws Exception
	{
		if (_instance == null)
			_instance = new Game();

		return _instance;
	}

	public void ChooseSave()
	{
		int save_slot = this._view.DisplayChooseSave(this._save_manager.GetSaveFile());

		Hero hero = this._save_manager.GetSave(save_slot);

		if (hero != null)
			this._hero = hero;
		else
		{
			Hero.Class Class = this._view.DisplayCreateHeroClass();
			String name = this._view.DisplayCreateHeroName();

			hero = HeroFactory.NewHero(Class, name);
			this._hero = hero;
			this._save_manager.SetSave(save_slot, hero);
			this._save_manager.Save();
		}

		this._map = new GameMap(this._hero);

		this._is_running = true;
	}

	public void Start() throws Exception
	{
		while (this._is_running)
		{
			switch (this._view.DisplayMainView(this._map, this._hero))
			{
				case DISPLAY_EQUIPMENT:
					this._view.DisplayEquipment(this._hero);
					break;

				case DISPLAY_STATISTICS:
					this._view.DisplayHero(this._hero);
					break;

				case DISPLAY_HELP:
					break;
				
				case MOVE_LEFT:
					this.MoveHero(Direction.LEFT);
					break;

				case MOVE_RIGHT:
					this.MoveHero(Direction.RIGHT);
					break;

				case MOVE_UP:
					this.MoveHero(Direction.UP);
					break;

				case MOVE_DOWN:
					this.MoveHero(Direction.DOWN);
					break;

				case QUIT:
					this._is_running = false;
					break;

				default:
					break;
			}
		}
	}

	public void DisplayCombatTurnResult(CombatTurnResult result)
	{
		this._view.DisplayCombatTurnResult(result);
	}

	public int CalculateXpGained(Villain villain)
	{
		double k = XP_BASE + XP_GROWTH * (double)villain.GetLevel();

		double mult = 1. + ((double)villain.GetLevel() - (double)this._hero.GetLevel()) / XP_DIFF_SCALE;
		mult = Math.max(XP_MIN_MULT, Math.min(XP_MAX_MULT, mult));

		double villain_xp_needed = villain.GetExperienceNeeded();

		double xp_gained = villain_xp_needed / k * mult * villain.GetExpMultiplier();

		double spread_roll = Math.min(1., Math.random());
		xp_gained *= 1. + (((XP_SPREAD * 2) * spread_roll) - XP_SPREAD);

		return (int) xp_gained;
	}

	public void MoveHero(Direction direction) throws Exception
	{
		switch (this._map.MoveHero(direction)) {
			case EXIT:
				Villain boss = this._map.GetBoss();
				this.StartCombat(boss);
				if (this._is_running)
					this._map = new GameMap(this._hero);
				break;

			case FIGHT:
				Villain villain = this._map.GetCurrentVillain();
				this.StartCombat(villain);
				break;

			case CHEST:
				this.OpenChest();
				break;

			default:
				break;
		}
	}

	public void StartCombat(Villain villain) throws Exception
	{
		Combat combat = new Combat(this._hero, villain);
		this._view.DisplayStartCombat(this._hero, villain);
		CombatResult result = combat.Start();

		if (result.hero_win)
		{
			this._view.DisplayVillainDied(villain);
			this._view.GetUserInput();

			int xp_gained = CalculateXpGained(villain);
			this._hero.GainExperience(xp_gained);
			this._view.DisplayXpGained(xp_gained);
			this._view.GetUserInput();
			
			if (this._hero.GetExperience() >= this._hero.GetExperienceNeeded())
			{
				this._hero.LevelUp();
				this._view.DisplayLevelUp(this._hero);
				this._view.GetUserInput();
			}

			Item drop = ItemFactory.GenerateItem(this._hero);
			if (drop != null)
			{
				boolean is_equip = false;
				while (!is_equip)
				{
					switch (this._view.DiplayEquipItem(drop))
					{
						case EQUIP_ITEM:
							this._hero.EquipItem(drop);	
							is_equip = true;					
							break;
						
						case LEAVE_ITEM:
							is_equip = true;
							break;
	
						case DISPLAY_EQUIPMENT:
							this._view.DisplayEquipment(this._hero);
							break;
	
						case DISPLAY_STATISTICS:
							this._view.DisplayHero(this._hero);
							break;
					
						default:
							break;
					}
				}
			}

			this._save_manager.Save();

			if (this._map.IsRoomEmpty())
			{
				this._map.SpawnChest();
				this._view.DisplayChestSpawn();
				this._view.GetUserInput();
			}
		}
		else if (result.flee)
		{
			this._save_manager.Save();
			this._map.GoToLastPosition();
			this._view.GetUserInput();
		}
		else
		{
			this._save_manager.DeleteHero(this._hero);
			this._is_running = false;
			this._view.DisplayYouDied();
			this._view.GetUserInput();
		}
	}

	public void GetUserInput()
	{
		this._view.GetUserInput();
	}

	public View.Action DisplayHeroCombatChoice(Hero hero, Villain villain, List<Entity> next_turns)
	{
		return this._view.DisplayHeroCombatChoice(hero, villain, next_turns);
	}

	public void OpenChest()
	{
		List<Item> chest_content = GenerateChestContent();

		int index = this._view.DisplayChooseChestContent(this._hero, chest_content);
		this._hero.EquipItem(chest_content.get(index));
		this._save_manager.Save();
	}

	public List<Item> GenerateChestContent()
	{
		List<Item> chest_content = new ArrayList<>();

		chest_content.add(ItemFactory.GenerateHealingItem(this._hero));
		chest_content.add(ItemFactory.GenerateEssence(this._hero));
		chest_content.add(ItemFactory.GenerateEssence(this._hero));

		return chest_content;
	}
}
