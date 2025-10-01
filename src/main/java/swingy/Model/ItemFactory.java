package swingy.Model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class ItemFactory
{
	private ItemFactory() {}

	private static final Random rand = new Random();

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

	public static Item GenerateCommonItem()
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

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : stats.entrySet()) {
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

	public static Item GenerateRareItem()
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
}
