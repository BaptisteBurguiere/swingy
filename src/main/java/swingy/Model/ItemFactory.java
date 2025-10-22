package swingy.Model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class ItemFactory
{
	private ItemFactory() {}

	private static final Random rand = new Random();

	private static final double BASE_DROP_CHANCE = 0.25;
	private static final int LEVEL_CHUNK = 10;
	private static final double DROP_CHANCE_SHIFT = 0.05;
	private static final double LUCK_DROP_CHANCE = 0.005;
	private static final double BASE_RARE_DROP_CHANCE = 0.15;
	private static final double BASE_EPIC_DROP_CHANCE = 0.03;
	private static final double BASE_LEGENDARY_DROP_CHANCE = 0;
	private static final double RARE_DROP_CHANCE_SHIFT = 0.03;
	private static final double EPIC_DROP_CHANCE_SHIFT = 0.01;
	private static final double LEGENDARY_DROP_CHANCE_SHIFT = 0.005;
	private static final double LUCK_RARITY_SHIFT = 0.0005;

	public static Item GenerateItem(Hero hero)
	{
		double luck = hero.GetStatistic(StatisticTemplate.Type.LUCK).GetValue();
		double drop_chance = BASE_DROP_CHANCE + ((double)(hero.GetLevel() / LEVEL_CHUNK) * DROP_CHANCE_SHIFT) + luck * LUCK_DROP_CHANCE;

		if (rand.nextDouble() < drop_chance)
			return null;

		double legendary_drop_chance = BASE_LEGENDARY_DROP_CHANCE + ((double)(hero.GetLevel() / LEVEL_CHUNK) * LEGENDARY_DROP_CHANCE_SHIFT) + luck * LUCK_RARITY_SHIFT;
		double epic_drop_chance = BASE_EPIC_DROP_CHANCE + ((double)(hero.GetLevel() / LEVEL_CHUNK) * EPIC_DROP_CHANCE_SHIFT) + luck * LUCK_RARITY_SHIFT;
		double rare_drop_chance = BASE_RARE_DROP_CHANCE + ((double)(hero.GetLevel() / LEVEL_CHUNK) * RARE_DROP_CHANCE_SHIFT) + luck * LUCK_RARITY_SHIFT;
		
		double rarity_roll = rand.nextDouble();
		if (rarity_roll < legendary_drop_chance)
			return GenerateLegendaryItem();
		if (rarity_roll < legendary_drop_chance + epic_drop_chance)
			return GenerateEpicItem();
		if (rarity_roll < legendary_drop_chance + epic_drop_chance + rare_drop_chance)
			return GenerateRareItem();
		else
			return GenerateCommonItem();
	}

	private static final int COMMON_MIN_ATTACK = 5;
	private static final int COMMON_MAX_ATTACK = 15;
	private static final int COMMON_MIN_DEFENSE = 5;
	private static final int COMMON_MAX_DEFENSE = 15;
	private static final int COMMON_MIN_HEALTH = 15;
	private static final int COMMON_MAX_HEALTH = 40;

	private static final List<Item> COMMON_TEMPLATES = List.of(
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Rusty Sword", "A worn sword, barely sharp but better than bare fists"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Cloth Armor", "Simple cloth covering, barely protective"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Cloth Cap", "Simple cloth cap, gives minimal protection"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Wooden Club", "Simple club made of wood, heavy and clumsy"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Leather Armor", "Lightweight leather, offers moderate protection"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Leather Cap", "Leather headgear, slightly sturdier than cloth"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Iron Dagger", "A small but sturdy blade, quick to swing"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Hide Armor", "Animal hide armor, tough and durable"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Iron Helmet", "Basic iron helmet, blocks light blows"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Steel Sword", "Balanced steel sword, reliable for any fight"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Iron Armor", "Basic iron armor, good balance of weight and defense"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Steel Helmet", "Strong steel helmet, protects well"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Battle Axe", "Heavy axe that delivers powerful strikes"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Scale Armor", "Layered scales offering solid protection"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Horned Helm", "Helmet with horns, offers extra coverage"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Short Bow", "A lightweight bow, decent range but weak power"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Bronze Armor", "Bronze-plated armor, slightly heavier but sturdy"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Bone Helm", "Headgear made from bones, unique and defensive"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Iron Mace", "Solid mace that crushes armor slowly"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Chainmail", "Interlocked rings, great against slashing attacks"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Bronze Helm", "Bronze helmet, durable and reliable"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Bronze Spear", "A long spear, useful for keeping foes at distance"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Bone Armor", "Crafted from bones, surprisingly protective"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Warrior Helm", "Standard warrior helmet, decent protection"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "War Hammer", "Massive hammer, slow but devastating"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Quilted Armor", "Padded armor, reduces damage from minor blows"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Visor Helm", "Helmet with visor, protects face and head"),
		new Item(Item.Type.WEAPON, Item.Rarity.COMMON, "Gladius", "Standard short sword, agile and precise"),
		new Item(Item.Type.ARMOR, Item.Rarity.COMMON, "Studded Armor", "Reinforced with metal studs, resilient against attacks"),
		new Item(Item.Type.HELMET, Item.Rarity.COMMON, "Battle Helm", "Sturdy helm designed for prolonged battles")
	);

	private static Item GenerateCommonItem()
	{
		int index = rand.nextInt(COMMON_TEMPLATES.size());
		Item template = COMMON_TEMPLATES.get(index);
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		switch (template.GetType()) {
			case WEAPON:
				int attack = COMMON_MIN_ATTACK + rand.nextInt(COMMON_MAX_ATTACK - COMMON_MIN_ATTACK + 1);
				stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, attack));
				break;

			case ARMOR:
				int defense = COMMON_MIN_DEFENSE + rand.nextInt(COMMON_MAX_DEFENSE - COMMON_MIN_DEFENSE + 1);
				stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, defense));
				break;

			case HELMET:
				int health = COMMON_MIN_HEALTH + rand.nextInt(COMMON_MAX_HEALTH - COMMON_MIN_HEALTH + 1);
				stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, health));
				break;
		
			default:
				break;
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}



	private static final int RARE_MIN_ATTACK = 15;
	private static final int RARE_MAX_ATTACK = 30;
	private static final int RARE_MIN_DEFENSE = 15;
	private static final int RARE_MAX_DEFENSE = 30;
	private static final int RARE_MIN_HEALTH = 40;
	private static final int RARE_MAX_HEALTH = 80;
	private static final int RARE_MIN_SPEED = 3;
	private static final int RARE_MAX_SPEED = 8;
	private static final double RARE_MIN_EVASION = 0.05;
	private static final double RARE_MAX_EVASION = 0.15;
	private static final double RARE_MIN_ACCURACY = 0.05;
	private static final double RARE_MAX_ACCURACY = 0.15;
	private static final double RARE_MIN_CRIT_CHANCE = 0.05;
	private static final double RARE_MAX_CRIT_CHANCE = 0.15;
	private static final double RARE_MIN_CRIT_DAMAGE = 0.25;
	private static final double RARE_MAX_CRIT_DAMAGE = 0.50;
	private static final int RARE_MIN_LUCK = 3;
	private static final int RARE_MAX_LUCK = 8;

	private static final List<Item> RARE_TEMPLATES = List.of(
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Knightblade", "A finely forged sword, sharp and balanced"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Steelplate Armor", "Forged steel plates, durable and heavy"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Steel Visor", "Protects both head and eyes with a metal faceplate"),
		new Item(Item.Type.RELIC, Item.Rarity.RARE, "Boots of Swiftness", "Light boots that quicken movement", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Reaver Axe", "Brutal axe designed to tear through armor"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Hunter's Leather", "Reinforced leather favored by trackers"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Wolfhide Helm", "Reinforced with wolf pelts, symbolic of hunters"),
		new Item(Item.Type.RELIC, Item.Rarity.RARE, "Cloak of Shadows", "Shrouds the wearer, harder to strike", Map.of(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Venomfang Dagger", "Poison-etched blade, light and deadly"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Dragonscale Vest", "Resistant scales sewn together, rumored from wyrmlings"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Crested Helmet", "A steel helm with a proud crest, inspiring allies"),
		new Item(Item.Type.RELIC, Item.Rarity.RARE, "Pendant of Focus", "Enhances concentration, reducing missed blows", Map.of(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Longbow of Precision", "Crafted for accuracy and range"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Guardian Mail", "Thick mail woven to withstand repeated blows"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Greathelm", "Heavy full-face helmet, nearly unbreakable"),
		new Item(Item.Type.RELIC, Item.Rarity.RARE, "Ring of the Hawk", "Sharpens instincts for deadly strikes", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "War Mace", "Spiked mace, crushing defenses with each strike"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Bonecarver Armor", "Armor reinforced with carved bones"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Bone-Mask Helm", "Helm adorned with carved bone, intimidating foes"),
		new Item(Item.Type.RELIC, Item.Rarity.RARE, "Amulet of Fury", "Channels rage into devastating blows", Map.of(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Halberd", "Polearm that combines spear thrusts and axe swings"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Ironclad Breastplate", "Strong chest protection, popular among mercenaries"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Sentinel's Helm", "Used by watchmen, offers both safety and visibility"),
		new Item(Item.Type.RELIC, Item.Rarity.RARE, "Charm of Fortune", "Said to bend fate ever so slightly", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Crusader's Hammer", "Heavy hammer blessed with righteous strength"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Oathbound Armor", "Armor worn by knights sworn to ancient vows"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Warborn Helm", "Battle-tested helmet passed down through veterans"),
		new Item(Item.Type.WEAPON, Item.Rarity.RARE, "Stormfang Blade", "A finely balanced sword that hums with latent power, capable of striking swiftly and with precision"),
		new Item(Item.Type.ARMOR, Item.Rarity.RARE, "Aegis Plate", "Heavy steel plate engraved with protective runes, offering superior defense against physical attacks"),
		new Item(Item.Type.HELMET, Item.Rarity.RARE, "Lionheart Helm", "Sturdy helmet adorned with a lion motif, increases vitality and inspires courage")
	);

	private static Item GenerateRareRelic(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet()) {
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case SPEED:
					value = RARE_MIN_SPEED + rand.nextInt(RARE_MAX_SPEED - RARE_MIN_SPEED + 1);
					break;

				case EVASION:
					value = RARE_MIN_EVASION + rand.nextDouble() * (RARE_MAX_EVASION - RARE_MIN_EVASION);
					break;

				case ACCURACY:
					value = RARE_MIN_ACCURACY + rand.nextDouble() * (RARE_MAX_ACCURACY - RARE_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = RARE_MIN_CRIT_CHANCE + rand.nextDouble() * (RARE_MAX_CRIT_CHANCE - RARE_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = RARE_MIN_CRIT_DAMAGE + rand.nextDouble() * (RARE_MAX_CRIT_DAMAGE - RARE_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = RARE_MIN_LUCK + rand.nextInt(RARE_MAX_LUCK - RARE_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateRareItem()
	{
		int index = rand.nextInt(RARE_TEMPLATES.size());
		Item template = RARE_TEMPLATES.get(index);
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		switch (template.GetType()) {
			case WEAPON:
				int attack = RARE_MIN_ATTACK + rand.nextInt(RARE_MAX_ATTACK - RARE_MIN_ATTACK + 1);
				stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, attack));
				break;

			case ARMOR:
				int defense = RARE_MIN_DEFENSE + rand.nextInt(RARE_MAX_DEFENSE - RARE_MIN_DEFENSE + 1);
				stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, defense));
				break;

			case HELMET:
				int health = RARE_MIN_HEALTH + rand.nextInt(RARE_MAX_HEALTH - RARE_MIN_HEALTH + 1);
				stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, health));
				break;

			case RELIC:
				return GenerateRareRelic(template);
		
			default:
				break;
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}



	private static final int EPIC_MIN_ATTACK = 30;
	private static final int EPIC_MAX_ATTACK = 50;
	private static final int EPIC_MIN_DEFENSE = 30;
	private static final int EPIC_MAX_DEFENSE = 50;
	private static final int EPIC_MIN_HEALTH = 80;
	private static final int EPIC_MAX_HEALTH = 120;
	private static final int EPIC_MIN_SPEED = 8;
	private static final int EPIC_MAX_SPEED = 15;
	private static final double EPIC_MIN_EVASION = 0.15;
	private static final double EPIC_MAX_EVASION = 0.25;
	private static final double EPIC_MIN_ACCURACY = 0.15;
	private static final double EPIC_MAX_ACCURACY = 0.25;
	private static final double EPIC_MIN_CRIT_CHANCE = 0.15;
	private static final double EPIC_MAX_CRIT_CHANCE = 0.25;
	private static final double EPIC_MIN_CRIT_DAMAGE = 0.50;
	private static final double EPIC_MAX_CRIT_DAMAGE = 0.80;
	private static final int EPIC_MIN_LUCK = 8;
	private static final int EPIC_MAX_LUCK = 15;

	private static final List<Item> EPIC_TEMPLATES = List.of(
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Flamebrand Sword", "Sword wreathed in flames, strikes with extra force on critical hits", Map.of(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Guardian's Aegis", "Thick plate that not only protects but fortifies vitality", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Helm of the Lionheart", "Increases vitality and channels courage into stronger strikes", Map.of(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Bloodforged Sigil", "A sigil dipped in warrior's blood, it sharpens the wielder's blows and thirsts for critical strikes", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Shadowfang Dagger", "A dagger from the shadowed realms, easier to dodge with", Map.of(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Eaglehide Vest", "Light but resilient armor, wearer can maneuver quickly", Map.of(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Nightshadow Hood", "Protects while blending the wearer into darkness", Map.of(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Bulwark Stone", "A heavy stone infused with ancient runes, bolstering both durability and endurance", Map.of(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0), StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Stormcaller Axe", "An axe that hums with storm energy, swings faster than ordinary weapons", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Runebound Chainmail", "Etched runes enhance the wearer's ability to retaliate critically", Map.of(StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Stormguard Helm", "Helm that allows faster reactions in combat", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Shadowdancer's Charm", "A charm blessed by thieves guild masters, letting the bearer slip past blows with uncanny grace", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0), StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Dragonfang Spear", "A spear tipped with dragon fang, sometimes strikes with uncanny fortune", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Stormplate Armor", "Plates designed for swift movement in battle", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Dragoncrest Helm", "Adorned with a dragon motif, brings occasional fortunate outcomes", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Phoenix Feather Talisman", "A single fiery feather said to grant vitality and miraculous fortune to those who carry it", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Moonlit Longsword", "A silvered blade that never seems to miss its mark under moonlight", Map.of(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Dragonhide Armor", "Worn from dragon hide, sometimes bends fate in the wearer's favor", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Seer's Circlet", "Enhances perception, making strikes more precise", Map.of(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Eye of Precision", "A relic fashioned from a polished crystal eye, focusing every strike with lethal accuracy", Map.of(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0), StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Thunderstrike Mace", "Each blow has a chance to strike like a thunderbolt", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Celestial Breastplate", "Stars engraved into the armor improve focus and precision in combat", Map.of(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Warden's Faceplate", "A heavy helm that sometimes helps deliver deadly blows", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Frostfang Pendant", "A pendant made from a frozen wolf's fang, it imbues strikes with relentless ferocity and speed", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.EPIC, "Viper Bow", "A bow that lets the wielder shoot arrows with unmatched swiftness", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.EPIC, "Ironwarden Mail", "Reinforced mail that sometimes lets strikes land devastatingly", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.EPIC, "Phoenix Visor", "Helm that inspires the wearer to move with unmatched agility", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.EPIC, "Trickster's Coin", "A two-faced coin blessed by a mischievous god, it bends fate and helps the bearer slip away unscathed", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0), StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0)))
	);

	private static Item GenerateEpicItem()
	{
		int index = rand.nextInt(EPIC_TEMPLATES.size());
		Item template = EPIC_TEMPLATES.get(index);
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		switch (template.GetType()) {
			case WEAPON:
				return GenerateEpicWeapon(template);

			case ARMOR:
				return GenerateEpicArmor(template);

			case HELMET:
				return GenerateEpicHelmet(template);

			case RELIC:
				return GenerateEpicRelic(template);
		
			default:
				break;
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateEpicWeapon(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		int attack = EPIC_MIN_ATTACK + rand.nextInt(EPIC_MAX_ATTACK - EPIC_MIN_ATTACK + 1);
		stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, attack));

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case SPEED:
					value = RARE_MIN_SPEED + rand.nextInt(RARE_MAX_SPEED - RARE_MIN_SPEED + 1);
					break;

				case EVASION:
					value = RARE_MIN_EVASION + rand.nextDouble() * (RARE_MAX_EVASION - RARE_MIN_EVASION);
					break;

				case ACCURACY:
					value = RARE_MIN_ACCURACY + rand.nextDouble() * (RARE_MAX_ACCURACY - RARE_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = RARE_MIN_CRIT_CHANCE + rand.nextDouble() * (RARE_MAX_CRIT_CHANCE - RARE_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = RARE_MIN_CRIT_DAMAGE + rand.nextDouble() * (RARE_MAX_CRIT_DAMAGE - RARE_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = RARE_MIN_LUCK + rand.nextInt(RARE_MAX_LUCK - RARE_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateEpicArmor(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		int defense = EPIC_MIN_DEFENSE + rand.nextInt(EPIC_MAX_DEFENSE - EPIC_MIN_DEFENSE + 1);
		stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, defense));

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = RARE_MIN_HEALTH + rand.nextInt(RARE_MAX_HEALTH - RARE_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = RARE_MIN_ATTACK + rand.nextInt(RARE_MAX_ATTACK - RARE_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = RARE_MIN_DEFENSE + rand.nextInt(RARE_MAX_DEFENSE - RARE_MIN_DEFENSE + 1);
					break;
					
				case SPEED:
					value = RARE_MIN_SPEED + rand.nextInt(RARE_MAX_SPEED - RARE_MIN_SPEED + 1);
					break;

				case EVASION:
					value = RARE_MIN_EVASION + rand.nextDouble() * (RARE_MAX_EVASION - RARE_MIN_EVASION);
					break;

				case ACCURACY:
					value = RARE_MIN_ACCURACY + rand.nextDouble() * (RARE_MAX_ACCURACY - RARE_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = RARE_MIN_CRIT_CHANCE + rand.nextDouble() * (RARE_MAX_CRIT_CHANCE - RARE_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = RARE_MIN_CRIT_DAMAGE + rand.nextDouble() * (RARE_MAX_CRIT_DAMAGE - RARE_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = RARE_MIN_LUCK + rand.nextInt(RARE_MAX_LUCK - RARE_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateEpicHelmet(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		int health = EPIC_MIN_HEALTH + rand.nextInt(EPIC_MAX_HEALTH - EPIC_MIN_HEALTH + 1);
		stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, health));

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = RARE_MIN_HEALTH + rand.nextInt(RARE_MAX_HEALTH - RARE_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = RARE_MIN_ATTACK + rand.nextInt(RARE_MAX_ATTACK - RARE_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = RARE_MIN_DEFENSE + rand.nextInt(RARE_MAX_DEFENSE - RARE_MIN_DEFENSE + 1);
					break;

				case SPEED:
					value = RARE_MIN_SPEED + rand.nextInt(RARE_MAX_SPEED - RARE_MIN_SPEED + 1);
					break;

				case EVASION:
					value = RARE_MIN_EVASION + rand.nextDouble() * (RARE_MAX_EVASION - RARE_MIN_EVASION);
					break;

				case ACCURACY:
					value = RARE_MIN_ACCURACY + rand.nextDouble() * (RARE_MAX_ACCURACY - RARE_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = RARE_MIN_CRIT_CHANCE + rand.nextDouble() * (RARE_MAX_CRIT_CHANCE - RARE_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = RARE_MIN_CRIT_DAMAGE + rand.nextDouble() * (RARE_MAX_CRIT_DAMAGE - RARE_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = RARE_MIN_LUCK + rand.nextInt(RARE_MAX_LUCK - RARE_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateEpicRelic(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = RARE_MIN_HEALTH + rand.nextInt(RARE_MAX_HEALTH - RARE_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = RARE_MIN_ATTACK + rand.nextInt(RARE_MAX_ATTACK - RARE_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = RARE_MIN_DEFENSE + rand.nextInt(RARE_MAX_DEFENSE - RARE_MIN_DEFENSE + 1);
					break;

				case SPEED:
					value = EPIC_MIN_SPEED + rand.nextInt(EPIC_MAX_SPEED - EPIC_MIN_SPEED + 1);
					break;

				case EVASION:
					value = EPIC_MIN_EVASION + rand.nextDouble() * (EPIC_MAX_EVASION - EPIC_MIN_EVASION);
					break;

				case ACCURACY:
					value = EPIC_MIN_ACCURACY + rand.nextDouble() * (EPIC_MAX_ACCURACY - EPIC_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = EPIC_MIN_CRIT_CHANCE + rand.nextDouble() * (EPIC_MAX_CRIT_CHANCE - EPIC_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = EPIC_MIN_CRIT_DAMAGE + rand.nextDouble() * (EPIC_MAX_CRIT_DAMAGE - EPIC_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = EPIC_MIN_LUCK + rand.nextInt(EPIC_MAX_LUCK - EPIC_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}



	private static final int LEGENDARY_MIN_ATTACK = 70;
	private static final int LEGENDARY_MAX_ATTACK = 100;
	private static final int LEGENDARY_MIN_DEFENSE = 70;
	private static final int LEGENDARY_MAX_DEFENSE = 100;
	private static final int LEGENDARY_MIN_HEALTH = 200;
	private static final int LEGENDARY_MAX_HEALTH = 350;
	private static final int LEGENDARY_MIN_SPEED = 20;
	private static final int LEGENDARY_MAX_SPEED = 40;
	private static final double LEGENDARY_MIN_EVASION = 0.25;
	private static final double LEGENDARY_MAX_EVASION = 0.35;
	private static final double LEGENDARY_MIN_ACCURACY = 0.25;
	private static final double LEGENDARY_MAX_ACCURACY = 0.35;
	private static final double LEGENDARY_MIN_CRIT_CHANCE = 0.25;
	private static final double LEGENDARY_MAX_CRIT_CHANCE = 0.35;
	private static final double LEGENDARY_MIN_CRIT_DAMAGE = 1;
	private static final double LEGENDARY_MAX_CRIT_DAMAGE = 1.5;
	private static final int LEGENDARY_MIN_LUCK = 20;
	private static final int LEGENDARY_MAX_LUCK = 40;

	private static final List<Item> LEGENDARY_TEMPLATES = List.of(
		new Item(Item.Type.WEAPON, Item.Rarity.LEGENDARY, "Sword of Eternal Valor", "A blade that never dulls, always striking with precision and valor", Map.of(StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0), StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.LEGENDARY, "Aegis of the Silver Dawn", "Blessed armor that inspires courage and shields its bearer", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.LEGENDARY, "Crown of Chivalry", "A regal helm symbolizing flawless balance between strength and honor", Map.of(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0), StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.LEGENDARY, "Sigil of the Perfect Warrior", "The trinity of battle - strike fast, defend strong, fight forever", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0), StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.LEGENDARY, "Hammer of the Earthshaker", "A colossal hammer that channels the fury of the earth itself", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.LEGENDARY, "Bulwark of Eternity", "Armor so heavy that arrows bounce like raindrops, yet deceptively nimble", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.LEGENDARY, "Helm of the Colossus", "Worn by titans of old, said to grant the patience of stone", Map.of(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0), StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.LEGENDARY, "Stoneheart Talisman", "The guardian's soul is unshakable, striking only with purpose", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, 0), StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.LEGENDARY, "Titan's Wrath Maul", "A weapon that grows stronger the longer the battle rages", Map.of(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.LEGENDARY, "Warborn Juggernaut Plate", "Forged from a hundred battlefields, its scars are its strength", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.LEGENDARY, "Visage of Fury", "A helm that channels the juggernaut's fury into devastating blows", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.LEGENDARY, "Idol of Relentless Might", "The juggernaut's power never wanes — only grows with rage", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, 0), StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.LEGENDARY, "Dagger of the Silent Fang", "Faster than a whisper, deadlier than a scream", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0), StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.LEGENDARY, "Shadowveil Garb", "Cloth spun from shadow, flowing with every dodge", Map.of(StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0), StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.LEGENDARY, "Mask of the Silent Blade", "A mask that sharpens senses and stills the heart", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0), StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.LEGENDARY, "Nightshade Pendant", "The assassin fades away only to return with a killing blow", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0), StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0), StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.LEGENDARY, "Axe of Endless Carnage", "Every drop of blood makes the blade hungrier", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0), StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.LEGENDARY, "Rageborn Vestments", "Lighter than most armor - perfect for unleashing berserk fury", Map.of(StatisticTemplate.Type.SPEED, new Statistic(StatisticTemplate.Type.SPEED, 0), StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.LEGENDARY, "Skull of the Bloodhowl", "A feral helm that amplifies battle rage", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0), StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.LEGENDARY, "Totem of Savage Glory", "Glory through chaos, survival through slaughter", Map.of(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, 0), StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0), StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0))),
		new Item(Item.Type.WEAPON, Item.Rarity.LEGENDARY, "Rapier of Double or Nothing", "Each strike flips destiny's coin - sometimes jackpot, sometimes nothing", Map.of(StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0), StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0))),
		new Item(Item.Type.ARMOR, Item.Rarity.LEGENDARY, "Cloak of Fortuned Odds", "Probability bends around the wearer, making them hard to pin down", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0), StatisticTemplate.Type.EVASION, new Statistic(StatisticTemplate.Type.EVASION, 0))),
		new Item(Item.Type.HELMET, Item.Rarity.LEGENDARY, "Hat of the Stacked Deck", "A gambler's charm - luck follows wherever the brim points", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0), StatisticTemplate.Type.ACCURACY, new Statistic(StatisticTemplate.Type.ACCURACY, 0))),
		new Item(Item.Type.RELIC, Item.Rarity.LEGENDARY, "Coin of Infinite Chances", "The ultimate gambler's tool - bending fate until it breaks", Map.of(StatisticTemplate.Type.LUCK, new Statistic(StatisticTemplate.Type.LUCK, 0), StatisticTemplate.Type.CRIT_CHANCE, new Statistic(StatisticTemplate.Type.CRIT_CHANCE, 0), StatisticTemplate.Type.CRIT_DAMAGE, new Statistic(StatisticTemplate.Type.CRIT_DAMAGE, 0)))
	);

	private static Item GenerateLegendaryItem()
	{
		int index = rand.nextInt(LEGENDARY_TEMPLATES.size());
		Item template = LEGENDARY_TEMPLATES.get(index);
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		switch (template.GetType()) {
			case WEAPON:
				return GenerateLegendaryWeapon(template);

			case ARMOR:
				return GenerateLegendaryArmor(template);

			case HELMET:
				return GenerateLegendaryHelmet(template);

			case RELIC:
				return GenerateLegendaryRelic(template);
		
			default:
				break;
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateLegendaryWeapon(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		int attack = LEGENDARY_MIN_ATTACK + rand.nextInt(LEGENDARY_MAX_ATTACK - LEGENDARY_MIN_ATTACK + 1);
		stats.put(StatisticTemplate.Type.ATTACK, new Statistic(StatisticTemplate.Type.ATTACK, attack));

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = LEGENDARY_MIN_HEALTH + rand.nextInt(LEGENDARY_MAX_HEALTH - LEGENDARY_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = LEGENDARY_MIN_ATTACK + rand.nextInt(LEGENDARY_MAX_ATTACK - LEGENDARY_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = LEGENDARY_MIN_DEFENSE + rand.nextInt(LEGENDARY_MAX_DEFENSE - LEGENDARY_MIN_DEFENSE + 1);
					break;

				case SPEED:
					value = EPIC_MIN_SPEED + rand.nextInt(EPIC_MAX_SPEED - EPIC_MIN_SPEED + 1);
					break;

				case EVASION:
					value = EPIC_MIN_EVASION + rand.nextDouble() * (EPIC_MAX_EVASION - EPIC_MIN_EVASION);
					break;

				case ACCURACY:
					value = EPIC_MIN_ACCURACY + rand.nextDouble() * (EPIC_MAX_ACCURACY - EPIC_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = EPIC_MIN_CRIT_CHANCE + rand.nextDouble() * (EPIC_MAX_CRIT_CHANCE - EPIC_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = EPIC_MIN_CRIT_DAMAGE + rand.nextDouble() * (EPIC_MAX_CRIT_DAMAGE - EPIC_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = EPIC_MIN_LUCK + rand.nextInt(EPIC_MAX_LUCK - EPIC_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateLegendaryArmor(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		int defense = LEGENDARY_MIN_DEFENSE + rand.nextInt(LEGENDARY_MAX_DEFENSE - LEGENDARY_MIN_DEFENSE + 1);
		stats.put(StatisticTemplate.Type.DEFENSE, new Statistic(StatisticTemplate.Type.DEFENSE, defense));

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = LEGENDARY_MIN_HEALTH + rand.nextInt(LEGENDARY_MAX_HEALTH - LEGENDARY_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = LEGENDARY_MIN_ATTACK + rand.nextInt(LEGENDARY_MAX_ATTACK - LEGENDARY_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = LEGENDARY_MIN_DEFENSE + rand.nextInt(LEGENDARY_MAX_DEFENSE - LEGENDARY_MIN_DEFENSE + 1);
					break;

				case SPEED:
					value = EPIC_MIN_SPEED + rand.nextInt(EPIC_MAX_SPEED - EPIC_MIN_SPEED + 1);
					break;

				case EVASION:
					value = EPIC_MIN_EVASION + rand.nextDouble() * (EPIC_MAX_EVASION - EPIC_MIN_EVASION);
					break;

				case ACCURACY:
					value = EPIC_MIN_ACCURACY + rand.nextDouble() * (EPIC_MAX_ACCURACY - EPIC_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = EPIC_MIN_CRIT_CHANCE + rand.nextDouble() * (EPIC_MAX_CRIT_CHANCE - EPIC_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = EPIC_MIN_CRIT_DAMAGE + rand.nextDouble() * (EPIC_MAX_CRIT_DAMAGE - EPIC_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = EPIC_MIN_LUCK + rand.nextInt(EPIC_MAX_LUCK - EPIC_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateLegendaryHelmet(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		int health = LEGENDARY_MIN_HEALTH + rand.nextInt(LEGENDARY_MAX_HEALTH - LEGENDARY_MIN_HEALTH + 1);
		stats.put(StatisticTemplate.Type.HEALTH, new Statistic(StatisticTemplate.Type.HEALTH, health));

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = LEGENDARY_MIN_HEALTH + rand.nextInt(LEGENDARY_MAX_HEALTH - LEGENDARY_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = LEGENDARY_MIN_ATTACK + rand.nextInt(LEGENDARY_MAX_ATTACK - LEGENDARY_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = LEGENDARY_MIN_DEFENSE + rand.nextInt(LEGENDARY_MAX_DEFENSE - LEGENDARY_MIN_DEFENSE + 1);
					break;

				case SPEED:
					value = EPIC_MIN_SPEED + rand.nextInt(EPIC_MAX_SPEED - EPIC_MIN_SPEED + 1);
					break;

				case EVASION:
					value = EPIC_MIN_EVASION + rand.nextDouble() * (EPIC_MAX_EVASION - EPIC_MIN_EVASION);
					break;

				case ACCURACY:
					value = EPIC_MIN_ACCURACY + rand.nextDouble() * (EPIC_MAX_ACCURACY - EPIC_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = EPIC_MIN_CRIT_CHANCE + rand.nextDouble() * (EPIC_MAX_CRIT_CHANCE - EPIC_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = EPIC_MIN_CRIT_DAMAGE + rand.nextDouble() * (EPIC_MAX_CRIT_DAMAGE - EPIC_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = EPIC_MIN_LUCK + rand.nextInt(EPIC_MAX_LUCK - EPIC_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}

	private static Item GenerateLegendaryRelic(Item template)
	{
		Map<StatisticTemplate.Type, Statistic> stats = new EnumMap<>(StatisticTemplate.Type.class);

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : template.GetStatistics().entrySet())
		{
			StatisticTemplate.Type type = entry.getKey();
			double value = 0;

			switch (type) {
				case HEALTH:
					value = EPIC_MIN_HEALTH + rand.nextInt(EPIC_MAX_HEALTH - EPIC_MIN_HEALTH + 1);
					break;

				case ATTACK:
					value = EPIC_MIN_ATTACK + rand.nextInt(EPIC_MAX_ATTACK - EPIC_MIN_ATTACK + 1);
					break;

				case DEFENSE:
					value = EPIC_MIN_DEFENSE + rand.nextInt(EPIC_MAX_DEFENSE - EPIC_MIN_DEFENSE + 1);
					break;

				case SPEED:
					value = LEGENDARY_MIN_SPEED + rand.nextInt(LEGENDARY_MAX_SPEED - LEGENDARY_MIN_SPEED + 1);
					break;

				case EVASION:
					value = LEGENDARY_MIN_EVASION + rand.nextDouble() * (LEGENDARY_MAX_EVASION - LEGENDARY_MIN_EVASION);
					break;

				case ACCURACY:
					value = LEGENDARY_MIN_ACCURACY + rand.nextDouble() * (LEGENDARY_MAX_ACCURACY - LEGENDARY_MIN_ACCURACY);
					break;

				case CRIT_CHANCE:
					value = LEGENDARY_MIN_CRIT_CHANCE + rand.nextDouble() * (LEGENDARY_MAX_CRIT_CHANCE - LEGENDARY_MIN_CRIT_CHANCE);
					break;

				case CRIT_DAMAGE:
					value = LEGENDARY_MIN_CRIT_DAMAGE + rand.nextDouble() * (LEGENDARY_MAX_CRIT_DAMAGE - LEGENDARY_MIN_CRIT_DAMAGE);
					break;

				case LUCK:
					value = LEGENDARY_MIN_LUCK + rand.nextInt(LEGENDARY_MAX_LUCK - LEGENDARY_MIN_LUCK + 1);
					break;
			
				default:
					break;
			}

			stats.put(type, new Statistic(type, value));
		}

		return new Item(template.GetType(), template.GetRarity(), template.GetName(), template.GetDescription(), stats);
	}
}
