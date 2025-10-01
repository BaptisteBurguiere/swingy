package swingy.View;

import swingy.Model.CombatTurnResult;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;
import swingy.Model.Entity;

public abstract class View
{
	public abstract void DisplayHero(Hero hero);
	public abstract void DisplayStatistic(Statistic statistic);
	public abstract void DisplayItem(Item item);
	public abstract void DisplayCombatTurnResult(CombatTurnResult result);
	public abstract void DisplayYouDied();
	public abstract void DisplayVillainDied(Entity entity);
	public abstract void DisplayLevelUp(Hero hero);
	public abstract void DisplayXpGained(int xp);
}
