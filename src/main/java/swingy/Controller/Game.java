package swingy.Controller;

import swingy.Model.Hero;
import swingy.Model.HeroFactory;
import swingy.View.View;
import swingy.View.Console.ConsoleView;

public class Game
{
	private Hero	_hero;
	private View	_view;

	public Game()
	{
		this._view = new ConsoleView();
		this._hero = HeroFactory.NewHero(Hero.Class.KNIGHT, "Hero");
	}

	public void Start()
	{
		this._view.DisplayHero(this._hero);
	}
}
