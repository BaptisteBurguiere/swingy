package swingy;

import swingy.Controller.Game;
import swingy.Model.Hero;

public class Main {
    public static void main(String[] args) {
		Game game = Game.GetInstance(Hero.Class.GAMBLER, "Phen'X");
		game.Start();
    }
}