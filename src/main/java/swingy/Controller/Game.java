package swingy.Controller;

import swingy.Model.CombatTurnResult;
import swingy.Model.GameMap;
import swingy.Model.GameStats;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.Model.ItemFactory;
import swingy.Model.Villain;
import swingy.Model.Entity;
import swingy.View.View;
import swingy.View.Console.ConsoleView;
import swingy.View.Gui.SwingView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import swingy.Model.CombatResult;
import swingy.Model.Item;
import swingy.Model.GameMap.Direction;

public class Game
{
	public static enum ViewMode
	{
		CONSOLE,
		SWING
	}

	private static Game	_instance = null;
	private boolean		_is_running = false;
	private ViewMode	_view_mode = ViewMode.SWING;

	private GameMap		_map;
	private Hero		_hero;
	private View		_view;
	private SaveManager	_save_manager;

	private GameStats	_stats;

	private static final double XP_BASE = 4;
	private static final double XP_GROWTH = 0.6;
	private static final double XP_DIFF_SCALE = 10;
	private static final double XP_MIN_MULT = 0.2;
	private static final double XP_MAX_MULT = 2;
	private static final double XP_SPREAD = 0.1;

	private Game() throws Exception
	{
		this._save_manager = SaveManager.GetInstance();
		SetView();
	}

	public static Game GetInstance() throws Exception
	{
		if (_instance == null)
			_instance = new Game();

		return _instance;
	}

	public GameStats GetStats() { return this._stats; }

	public void SetView()
	{
		switch (this._view_mode)
		{
			case CONSOLE:
				this._view = new ConsoleView();
				break;

			case SWING:
				this._view = new SwingView();
				break;
		
			default:
				this._view = new SwingView();
				break;
		}
	}

	public void SwitchView()
	{
		if (this._view_mode == ViewMode.CONSOLE)
			this._view_mode = ViewMode.SWING;
		else
			this._view_mode = ViewMode.CONSOLE;

		SetView();
	}

	public void ChooseSave()
	{
		int save_slot = this._view.DisplayChooseSave(this._save_manager.GetSaveFile());

		Hero hero = this._save_manager.GetSave(save_slot);
		GameStats stats = this._save_manager.GetSaveStats(save_slot);

		if (hero != null)
		{
			this._hero = hero;
			this._stats = stats;
		}
		else
		{
			Hero.Class Class = this._view.DisplayCreateHeroClass();
			String name = this._view.DisplayCreateHeroName();

			hero = HeroFactory.NewHero(Class, name);
			stats = new GameStats();
			this._hero = hero;
			this._stats = stats;
			this._save_manager.SetSave(save_slot, hero, stats);
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

				case SWITCH_VIEW:
					this.SwitchView();
					break;

				default:
					break;
			}
		}
	}

	public void DisplayCombatTurnResult(CombatTurnResult result, List<Entity> next_turns)
	{
		this._view.DisplayCombatTurnResult(result, next_turns);
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

	public static int CalculateXpGained(Villain villain, Hero hero)
	{
		double k = XP_BASE + XP_GROWTH * (double)villain.GetLevel();

		double mult = 1. + ((double)villain.GetLevel() - (double)hero.GetLevel()) / XP_DIFF_SCALE;
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
				if (this._hero.GetLevel() > 2)
				{
					Villain boss = this._map.GetBoss();
					this.StartCombat(boss, true);
				}

				if (this._is_running)
				{
					this._stats.rooms_exited++;
					this._map = new GameMap(this._hero);
					this._view.MapChanged();
				}
				break;

			case FIGHT:
				Villain villain = this._map.GetCurrentVillain();
				this.StartCombat(villain, false);
				break;

			case CHEST:
				this._stats.chest_opened++;
				this.OpenChest();
				break;

			default:
				break;
		}
	}

	public void StartCombat(Villain villain, boolean is_boss) throws Exception
	{
		Combat combat = new Combat(this._hero, villain, is_boss);
		this._view.DisplayStartCombat(this._hero, villain, is_boss);
		CombatResult result = combat.Start();

		if (result.hero_win)
		{
			this._view.DisplayVillainDied(villain);
			this._view.GetUserInput();

			if (is_boss && villain.GetLevel() == 50)
			{
				this._is_running = false;
				this._stats.has_won = true;
				this._stats.win_date = LocalDate.now();
				this._save_manager.AddToPantheon(this._hero, this._stats);
				this._save_manager.DeleteHero(this._hero);
				return;
			}

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

			Item drop = ItemFactory.GenerateItem(this._hero, villain, is_boss);
			if (drop != null)
			{
				boolean is_equip = false;
				while (!is_equip)
				{
					switch (this._view.DisplayEquipItem(drop))
					{
						case EQUIP_ITEM:
							this._stats.items_equiped++;
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

			if (this._map.IsRoomEmpty() && !is_boss)
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

	public View.Action DisplayHeroCombatChoice(Hero hero, Villain villain, List<Entity> next_turns, boolean is_boss)
	{
		return this._view.DisplayHeroCombatChoice(hero, villain, next_turns, is_boss);
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

	public boolean IsPantheon()
	{
		return this._save_manager.IsPantheon();
	}

	public void ChooseStart() throws Exception
	{
		int action = this._view.DisplayStart();

		switch (action) {
			case 0:
				this.ChooseSave();
				this.Start();
				break;

			case 1:
				this._view.DisplayPantheon(this._save_manager.GetPantheonFile());
				break;
		
			default:
				this.ChooseSave();
				break;
		}
	}
}
