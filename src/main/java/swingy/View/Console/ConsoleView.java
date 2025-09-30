package swingy.View.Console;

import java.util.Map;

import swingy.Model.CombatTurnResult;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.View.View;
import swingy.Model.Entity;

public class ConsoleView extends View
{
	public ConsoleView() {}

	public void DisplayHero(Hero hero)
	{
		String to_display = String.format("%s lvl. %d - %s", hero.GetClassStr(), hero.GetLevel(), hero.GetName());
		System.out.println(to_display);

		Statistic stat = hero.GetStatistic(StatisticTemplate.Type.HEALTH);
		to_display = String.format("  %s: %d/%d", stat.GetName(), (int)hero.GetCurrentHealth(), (int)stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.ATTACK);
		to_display = String.format("  %s: %.0f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.DEFENSE);
		to_display = String.format("  %s: %.0f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.SPEED);
		to_display = String.format("  %s: %.0f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.EVASION);
		to_display = String.format("  %s: %.2f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.ACCURACY);
		to_display = String.format("  %s: %.2f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE);
		to_display = String.format("  %s: %.2f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE);
		to_display = String.format("  %s: %.2f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		stat = hero.GetStatistic(StatisticTemplate.Type.LUCK);
		to_display = String.format("  %s: %.0f", stat.GetName(), stat.GetValue());
		System.out.println(to_display);

		System.out.println("Equipment:");

		Item item = hero.GetItem(Item.Type.WEAPON);
		to_display = "  Weapon: ";
		if (item != null)
			to_display += String.format("%s (%s)", item.GetName(), item.GetRarityStr());
		System.out.println(to_display);

		item = hero.GetItem(Item.Type.ARMOR);
		to_display = "  Armor: ";
		if (item != null)
			to_display += String.format("%s (%s)", item.GetName(), item.GetRarityStr());
		System.out.println(to_display);

		item = hero.GetItem(Item.Type.HELMET);
		to_display = "  Helmet: ";
		if (item != null)
			to_display += String.format("%s (%s)", item.GetName(), item.GetRarityStr());
		System.out.println(to_display);

		item = hero.GetItem(Item.Type.RELIC);
		to_display = "  Relic: ";
		if (item != null)
			to_display += String.format("%s (%s)", item.GetName(), item.GetRarityStr());
		System.out.println(to_display);
	}

	public void DisplayStatistic(Statistic statistic)
	{
		String to_display = String.format("%s: %s", statistic.GetName(), statistic.GetValue());
		System.out.println(to_display);
	}

	public void DisplayItemRarity(Item.Rarity rarity)
	{

	}

	public void DisplayItem(Item item)
	{
		System.out.println("[" + item.GetRarityStr() + "]");
	}

	public void DisplayCombatTurnResult(CombatTurnResult result)
	{
		String to_display = String.format("%s attacks %s!", result.attacker.GetName(), result.defender.GetName());
		System.out.println(to_display);

		if (result.missed)
		{
			System.out.println("Missed!");
			return;
		}

		if (result.critical)
			System.out.println("Critical Hit!");
		
		to_display = String.format("%.0f damages dealt!", result.damage);
		System.out.println(to_display);
	}

	public void DisplayYouDied()
	{
		System.out.println("You died...");
	}

	public void DisplayVillainDied(Entity entity)
	{
		String to_display = String.format("Ennemy %s died!", entity.GetName());
		System.out.println(to_display);
	}
}
