package swingy.Controller;

import java.util.EnumMap;
import java.util.Map;

import swingy.Model.CombatTurnResult;
import swingy.Model.GameMap;
import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.Model.ItemFactory;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.View;
import swingy.View.Console.ConsoleView;
import swingy.Model.CombatResult;
import swingy.Model.Item;
import swingy.Model.VillainFactory;
import swingy.Model.GameMap.Direction;
import swingy.Model.GameMap.MoveResult;

public class Game
{
	private static Game	_instance = null;
	private boolean		_is_running = true;

	private GameMap		_map;
	private Hero		_hero;
	private View		_view;

	private final double XP_BASE = 4;
	private final double XP_GROWTH = 0.6;
	private final double XP_DIFF_SCALE = 10;
	private final double XP_MIN_MULT = 0.2;
	private final double XP_MAX_MULT = 2;
	private final double XP_SPREAD = 0.1;

	private Game(Hero.Class Class, String name)
	{
		this._view = new ConsoleView();
		this._hero = HeroFactory.NewHero(Class, name);
		this._map = new GameMap(this._hero.GetLevel());
	}

	public static Game GetInstance()
	{
		if (_instance == null)
			_instance = new Game(Hero.Class.KNIGHT, "Hero");

		return _instance;
	}

	public static Game GetInstance(Hero.Class Class, String name)
	{
		if (_instance == null)
			_instance = new Game(Class, name);

		return _instance;
	}

	public void Start()
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

		double xp_gained = villain_xp_needed / k * mult;

		double spread_roll = Math.min(1., Math.random());
		xp_gained *= 1. + (((XP_SPREAD * 2) * spread_roll) - XP_SPREAD);

		return (int) xp_gained;
	}

	public void MoveHero(Direction direction)
	{
		switch (this._map.MoveHero(direction)) {
			case EXIT:
				this._map = new GameMap(this._hero.GetLevel());
				break;

			case FIGHT:
				this.StartCombat();
				break;

			default:
				break;
		}
	}

	public void StartCombat()
	{
		Villain villain = VillainFactory.GenerateVillain(this._hero);

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
		}
		else
		{
			this._is_running = false;
			this._view.DisplayYouDied();
			this._view.GetUserInput();
		}
	}

	public void GetUserInput()
	{
		this._view.GetUserInput();
	}
}
