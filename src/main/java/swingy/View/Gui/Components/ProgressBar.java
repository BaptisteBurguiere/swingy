package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

public class ProgressBar extends PanelComponent
{
	protected int	_border_size;
	protected Color	_border_color;
	protected Color	_bar_color;
	protected Color	_bg_color;

	protected int	_max_value;
	protected int	_value;

	public ProgressBar(int origin_x, int origin_y, int width, int height, int border_size, int max_value)
	{
		super(origin_x, origin_y, width, height);
		this._max_value = max_value;
		this._border_size = border_size;
	}

	public void SetValue(int value) { this._value = Math.min(value, this._max_value); }

	public void SetMaxValue(int max_value)
	{
		this._max_value = max_value;
		this._value = Math.min(this._value, this._max_value);
	}

	@Override
	public void Draw(Graphics g)
	{
		g.setColor(this._border_color);
		g.fillRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight());

		int bg_width = this.GetWidth() - this._border_size * 2;
		int bg_height = this.GetHeight() - this._border_size * 2;

		g.setColor(this._bg_color);
		g.fillRect(this._top_left_x + this._border_size, this._top_left_y + this._border_size, bg_width, bg_height);

		int bar_width = (int)(((double)this._value / (double)this._max_value) * (double)bg_width);

		g.setColor(this._bar_color);
		g.fillRect(this._top_left_x + this._border_size, this._top_left_y + this._border_size, bar_width, bg_height);
	}
}
