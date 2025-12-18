package swingy.View;

import java.util.List;

import swingy.Model.CombatTurnResult;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.PantheonFile;
import swingy.Model.SaveFile;
import swingy.Model.Villain;
import swingy.Model.Entity;
import swingy.Model.GameMap;

public abstract class View
{
	public enum Action
	{
		MOVE_LEFT,
		MOVE_RIGHT,
		MOVE_UP,
		MOVE_DOWN,
		DISPLAY_EQUIPMENT,
		DISPLAY_STATISTICS,
		DISPLAY_HELP,
		QUIT,
		EQUIP_ITEM,
		LEAVE_ITEM,
		ATTACK,
		DEFEND,
		FLEE,
		SWITCH_VIEW
	}

	public abstract void DisplayHero(Hero hero);
	public abstract void DisplayItem(Item item);
	public abstract void DisplayCombatTurnResult(CombatTurnResult result, List<Entity> next_turns);
	public abstract void DisplayYouDied();
	public abstract void DisplayVillainDied(Entity entity);
	public abstract void DisplayLevelUp(Hero hero);
	public abstract void DisplayXpGained(int xp);
	public abstract void DisplayEquipment(Hero hero);
	public abstract Action DisplayMainView(GameMap map, Hero hero);
	public abstract String GetUserInput();
	public abstract void DisplayStartCombat(Hero hero, Villain villain, boolean is_boss);
	public abstract Action DisplayEquipItem(Item item);
	public abstract int DisplayChooseSave(SaveFile save_file);
	public abstract Hero.Class DisplayCreateHeroClass();
	public abstract String DisplayCreateHeroName();
	public abstract Action DisplayHeroCombatChoice(Hero hero, Villain villain, List<Entity> next_turns, boolean is_boss);
	public abstract int DisplayChooseChestContent(Hero hero, List<Item> chest_content);
	public abstract void DisplayChestSpawn();
	public abstract void MapChanged();
	public abstract int DisplayStart();
	public abstract int DisplayPantheon(PantheonFile pantheon);
}
