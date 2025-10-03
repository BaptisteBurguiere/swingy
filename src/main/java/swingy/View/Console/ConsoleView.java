package swingy.View.Console;

import java.util.Map;
import java.util.Scanner;

import swingy.Model.CombatTurnResult;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.View.View;
import swingy.Model.Entity;
import swingy.Model.GameMap;

public class ConsoleView extends View
{
	private Scanner _scanner = new Scanner(System.in);

	public ConsoleView() {}


	public void DisplayHero(Hero hero)
	{
		Clear();

		String to_display = String.format("%s lvl. %d (XP: %d / %d) - %s", hero.GetClassStr(), hero.GetLevel(), hero.GetExperience(), hero.GetExperienceNeeded(), hero.GetName());
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

		System.out.println();
		System.out.println("Press Enter to exit");

		GetUserInput();
	}

	public void DisplayItem(Item item)
	{
		String to_display = String.format("[%s] %s (%s)", item.GetTypeStr(), item.GetName(), item.GetRarityStr());
		System.out.println(to_display);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
		{
			Statistic stat = entry.getValue();
			switch (stat.GetType())
			{
				case HEALTH:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case ATTACK:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case DEFENSE:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case SPEED:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case EVASION:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case ACCURACY:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case CRIT_CHANCE:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case CRIT_DAMAGE:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case LUCK:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;
			
				default:
					break;
			}
			
			System.out.println(to_display);
		}
		
		to_display = String.format("Description: %s", item.GetDescription());
		System.out.println(to_display);
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

	public void Clear()
	{
		System.out.print("\u001b[2J");
		System.out.print("\u001b[H");
	}

	public void DisplayLevelUp(Hero hero)
	{
		String to_display = String.format("%s levels up to level %d!", hero.GetName(), hero.GetLevel());
		System.out.println(to_display);
	}

	public void DisplayXpGained(int xp)
	{
		String to_display = String.format("%d XP gained!", xp);
		System.out.println(to_display);
	}

	public void DisplayInputHelp()
	{
		System.out.println("Controls:");
		System.out.println("  w, a, s, d: Move on the map");
		System.out.println("  e: Display equipment");
		System.out.println("  c: Display hero statistics");
		System.out.println("  q: Quit");
		System.out.println("  h: Display help");
	}

	public void DisplayPrompt()
	{
		System.out.print("> ");
	}

	public void DisplayEquipmentItem(Item item)
	{
		String to_display = String.format("%s (%s)", item.GetName(), item.GetRarityStr());
		System.out.println(to_display);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
		{
			Statistic stat = entry.getValue();
			switch (stat.GetType())
			{
				case HEALTH:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case ATTACK:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case DEFENSE:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case SPEED:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;

				case EVASION:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case ACCURACY:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case CRIT_CHANCE:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case CRIT_DAMAGE:
					to_display = String.format("  +%.2f %s", stat.GetValue(), stat.GetName());
					break;

				case LUCK:
					to_display = String.format("  +%.0f %s", stat.GetValue(), stat.GetName());
					break;
			
				default:
					break;
			}
			
			System.out.println(to_display);
		}
		
		to_display = String.format("Description: %s", item.GetDescription());
		System.out.println(to_display);
	}

	public void DisplayEquipment(Hero hero)
	{
		Clear();

		Item item = hero.GetItem(Item.Type.WEAPON);
		System.out.print("Weapon: ");
		if (item != null)
			DisplayEquipmentItem(item);
		else
			System.out.println();
		System.out.println();

		item = hero.GetItem(Item.Type.ARMOR);
		System.out.print("Armor: ");
		if (item != null)
			DisplayEquipmentItem(item);
		else
			System.out.println();
		System.out.println();

		item = hero.GetItem(Item.Type.HELMET);
		System.out.print("Helmet: ");
		if (item != null)
			DisplayEquipmentItem(item);
		else
			System.out.println();
		System.out.println();

		item = hero.GetItem(Item.Type.RELIC);
		System.out.print("Relic: ");
		if (item != null)
			DisplayEquipmentItem(item);
		else
			System.out.println();
		System.out.println();

		System.out.println("Press Enter to exit");
		GetUserInput();
	}

	public String GetUserInput()
	{
		String input = this._scanner.nextLine();

		return input;
	}

	public void DisplayMap(GameMap map)
	{
		int size = map.GetSize();
		GameMap.Element[][] grid = map.GetGrid();

		for (int i = 0; i < size + 2; i++)
			System.out.print("-");
		System.out.println();

		for (int i = 0; i < size; i++)
		{
			System.out.print("|");
			for (int j = 0; j < size; j++)
			{
				switch (grid[i][j])
				{
					case EMPTY:
						System.out.print(" ");
						break;

					case WALL:
						System.out.print("#");
						break;

					case HERO:
						System.out.print("H");
						break;

					case VILLAIN:
						System.out.print("V");
						break;

					default:
						break;
				}
			}
			System.out.println("|");
		}

		for (int i = 0; i < size + 2; i++)
			System.out.print("-");
		System.out.println();
	}

	public void DisplayMainView(GameMap map, Hero hero)
	{
		while (true)
		{
			Clear();
			DisplayMap(map);

			System.out.println();
			
			DisplayInputHelp();
			System.out.println();
			DisplayPrompt();
			
			String input = GetUserInput().toUpperCase();
			
			if (input.equals("E"))
			{
				DisplayEquipment(hero);
			}
			if (input.equals("C"))
			{
				DisplayHero(hero);
			}
			if (input.equals("Q"))
			{
				break;
			}
		}
	}
}
