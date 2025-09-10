package swingy;

import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Weapon;
import swingy.Model.Armor;
import swingy.Model.Helm;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

		Hero hero = new Hero("hero", Hero.Class.KNIGHT, 100, 10, 10);
		hero.EquipItem(new Weapon("sword", Item.Rarity.COMMON, 5));
		hero.EquipItem(new Armor("chainmail", Item.Rarity.COMMON, 5));
		hero.EquipItem(new Helm("lether helm", Item.Rarity.COMMON, 5));
    }
}