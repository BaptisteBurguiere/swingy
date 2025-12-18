package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

public class PantheonSlot extends PanelComponent
{
	public static final int		BORDER_RADIUS = 10;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 125);
	public static final Color	FG_COLOR = new Color(180, 180, 180, 255);
	public static final Color	HOVER_BG_COLOR = new Color(200, 200, 200, 220);
	public static final Color	HOVER_FG_COLOR = Color.BLACK;

	private int		_slot;
	private String	_content = "";
	private int		_font_size;

	public PantheonSlot() { this._interactive = true; }

	public void SetFontSize(int font_size) { this._font_size = font_size; }
	public void SetContent(String content) { this._content = content; }
	public void SetSlot(int slot) { this._slot = slot; }

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
		if (this._hidden)
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
		g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);

		int height = TextArea.CalculateHeight(this._font_size);
		int width = this.GetWidth() - 20;
		int y = this._top_left_y + (this.GetHeight() - height) / 2;
		int x = this._top_left_x + 10;

		TextArea text_area = new TextArea(x, y, width, height, this._font_size);
		text_area.AddChunk(this._content, fg_color);

		int font_size = text_area.CalculateFontSize();
		height = TextArea.CalculateHeight(font_size);
		y = this._top_left_y + (this.GetHeight() - height) / 2;

		// text_area.SetHeight(height);
		// text_area.SetOriginY(y);

		text_area.Draw(g);
	}
}
