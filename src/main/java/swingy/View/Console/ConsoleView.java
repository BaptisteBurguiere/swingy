package swingy.View.Console;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import swingy.Model.CombatTurnResult;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.SaveFile;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.View;
import swingy.Model.Entity;
import swingy.Model.GameMap;

public class ConsoleView extends View
{
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String MAGENTA = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

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
		{
			String color = RESET;
			switch (item.GetRarity())
			{
				case COMMON:
					color = WHITE;
					break;
				case RARE:
					color = BLUE;
					break;
				case EPIC:
					color = MAGENTA;
					break;
				case LEGENDARY:
					color = YELLOW;
					break;
			}
			to_display += String.format("%s (%s%s%s)", item.GetName(), color, item.GetRarityStr(), RESET);
		}
		System.out.println(to_display);

		item = hero.GetItem(Item.Type.ARMOR);
		to_display = "  Armor: ";
		if (item != null)
		{
			String color = RESET;
			switch (item.GetRarity())
			{
				case COMMON:
					color = WHITE;
					break;
				case RARE:
					color = BLUE;
					break;
				case EPIC:
					color = MAGENTA;
					break;
				case LEGENDARY:
					color = YELLOW;
					break;
			}
			to_display += String.format("%s (%s%s%s)", item.GetName(), color, item.GetRarityStr(), RESET);
		}
		System.out.println(to_display);

		item = hero.GetItem(Item.Type.HELMET);
		to_display = "  Helmet: ";
		if (item != null)
		{
			String color = RESET;
			switch (item.GetRarity())
			{
				case COMMON:
					color = WHITE;
					break;
				case RARE:
					color = BLUE;
					break;
				case EPIC:
					color = MAGENTA;
					break;
				case LEGENDARY:
					color = YELLOW;
					break;
			}
			to_display += String.format("%s (%s%s%s)", item.GetName(), color, item.GetRarityStr(), RESET);
		}
		System.out.println(to_display);

		item = hero.GetItem(Item.Type.RELIC);
		to_display = "  Relic: ";
		if (item != null)
		{
			String color = RESET;
			switch (item.GetRarity())
			{
				case COMMON:
					color = WHITE;
					break;
				case RARE:
					color = BLUE;
					break;
				case EPIC:
					color = MAGENTA;
					break;
				case LEGENDARY:
					color = YELLOW;
					break;
			}
			to_display += String.format("%s (%s%s%s)", item.GetName(), color, item.GetRarityStr(), RESET);
		}
		System.out.println(to_display);

		System.out.println();
		System.out.println("Press Enter to exit");

		GetUserInput();
	}

	public void DisplayItem(Item item)
	{
		String color = RESET;
		switch (item.GetRarity())
		{
			case COMMON:
				color = WHITE;
				break;
			case RARE:
				color = BLUE;
				break;
			case EPIC:
				color = MAGENTA;
				break;
			case LEGENDARY:
				color = YELLOW;
				break;
		}
		String to_display = String.format("[%s] %s (%s%s%s)", item.GetTypeStr(), item.GetName(), color, item.GetRarityStr(), RESET);
		System.out.println(to_display);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
		{
			Statistic stat = entry.getValue();
			switch (stat.GetType())
			{
				case HEALTH:
					if (item.GetType() == Item.Type.HEALING)
						to_display = String.format("  +%.0f%% %s", stat.GetValue(), stat.GetName());
					else
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
		Clear();

		String to_display;
		String attacker_color = result.hero_turn ? CYAN : RED;
		String defender_color = result.hero_turn ? RED : CYAN;

		if (result.defense_stance)
		{
			to_display = String.format("%s%s%s adopts a defensive stance!", attacker_color, result.attacker.GetName(), RESET);
			System.out.println(to_display);
			return;
		}

		if (result.try_flee)
		{
			to_display = String.format("%s%s%s tries to fly away!", attacker_color, result.attacker.GetName(), RESET);
			System.out.println(to_display);

			if (result.flee_successful)
				to_display = String.format("%s%s%s escaped successfully!", attacker_color, result.attacker.GetName(), RESET);
			else
				to_display = String.format("%s%s%s flew in spirit, but his legs disagreed.", attacker_color, result.attacker.GetName(), RESET);

			System.out.println(to_display);
			return;
		}

		to_display = String.format("%s%s%s attacks %s%s%s!", attacker_color, result.attacker.GetName(), RESET, defender_color, result.defender.GetName(), RESET);
		System.out.println(to_display);

		if (result.parried)
		{
			to_display = String.format("%s%s%s parries the attack!", defender_color, result.defender.GetName(), RESET);
			System.out.println(to_display);
		}

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

	public void DiplayInputHelpEquipItem()
	{
		System.out.println("Controls:");
		System.out.println("  y: Equip item");
		System.out.println("  n: Leave item");
		System.out.println("  e: Display equipment");
		System.out.println("  c: Display hero statistics");
	}

	public void DisplayPrompt()
	{
		System.out.print("> ");
	}

	public void DisplayEquipmentItem(Item item)
	{
		String color = RESET;
		switch (item.GetRarity())
		{
			case COMMON:
				color = WHITE;
				break;
			case RARE:
				color = BLUE;
				break;
			case EPIC:
				color = MAGENTA;
				break;
			case LEGENDARY:
				color = YELLOW;
				break;
		}
		String to_display = String.format("%s (%s%s%s)", item.GetName(), color, item.GetRarityStr(), RESET);
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

					case CHEST:
						System.out.print("C");

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

	public Action DisplayMainView(GameMap map, Hero hero)
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
				return Action.DISPLAY_EQUIPMENT;
	
			if (input.equals("C"))
				return Action.DISPLAY_STATISTICS;

			if (input.equals("H"))
			{
				DisplayHelp();
				continue;
			}
	
			if (input.equals("Q"))
			{
				Clear();
				return Action.QUIT;
			}
	
			if (input.equals("W"))
				return Action.MOVE_UP;
	
			if (input.equals("A"))
				return Action.MOVE_LEFT;
	
			if (input.equals("S"))
				return Action.MOVE_DOWN;
			
			if (input.equals("D"))
				return Action.MOVE_RIGHT;
		}
	}

	public void DisplayStartCombat(Hero hero, Villain villain, boolean is_boss)
	{
		Clear();
		
		String to_display = String.format("%s starts a fight against %s lvl. %d!", hero.GetName(), villain.GetName(), villain.GetLevel());
		System.out.println(to_display);
	}

	public Action DiplayEquipItem(Item item)
	{
		while (true)
		{
			Clear();
			System.out.println("The ennemy dropped an item!");
			System.out.println();
			DisplayItem(item);
			System.out.println();
			DiplayInputHelpEquipItem();
			System.out.println();
			DisplayPrompt();

			String input = GetUserInput().toUpperCase();

			if (input.equals("Y"))
				return Action.EQUIP_ITEM;
			
			if (input.equals("N"))
				return Action.LEAVE_ITEM;

			if (input.equals("E"))
				return Action.DISPLAY_EQUIPMENT;
			
			if (input.equals("C"))
				return Action.DISPLAY_STATISTICS;
		}
	}

	public int DisplayChooseSave(SaveFile save_file)
	{
		while (true)
		{
			Clear();

			System.out.println("Choose a save slot:");
			System.out.println();

			for (int i = 0; i < SaveFile.NB_SAVES; i++)
			{
				Hero hero = save_file.heroes[i];
				String to_display = "";

				if (hero == null)
					to_display = String.format("%d: Empty", i + 1);
				else
					to_display = String.format("%d: %s lvl. %d - %s", i + 1, hero.GetClassStr(), hero.GetLevel(), hero.GetName());

				System.out.println(to_display);
			}

			System.out.println();
			DisplayPrompt();

			String input = GetUserInput();

			try
			{
				int int_input = Integer.parseInt(input);

				if (int_input > 0 && int_input <= SaveFile.NB_SAVES)
					return int_input - 1;
			}
			catch (Exception e) {}
		}
	}

	public Hero.Class DisplayCreateHeroClass()
	{
		EnumSet<Hero.Class> all_classes = EnumSet.allOf(Hero.Class.class);
		List<Hero.Class> all_classes_lst = new ArrayList<>(all_classes);
		List<String> all_classes_str = new ArrayList<String>();

		for (Hero.Class Class : all_classes)
			all_classes_str.add(Hero.GetClassStr(Class));
		
		while (true)
		{
			Clear();

			System.out.println("Choose a class:");
			System.out.println();
			
			for (int i = 0; i < all_classes.size(); i++)
			{
				String to_display = String.format("%d: %s", i + 1, all_classes_str.get(i));
				System.out.println(to_display);
			}

			System.out.println();
			DisplayPrompt();

			String input = GetUserInput();

			try
			{
				int int_input = Integer.parseInt(input);

				if (int_input > 0 && int_input <= SaveFile.NB_SAVES)
					return all_classes_lst.get(int_input - 1);
			}
			catch (Exception e) {}
		}
	}

	public String DisplayCreateHeroName()
	{
		while (true)
		{
			Clear();
	
			System.out.println("Choose a name:");
			System.out.println();
			DisplayPrompt();
	
			String input = GetUserInput();
			input = input.trim();

			if (input.length() > 0)
				return input;
		}
	}

	public Action DisplayHeroCombatChoice(Hero hero, Villain villain, List<Entity> next_turns, boolean is_boss)
	{
		while (true)
		{
			Clear();

			double current_health = villain.GetCurrentHealth();
			double health = villain.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue();
			String health_color = current_health / health <= 0.2 ? RED : RESET;

			String to_display = String.format("Ennemy %s%s%s lvl. %d (%s%d%s/%d)",
				RED, villain.GetName(), RESET, villain.GetLevel(), health_color, (int)villain.GetCurrentHealth(), RESET, (int)health);
			System.out.println(to_display);

			System.out.println();

			current_health = hero.GetCurrentHealth();
			health = hero.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue();
			health_color = current_health / health <= 0.2 ? RED : RESET;

			to_display = String.format("%s%s%s lvl. %d (%s%d%s/%d)",
				CYAN, hero.GetName(), RESET, hero.GetLevel(), health_color, (int)hero.GetCurrentHealth(), RESET, (int)health);
			System.out.println(to_display);

			System.out.println();

			System.out.print("Next turns: ");

			to_display = String.format("%s%s%s > ", GREEN, next_turns.get(0).GetName(), RESET);
			System.out.print(to_display);
			for (int i = 1; i < next_turns.size() - 1; i++)
			{
				Entity entity = next_turns.get(i);
				to_display = String.format("%s > ", entity.GetName());
				System.out.print(to_display);
			}
			to_display = String.format("%s", next_turns.get(next_turns.size() - 1).GetName());
			System.out.println(to_display);

			System.out.println();

			System.out.println();
			DisplayHelpCombat(is_boss);
			System.out.println();

			DisplayPrompt();

			String input = GetUserInput().toUpperCase();

			if (input.equals("A"))
				return Action.ATTACK;

			if (input.equals("D"))
				return Action.DEFEND;

			if (!is_boss && input.equals("F"))
				return Action.FLEE;
		}
	}

	public void DisplayHelpCombat(boolean is_boss)
	{
		System.out.println("Controls:");
		System.out.println("  a: Attack");
		System.out.println("  d: Defend");
		if (!is_boss)
			System.out.println("  f: Flee");
	}

	public int DisplayChooseChestContent(Hero hero, List<Item> chest_content)
	{
		while (true)
		{
			Clear();

			String to_display = String.format("%s%s%s %d/%d", CYAN, hero.GetName(), RESET, (int)hero.GetCurrentHealth(), (int)hero.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue());
			System.out.println(to_display);

			System.out.println("Pick and item:");
			System.out.println();

			for (int i = 0; i < chest_content.size(); i++)
			{
				Item item = chest_content.get(i);

				System.out.print(String.format("%d: ", i + 1));
				DisplayItem(item);
				System.out.println();
			}

			System.out.println();
			DisplayPrompt();

			String input = GetUserInput();

			try
			{
				int int_input = Integer.parseInt(input);

				if (int_input > 0 && int_input <= chest_content.size())
					return int_input - 1;
			}
			catch (Exception e) {}
		}
	}

	public void DisplayChestSpawn()
	{
		Clear();

		System.out.println("As all ennemies in the room are defeated,\nyou can hear a mechanism revealing a chest in the center of the room.");
	}

	public void DisplayHelp()
	{
		String to_display;

		System.out.println("Gameplay:");
		System.out.println("  You spawn on a map filled with ennemies.");
		System.out.println("  Your objective is to defeat them to level up and get a chance to drop loot.");
		System.out.println("  Level up enough to confidently escape the room and face the boss.");
		System.out.println("  You can escape the room by hitting a border");
		System.out.println("  The game ends when you die.");

		System.out.println();

		System.out.println("Combat:");
		System.out.println("  Turn by turn combat system.");
		System.out.println("  You have multiple options when fighting an ennemy:");
		System.out.println("    Attack: I don't think I need to explain this one.");
		System.out.println("    Defend: Boost your defense until your next turn.");
		System.out.println("            If you have enough defense, you might be able to parry the incoming attack.");
		System.out.println("    Flee: If the ennemy is too strong for you, fly away might be the best option for you.");
		System.out.println("          The more speed and evasion you have compared to your opponent, the more chance you have to escape.");

		System.out.println();

		System.out.println("Statistics:");
		for (Map.Entry<StatisticTemplate.Type, StatisticTemplate> entry : StatisticTemplate.GetTemplates().entrySet())
		{
			StatisticTemplate stat = entry.getValue();
			to_display = String.format("  %s: %s", stat.GetName(), stat.GetDescription());
			System.out.println(to_display);
		}

		System.out.println();

		System.out.println("Items:");
		System.out.println("  Items have a rarity according to the power of their effect:");
		to_display = String.format("    %sCommon%s, %sRare%s, %sEpic%s, %sLegendary%s", WHITE, RESET, BLUE, RESET, MAGENTA, RESET, YELLOW, RESET);
		System.out.println(to_display);
		System.out.println("  You have multiple item categories, some you can equip and some you cannot.");
		System.out.println("  Equipments:");
		System.out.println("    Weapon: Boosts your attack");
		System.out.println("    Armor: Boosts your defense");
		System.out.println("    Helmet: Boosts your health");
		System.out.println("    Relic: Boosts a secondary statistic");
		System.out.println("  Consumables:");
		System.out.println("    Healing: Heals by the amount");
		System.out.println("    Essence: Permanantly boosts a statistic");

		System.out.println();

		System.out.println("Classes:");
		System.out.println("  Choose your class according to the gameplay you want to play.");
		System.out.println("    Knight: Balanced");
		System.out.println("    Guardian: Focussed on health and defense");
		System.out.println("    Juggernaut: Focussed on health and attack");
		System.out.println("    Assassin: Focussed on speed, evasion and crit chance");
		System.out.println("    Berserker: Focussed on attack, crit chance and crit damage");
		System.out.println("    Gambler: Focussed on luck, will level up luck and 2 random statistics when leveling up");

		System.out.println();
		
		System.out.println("Press Enter to exit");

		GetUserInput();
	}
}
