package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

import swingy.Model.Hero;
import swingy.View.Gui.SwingView;

public class MapSide extends PanelComponent
{
	public static final int		BORDER_RADIUS = 20;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 125);
	public static final Color	FG_COLOR = new Color(180, 180, 180, 255);

	private Hero	_hero;

	public MapSide(Hero hero, int origin_x, int origin_y, int width, int height)
	{
		super(origin_x, origin_y, width, height);

		this._hero = hero;
		this._interactive = false;
	}

	@Override
	public void Draw(Graphics g)
	{
		g.setColor(BG_COLOR);
		g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);
	}
}
