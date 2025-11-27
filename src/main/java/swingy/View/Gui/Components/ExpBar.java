package swingy.View.Gui.Components;

import java.awt.Color;

public class ExpBar extends ProgressBar
{
	private static final Color BAR_COLOR = new Color(42, 234, 245);
	private static final Color BG_COLOR = new Color(161, 164, 166);
	private static final Color BORDER_COLOR = Color.BLACK;

	public ExpBar(int origin_x, int origin_y, int width, int height, int max_value)
	{
		super(origin_x, origin_y, width, height, 3, max_value);
		this._border_color = BORDER_COLOR;
		this._bg_color = BG_COLOR;
		this._bar_color = BAR_COLOR;
	}
}
