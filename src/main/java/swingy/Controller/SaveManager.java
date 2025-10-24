package swingy.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.SaveFile;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;

public class SaveManager
{
	private static final String SAVE_PATH = "./swingy_save.cfg";

	private static SaveManager	_instance = null;

	private SaveFile		_save_file;
	private Path			_save_path;

	public static SaveManager GetInstance() throws Exception
	{
		if (_instance == null)
			_instance = new SaveManager();

		return _instance;
	}

	public SaveFile GetSaveFile() { return _save_file;}
	public Hero GetSave(int save_slot) { return this._save_file.heroes[save_slot]; }
	public void SetSave(int save_slot, Hero hero) { this._save_file.heroes[save_slot] = hero; }

	private SaveManager() throws Exception
	{
		try
		{
			_save_file = new SaveFile();
			_save_path = Paths.get(SAVE_PATH);

			if (Files.exists(_save_path))
			{
				if (!ParseSaveFile())
					throw new Exception("Error: Invalid save file");
			}
			else
			{
				Files.createDirectories(_save_path.getParent());
				Files.createFile(_save_path);
				Save();
			}
		}
		catch (Exception e)
		{
			System.err.println("Error: Impossible to create the save file");
			System.err.println(e.getMessage());
		}
	}

	private boolean ParseSaveFile()
	{
		try
		{
			List<String> lines = new ArrayList<String>();
			Scanner scanner = new Scanner(_save_path);

			while (scanner.hasNextLine())
				lines.add(scanner.nextLine());

			scanner.close();

			int current_hero = 0;
			AtomicInteger i = new AtomicInteger(0);

			while (i.get() < lines.size())
			{
				if (current_hero >= SaveFile.NB_SAVES)
					return false;

				if (lines.get(i.get()).equals("BEGIN") && lines.get(i.get() + 1).equals("END"))
				{
					this._save_file.heroes[current_hero] = null;
					i.set(i.get() + 2);
				}
				else if (lines.get(i.get()).equals("BEGIN"))
				{
					i.getAndIncrement();

					Hero.Class Class = null;
					String name = null;
					int level = 0;
					int exp = -1;
					double current_health = 0;
					double health = 0;
					double attack = 0;
					double defense = 0;
					double speed = 0;
					double evasion = 0;
					double accuracy = 0;
					double crit_chance = 0;
					double crit_damage = 0;
					double luck = 0;
					Map<Item.Type, Item> items = new EnumMap<>(Item.Type.class);

					boolean is_current = true;

					while (is_current)
					{
						String[] key_value = lines.get(i.get()).split(":");
						String key = key_value[0].trim();
						String value = key_value.length == 2 ? key_value[1].trim() : null;

						switch (key) {
							case "class":
								Class = ParseClass(value);
								break;
	
							case "name":
								name = value;
								break;
	
							case "level":
								level = Integer.parseInt(value);
								break;
	
							case "exp":
								exp = Integer.parseInt(value);
								break;
	
							case "current_health":
								current_health = Double.parseDouble(value);
								break;
	
							case "Health":
								health = Double.parseDouble(value);
								break;
	
							case "Attack":
								attack = Double.parseDouble(value);
								break;
	
							case "Defense":
								defense = Double.parseDouble(value);
								break;
	
							case "Speed":
								speed = Double.parseDouble(value);
								break;
	
							case "Evasion":
								evasion = Double.parseDouble(value);
								break;
	
							case "Accuracy":
								accuracy = Double.parseDouble(value);
								break;
	
							case "Critical Chance":
								crit_chance = Double.parseDouble(value);
								break;
	
							case "Critical Damage":
								crit_damage = Double.parseDouble(value);
								break;
	
							case "Luck":
								luck = Double.parseDouble(value);
								break;

							case "Weapon":
								items.put(Item.Type.WEAPON, ParseItem(Item.Type.WEAPON, lines, i));
								break;

							case "Armor":
								items.put(Item.Type.ARMOR, ParseItem(Item.Type.ARMOR, lines, i));
								break;

							case "Helmet":
								items.put(Item.Type.HELMET, ParseItem(Item.Type.HELMET, lines, i));
								break;

							case "Relic":
								items.put(Item.Type.RELIC, ParseItem(Item.Type.RELIC, lines, i));
								break;

							case "END":
								is_current = false;
								break;
						
							default:
								return false;
						}

						i.getAndIncrement();
					}

					if (	Class == null
						||	name == null
						||	level == 0
						||	exp == -1
						||	current_health == 0
						||	health == 0
						||	attack == 0
						||	defense == 0
						||	speed == 0
						||	evasion == 0
						||	accuracy == 0
						||	crit_chance == 0
						||	crit_damage == 0
						||	luck == 0)
						return false;

					Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

					stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, health));
					stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, attack));
					stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, defense));
					stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, speed));
					stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, evasion));
					stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, accuracy));
					stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, crit_chance));
					stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, crit_damage));
					stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, luck));

					this._save_file.heroes[current_hero] = new Hero(Class, name, level, stats, exp, current_health, items);
				}
				else
					return false;

				current_hero++;
			}

			return true;
		}
		catch (Exception e)
		{
			System.err.println("Error: Error reading the save file");
			System.err.println(e.getMessage());
			return false;
		}
	}

	private Hero.Class ParseClass(String value)
	{
		EnumSet<Hero.Class> all_classes = EnumSet.allOf(Hero.Class.class);

		for (Hero.Class Class : all_classes)
		{
			if (value.equals(Hero.GetClassStr(Class)))
				return Class;
		}
		
		return null;
	}

	private Item ParseItem(Item.Type type, List<String> lines, AtomicInteger i) throws Exception
	{
		i.getAndIncrement();

		if (lines.get(i.get()).equals("BEGIN"))
		{
			i.getAndIncrement();

			String name = null;
			String description = null;
			Item.Rarity rarity = null;
			Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

			boolean is_item = true;

			while (is_item)
			{
				String[] key_value = lines.get(i.get()).split(":");
				String key = key_value[0].trim();
				String value = key_value.length == 2 ? key_value[1].trim() : null;

				switch (key)
				{
					case "name":
						name = value;
						break;

					case "description":
						description = value;
						break;

					case "rarity":
						rarity = ParseRarity(value);
						break;

					case "Health":
						stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, Double.parseDouble(value)));
						break;

					case "Attack":
						stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, Double.parseDouble(value)));
						break;

					case "Defense":
						stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, Double.parseDouble(value)));
						break;

					case "Speed":
						stats.put(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, Double.parseDouble(value)));
						break;

					case "Evasion":
						stats.put(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, Double.parseDouble(value)));
						break;

					case "Accuracy":
						stats.put(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, Double.parseDouble(value)));
						break;

					case "Critical Chance":
						stats.put(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, Double.parseDouble(value)));
						break;

					case "Critical Damage":
						stats.put(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, Double.parseDouble(value)));
						break;

					case "Luck":
						stats.put(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, Double.parseDouble(value)));
						break;

					case "END":
						is_item = false;
						break;
				
					default:
						throw new Exception("Invalid Item");
				}

				i.getAndIncrement();
			}
			i.getAndDecrement();

			if (	name == null
				||	description == null
				||	rarity == null)
				throw new Exception("Invalid Item");
			
			return new Item(type, rarity, name, description, stats);
		}
		else
			throw new Exception("Invalid Item");
	}

	private Item.Rarity ParseRarity(String value)
	{
		EnumSet<Item.Rarity> all_rarities = EnumSet.allOf(Item.Rarity.class);

		for (Item.Rarity rarity : all_rarities)
		{
			if (value.equals(Item.GetRarityStr(rarity)))
				return rarity;
		}
		
		return null;
	}

	public void Save()
	{
		try
		{
			ClearFile();

			for (int i = 0; i < SaveFile.NB_SAVES; i++)
				SaveHero(_save_file.heroes[i]);
		}
		catch (Exception e)
		{
			System.err.println("Error: Impossible to save");
			System.err.println(e.getMessage());
		}
	}

	private void ClearFile() throws IOException
	{
		Files.write(_save_path, "".getBytes());
	}

	private void WriteToFile(String str) throws IOException
	{
		Files.write(_save_path, str.getBytes(), StandardOpenOption.APPEND);
	}

	private void SaveHero(Hero hero) throws IOException
	{
		String to_write;

		WriteToFile("BEGIN\n");

		if (hero != null)
		{
			to_write = String.format("class: %s\n", hero.GetClassStr());
			WriteToFile(to_write);

			to_write = String.format("name: %s\n", hero.GetName());
			WriteToFile(to_write);

			to_write = String.format("level: %d\n", hero.GetLevel());
			WriteToFile(to_write);

			to_write = String.format("exp: %d\n", hero.GetExperience());
			WriteToFile(to_write);

			to_write = String.format("current_health: %d\n", (int)hero.GetCurrentHealth());
			WriteToFile(to_write);

			Statistic stat = hero.GetStatistic(StatisticTemplate.Type.HEALTH);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.ATTACK);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.DEFENSE);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.SPEED);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.EVASION);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.ACCURACY);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE);
			SaveStatistic(stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.LUCK);
			SaveStatistic(stat);


			Item item = hero.GetItem(Item.Type.WEAPON);
			SaveItem(item);

			item = hero.GetItem(Item.Type.ARMOR);
			SaveItem(item);

			item = hero.GetItem(Item.Type.HELMET);
			SaveItem(item);

			item = hero.GetItem(Item.Type.RELIC);
			SaveItem(item);
		}

		WriteToFile("END\n");
	}

	private void SaveStatistic(Statistic stat) throws IOException
	{
		String to_write = "";

		switch (stat.GetType())
		{
			case HEALTH:
				to_write = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
				break;

			case ATTACK:
				to_write = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
				break;

			case DEFENSE:
				to_write = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
				break;

			case SPEED:
				to_write = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
				break;

			case EVASION:
				to_write = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
				break;

			case ACCURACY:
				to_write = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
				break;

			case CRIT_CHANCE:
				to_write = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
				break;

			case CRIT_DAMAGE:
				to_write = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
				break;

			case LUCK:
				to_write = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
				break;
		
			default:
				break;
		}
		
		WriteToFile(to_write);
	}

	private void SaveItem(Item item) throws IOException
	{
		if (item != null)
		{
			WriteToFile(String.format("%s\n", item.GetTypeStr()));
			WriteToFile("BEGIN\n");

			String to_write = String.format("name: %s\n", item.GetName());
			WriteToFile(to_write);

			to_write = String.format("rarity: %s\n", item.GetRarityStr());
			WriteToFile(to_write);

			to_write = String.format("description: %s\n", item.GetDescription());
			WriteToFile(to_write);

			for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
			{
				Statistic stat = entry.getValue();
				SaveStatistic(stat);
			}

			WriteToFile("END\n");
		}
	}

	public void DeleteHero(Hero hero)
	{
		for (int i = 0; i < SaveFile.NB_SAVES; i++)
		{
			if (this._save_file.heroes[i] == hero)
			{
				this._save_file.heroes[i] = null;
				break;
			}
		}

		Save();
	}
}
