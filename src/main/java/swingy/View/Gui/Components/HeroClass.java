package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

public class HeroClass extends PanelComponent
{
	public static final int		BORDER_RADIUS = 10;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 125);
	public static final Color	FG_COLOR = new Color(180, 180, 180, 255);
	public static final Color	HOVER_BG_COLOR = new Color(200, 200, 200, 220);
	public static final Color	HOVER_FG_COLOR = Color.BLACK;

	private int		_class;
	private String	_content;
	private int		_font_size;

	public HeroClass(int Class, String content, int origin_x, int origin_y, int width, int height, int font_size)
	{
		super(origin_x, origin_y, width, height);
		this._interactive = true;

		this._class = Class;
		this._content = content;
		this._font_size = font_size;
	}

	@Override
	public void HoverIn()
	{
		this._hover = true;
	}

	@Override
	public void HoverOut()
	{
		this._hover = false;
	}

	@Override
	public int Click(int x, int y)
	{
		return this._class;
	}

	@Override
	public void Draw(Graphics g)
	{
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
		g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);

		int height = TextArea.CalculateHeight(this._font_size);
		int width = TextArea.CalculateWidth(this._content, this._font_size);
		int y = this._top_left_y + (this.GetHeight() - height) / 2;
		int x = this._top_left_x + 10;

		TextArea text_area = new TextArea(x, y, width, height, this._font_size);
		text_area.AddChunk(this._content, fg_color);

		text_area.Draw(g);
	}
}
