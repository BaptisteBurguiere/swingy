package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import swingy.View.Gui.SwingView;


public class SaveSlot extends PanelComponent
{
	public static final int		FONT_SIZE = 32;
	public static final int		WIDTH = 500;
	public static final int		HEIGHT = 50;
	public static final int		BORDER_RADIUS = 10;
	public static final Color	BG_COLOR = Color.RED;
	public static final Color	FG_COLOR = Color.BLACK;
	public static final Color	HOVER_BG_COLOR = Color.CYAN;
	public static final Color	HOVER_FG_COLOR = Color.BLACK;

	private int		_slot;
	private String	_content;

	public SaveSlot(int slot, String content, int origin_x, int origin_y)
	{
		super(origin_x, origin_y, WIDTH, HEIGHT);
		this._interactive = true;

		this._slot = slot;
		this._content = content;
	}

	@Override
	public void HoverIn()
	{
		this._changed = true;
		this._hover = true;
	}

	@Override
	public void HoverOut()
	{
		this._changed = true;
		this._hover = false;
	}

	@Override
	public int Click(int x, int y)
	{
		System.out.println(String.format("Slot %d clicked", this._slot));
		return this._slot;
	}

	@Override
	public void Draw(Graphics g)
	{
		if (!this._changed)
			return;

		Color bg_color;
		Color fg_color;

		if (IsHover())
		{
			bg_color = HOVER_BG_COLOR;
			fg_color = HOVER_FG_COLOR;
		}
		else
		{
			bg_color = BG_COLOR;
			fg_color = FG_COLOR;
		}

		g.setColor(bg_color);
		g.fillRoundRect(this._top_left_x, this._top_left_y, WIDTH, HEIGHT, BORDER_RADIUS, BORDER_RADIUS);

		g.setColor(fg_color);
		g.setFont(SwingView.LoadCustomFont(FONT_SIZE));
		g.drawString(this._content, this._top_left_x + 10, this._bottom_right_y - (HEIGHT - FONT_SIZE) / 2);

		this._changed = false;
	}
}
