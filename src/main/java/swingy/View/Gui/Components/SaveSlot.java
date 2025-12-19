package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

import swingy.View.Gui.SwingView;

public class SaveSlot extends PanelComponent
{
	public static final int		BORDER_RADIUS = 10;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 125);
	public static final Color	FG_COLOR = new Color(180, 180, 180, 255);
	public static final Color	HOVER_BG_COLOR = new Color(200, 200, 200, 220);
	public static final Color	HOVER_FG_COLOR = Color.BLACK;

	private int		_slot;
	private String	_content;
	private int		_font_size;
	private boolean	_has_win;
	private boolean _endless;

	public SaveSlot(int slot, String content, boolean has_win, boolean endless, int origin_x, int origin_y, int width, int height, int font_size)
	{
		super(origin_x, origin_y, width, height);
		this._interactive = true;

		this._slot = slot;
		this._content = content;
		this._font_size = font_size;
		this._has_win = has_win;
		this._endless = endless;
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
		return this._slot;
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

		int x = this._top_left_x;
		int y;
		int height;
		int width;

		if (this._endless)
		{
			g.drawImage(SwingView.GetSprite("endless"), this._top_left_x, this._top_left_y, this.GetHeight(), this.GetHeight(), null);
			x += this.GetHeight();
		}
		else if (this._has_win)
		{
			g.drawImage(SwingView.GetSprite("crown"), this._top_left_x, this._top_left_y, this.GetHeight(), this.GetHeight(), null);
			x += this.GetHeight();
		}

		height = TextArea.CalculateHeight(this._font_size);
		width = TextArea.CalculateWidth(this._content, this._font_size);
		y = this._top_left_y + (this.GetHeight() - height) / 2;
		x += 10;

		TextArea text_area = new TextArea(x, y, width, height, this._font_size);
		text_area.AddChunk(this._content, fg_color);

		text_area.Draw(g);
	}
}
