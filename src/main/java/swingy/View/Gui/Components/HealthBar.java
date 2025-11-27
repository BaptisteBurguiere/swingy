package swingy.View.Gui.Components;

import java.awt.Color;

public class HealthBar extends ProgressBar
{
	private static final Color BAR_HIGH_COLOR = new Color(132, 191, 4);
	private static final Color BAR_MID_COLOR = new Color(242, 183, 5);
	private static final Color BAR_LOW_COLOR = new Color(187, 32, 32);
	private static final Color BG_COLOR = new Color(161, 164, 166);
	private static final Color BORDER_COLOR = Color.BLACK;

	public HealthBar(int origin_x, int origin_y, int width, int height, int max_value)
	{
		super(origin_x, origin_y, width, height, 3, max_value);
		this._border_color = BORDER_COLOR;
		this._bg_color = BG_COLOR;
	}

	@Override
	public void SetValue(int value)
	{
		this._value = Math.min(value, this._max_value);

		if (value < this._max_value / 5)
			this._bar_color = BAR_LOW_COLOR;
		else if (value < this._max_value / 2)
			this._bar_color = BAR_MID_COLOR;
		else
			this._bar_color = BAR_HIGH_COLOR;
	}
}
