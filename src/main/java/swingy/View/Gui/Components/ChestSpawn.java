package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

public class ChestSpawn extends PanelComponent
{
    public static final int		BORDER_RADIUS = 20;
    public static final int    PADDING = 20;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 200);
	public static final Color	FG_COLOR = new Color(220, 220, 220);
    public static final int     FONT_SIZE = 20;

	private TextArea _text_area;
	
	public ChestSpawn(int origin_x, int origin_y, int width, int height)
	{
		super(origin_x, origin_y, width, height);

		int component_x = this._top_left_x + PADDING;
		int component_y = this._top_left_y + PADDING;
		int component_width = width - 2 * PADDING;
		int component_height = height - 2 * PADDING;

		this._text_area = new TextArea(component_x, component_y, component_width, component_height, FONT_SIZE);
		this._text_area.AddChunk("As all ennemies in the room are defeated,\nyou can hear a mechanism revealing a chest in the center of the room.", FG_COLOR);
	}

	@Override
	public void Draw(Graphics g)
	{
		g.setColor(BG_COLOR);
		g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);

		this._text_area.Draw(g);
	}
}
