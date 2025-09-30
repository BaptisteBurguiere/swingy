package swingy;

import swingy.Controller.Game;

public class Main {
    public static void main(String[] args) {
		Game game = Game.GetInstance();
		game.Start();
    }
}