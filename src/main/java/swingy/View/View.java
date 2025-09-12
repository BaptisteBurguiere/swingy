package swingy.View;

import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;

public abstract class View
{
	public abstract void DisplayHero(Hero hero);
	public abstract void DisplayStatistic(Statistic statistic);
	public abstract void DisplayItem(Item item);
}
