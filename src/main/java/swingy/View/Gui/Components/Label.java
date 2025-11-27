package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

import swingy.View.Gui.SwingView;

public class Label extends PanelComponent
{
	private String	_content;
	private int		_font_size;
	private Color	_color;

	public Label(String content, int font_size, Color color, int origin_x, int origin_y)
	{
		super(origin_x, origin_y);
		this._content = content;
		this._font_size = font_size;
		this._color = color;
	}

	public void SetContent(String content) { this._content = content; }

	public void SetFontSize(int font_size) { this._font_size = font_size; }

	@Override
	public void Draw(Graphics g)
	{
		g.setColor(this._color);
		g.setFont(SwingView.LoadCustomFont(this._font_size));
		g.drawString(_content, this._top_left_x, this._top_left_y + this._font_size);
	}
}
