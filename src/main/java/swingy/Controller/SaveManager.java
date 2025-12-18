package swingy.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import swingy.Model.GameStats;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.PantheonFile;
import swingy.Model.SaveFile;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;

public class SaveManager
{
	private static final String SAVE_PATH = "./swingy_save.cfg";
	private static final String PANTHEON_PATH = "./swingy_pantheon.cfg";

	private static SaveManager	_instance = null;

	private SaveFile		_save_file;
	private Path			_save_path;
	private PantheonFile	_pantheon_file;
	private Path			_pantheon_path;

	public static SaveManager GetInstance() throws Exception
	{
		if (_instance == null)
			_instance = new SaveManager();

		return _instance;
	}

	public SaveFile GetSaveFile() { return _save_file;}
	public PantheonFile GetPantheonFile() { return _pantheon_file; }
	public Hero GetSave(int save_slot) { return this._save_file.heroes[save_slot]; }
	public GameStats GetSaveStats(int save_slot) { return this._save_file.stats[save_slot]; }
	public void SetSave(int save_slot, Hero hero, GameStats stats)
	{
		this._save_file.heroes[save_slot] = hero;
		this._save_file.stats[save_slot] = stats;
	}

	private SaveManager() throws Exception
	{
		try
		{
			_save_file = new SaveFile();
			_save_path = Paths.get(SAVE_PATH);
			_pantheon_file = new PantheonFile();
			_pantheon_path = Paths.get(PANTHEON_PATH);

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

			if (Files.exists(_pantheon_path))
			{
				if (!ParsePantheonFile())
					throw new Exception("Error: Invalid pantheon file");
			}
			else
			{
				Files.createDirectories(_pantheon_path.getParent());
				Files.createFile(_pantheon_path);
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
					this._save_file.stats[current_hero] = null;
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

					int damage_dealt = 0;
					int damage_received = 0;
					int crit = 0;
					int missed = 0;
					int dodged = 0;
					int parried = 0;
					int flee_failed = 0;
					int flee_success = 0;
					int villain_slained = 0;
					int rooms_exited = 0;
					int boss_slained = 0;
					int items_equiped = 0;
					int chest_opened = 0;
					boolean has_won = false;
					LocalDate win_date = null;
					boolean endless_mode = false;

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

							case "damage_dealt":
								damage_dealt = Integer.parseInt(value);
								break;

							case "damage_received":
								damage_received = Integer.parseInt(value);
								break;

							case "crit":
								crit = Integer.parseInt(value);
								break;

							case "missed":
								missed = Integer.parseInt(value);
								break;

							case "dodged":
								dodged = Integer.parseInt(value);
								break;

							case "parried":
								parried = Integer.parseInt(value);
								break;

							case "flee_failed":
								flee_failed = Integer.parseInt(value);
								break;

							case "flee_success":
								flee_success = Integer.parseInt(value);
								break;

							case "villain_slained":
								villain_slained = Integer.parseInt(value);
								break;

							case "rooms_exited":
								rooms_exited = Integer.parseInt(value);
								break;

							case "boss_slained":
								boss_slained = Integer.parseInt(value);
								break;

							case "items_equiped":
								items_equiped = Integer.parseInt(value);
								break;

							case "chest_opened":
								chest_opened = Integer.parseInt(value);
								break;

							case "has_won":
								has_won = Boolean.parseBoolean(value);
								break;

							case "win_date":
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
								win_date = LocalDate.parse(value, dtf);

							case "endless_mode":
								endless_mode = Boolean.parseBoolean(value);
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

					GameStats current_stats = new GameStats();
					current_stats.damage_dealt = damage_dealt;
					current_stats.damage_received = damage_received;
					current_stats.crit = crit;
					current_stats.missed = missed;
					current_stats.dodged = dodged;
					current_stats.parried = parried;
					current_stats.flee_failed = flee_failed;
					current_stats.flee_success = flee_success;
					current_stats.villain_slained = villain_slained;
					current_stats.rooms_exited = rooms_exited;
					current_stats.boss_slained = boss_slained;
					current_stats.items_equiped = items_equiped;
					current_stats.chest_opened = chest_opened;
					current_stats.has_won = has_won;
					current_stats.win_date = win_date;
					current_stats.endless_mode = endless_mode;

					this._save_file.stats[current_hero] = current_stats;
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

	private boolean ParsePantheonFile()
	{
		try
		{
			List<String> lines = new ArrayList<String>();
			Scanner scanner = new Scanner(_pantheon_path);

			while (scanner.hasNextLine())
				lines.add(scanner.nextLine());

			scanner.close();

			AtomicInteger i = new AtomicInteger(0);

			while (i.get() < lines.size())
			{
				if (lines.get(i.get()).equals("BEGIN"))
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

					int damage_dealt = 0;
					int damage_received = 0;
					int crit = 0;
					int missed = 0;
					int dodged = 0;
					int parried = 0;
					int flee_failed = 0;
					int flee_success = 0;
					int villain_slained = 0;
					int rooms_exited = 0;
					int boss_slained = 0;
					int items_equiped = 0;
					int chest_opened = 0;
					boolean has_won = false;
					LocalDate win_date = LocalDate.now();
					boolean endless_mode = false;

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

							case "damage_dealt":
								damage_dealt = Integer.parseInt(value);
								break;

							case "damage_received":
								damage_received = Integer.parseInt(value);
								break;

							case "crit":
								crit = Integer.parseInt(value);
								break;

							case "missed":
								missed = Integer.parseInt(value);
								break;

							case "dodged":
								dodged = Integer.parseInt(value);
								break;

							case "parried":
								parried = Integer.parseInt(value);
								break;

							case "flee_failed":
								flee_failed = Integer.parseInt(value);
								break;

							case "flee_success":
								flee_success = Integer.parseInt(value);
								break;

							case "villain_slained":
								villain_slained = Integer.parseInt(value);
								break;

							case "rooms_exited":
								rooms_exited = Integer.parseInt(value);
								break;

							case "boss_slained":
								boss_slained = Integer.parseInt(value);
								break;

							case "items_equiped":
								items_equiped = Integer.parseInt(value);
								break;

							case "chest_opened":
								chest_opened = Integer.parseInt(value);
								break;

							case "has_won":
								has_won = Boolean.parseBoolean(value);
								break;

							case "win_date":
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
								win_date = LocalDate.parse(value, dtf);

							case "endless_mode":
								endless_mode = Boolean.parseBoolean(value);
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

					this._pantheon_file.heroes.add(new Hero(Class, name, level, stats, exp, current_health, items));

					GameStats current_stats = new GameStats();
					current_stats.damage_dealt = damage_dealt;
					current_stats.damage_received = damage_received;
					current_stats.crit = crit;
					current_stats.missed = missed;
					current_stats.dodged = dodged;
					current_stats.parried = parried;
					current_stats.flee_failed = flee_failed;
					current_stats.flee_success = flee_success;
					current_stats.villain_slained = villain_slained;
					current_stats.rooms_exited = rooms_exited;
					current_stats.boss_slained = boss_slained;
					current_stats.items_equiped = items_equiped;
					current_stats.chest_opened = chest_opened;
					current_stats.has_won = has_won;
					current_stats.win_date = win_date;
					current_stats.endless_mode = endless_mode;

					this._pantheon_file.stats.add(current_stats);
				}
				else
					return false;
			}

			return true;
		}
		catch (Exception e)
		{
			System.err.println("Error: Error reading the pantheon file");
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
				SaveHero(_save_path, _save_file.heroes[i], _save_file.stats[i]);
		}
		catch (Exception e)
		{
			System.err.println("Error: Impossible to save");
			System.err.println(e.getMessage());
		}
	}

	public void AddToPantheon(Hero hero, GameStats stats)
	{
		try
		{
			SaveHero(_pantheon_path, hero, stats);
		}
		catch (Exception e)
		{
			System.err.println("Error: Impossible to add to pantheon");
			System.err.println(e.getMessage());
		}
	}

	private void ClearFile() throws IOException
	{
		Files.write(_save_path, "".getBytes());
	}

	private void WriteToFile(Path file, String str) throws IOException
	{
		Files.write(file, str.getBytes(), StandardOpenOption.APPEND);
	}

	private void SaveHero(Path file, Hero hero, GameStats stats) throws IOException
	{
		String to_write;

		WriteToFile(file, "BEGIN\n");

		if (hero != null)
		{
			to_write = String.format("class: %s\n", hero.GetClassStr());
			WriteToFile(file, to_write);

			to_write = String.format("name: %s\n", hero.GetName());
			WriteToFile(file, to_write);

			to_write = String.format("level: %d\n", hero.GetLevel());
			WriteToFile(file, to_write);

			to_write = String.format("exp: %d\n", hero.GetExperience());
			WriteToFile(file, to_write);

			to_write = String.format("current_health: %d\n", (int)hero.GetCurrentHealth());
			WriteToFile(file, to_write);

			Statistic stat = hero.GetStatistic(StatisticTemplate.Type.HEALTH);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.ATTACK);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.DEFENSE);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.SPEED);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.EVASION);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.ACCURACY);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE);
			SaveStatistic(file, stat);

			stat = hero.GetStatistic(StatisticTemplate.Type.LUCK);
			SaveStatistic(file, stat);


			Item item = hero.GetItem(Item.Type.WEAPON);
			SaveItem(file, item);

			item = hero.GetItem(Item.Type.ARMOR);
			SaveItem(file, item);

			item = hero.GetItem(Item.Type.HELMET);
			SaveItem(file, item);

			item = hero.GetItem(Item.Type.RELIC);
			SaveItem(file, item);

			SaveStats(file, stats);
		}

		WriteToFile(file, "END\n");
	}

	private void SaveStats(Path file, GameStats stats) throws IOException
	{
		WriteToFile(file, String.format("damage_dealt: %d\n", stats.damage_dealt));
		WriteToFile(file, String.format("damage_received: %d\n", stats.damage_received));
		WriteToFile(file, String.format("crit: %d\n", stats.crit));
		WriteToFile(file, String.format("missed: %d\n", stats.missed));
		WriteToFile(file, String.format("dodged: %d\n", stats.dodged));
		WriteToFile(file, String.format("parried: %d\n", stats.parried));
		WriteToFile(file, String.format("flee_failed: %d\n", stats.flee_failed));
		WriteToFile(file, String.format("flee_success: %d\n", stats.flee_success));
		WriteToFile(file, String.format("villain_slained: %d\n", stats.villain_slained));
		WriteToFile(file, String.format("rooms_exited: %d\n", stats.rooms_exited));
		WriteToFile(file, String.format("boss_slained: %d\n", stats.boss_slained));
		WriteToFile(file, String.format("items_equiped: %d\n", stats.items_equiped));
		WriteToFile(file, String.format("chest_opened: %d\n", stats.chest_opened));
		WriteToFile(file, String.format("has_won: %b\n", stats.has_won));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		WriteToFile(file, String.format("win_date: %s", stats.win_date.format(dtf)));
		WriteToFile(file, String.format("endless_mode: %b\n", stats.endless_mode));
	}

	private void SaveStatistic(Path file, Statistic stat) throws IOException
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

		to_write.replace(',', '.');
		
		WriteToFile(file, to_write);
	}

	private void SaveItem(Path file, Item item) throws IOException
	{
		if (item != null)
		{
			WriteToFile(file, String.format("%s\n", item.GetTypeStr()));
			WriteToFile(file, "BEGIN\n");

			String to_write = String.format("name: %s\n", item.GetName());
			WriteToFile(file, to_write);

			to_write = String.format("rarity: %s\n", item.GetRarityStr());
			WriteToFile(file, to_write);

			to_write = String.format("description: %s\n", item.GetDescription());
			WriteToFile(file, to_write);

			for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
			{
				Statistic stat = entry.getValue();
				SaveStatistic(file, stat);
			}

			WriteToFile(file, "END\n");
		}
	}

	public void DeleteHero(Hero hero)
	{
		for (int i = 0; i < SaveFile.NB_SAVES; i++)
		{
			if (this._save_file.heroes[i] == hero)
			{
				this._save_file.heroes[i] = null;
				this._save_file.stats[i] = null;
				break;
			}
		}

		Save();
	}

	public boolean IsPantheon()
	{
		return !this._pantheon_file.heroes.isEmpty();
	}
}
