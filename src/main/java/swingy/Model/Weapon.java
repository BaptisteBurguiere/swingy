package swingy.Model;

public class Weapon extends Item {
	public Weapon(String name, Rarity rarity, int attack) {super(Item.Type.WEAPON, rarity, name, attack);}
}
