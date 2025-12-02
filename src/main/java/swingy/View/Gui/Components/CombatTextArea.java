package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

public class CombatTextArea extends PanelComponent
{
	private static final int	BORDER_RADIUS = 20;
	private static final Color	BG_COLOR = new Color(0, 0, 0, 125);
	public static final Color	FG_COLOR = new Color(220, 220, 220);
    private static final int    FONT_SIZE = 32;
    private static final int    PADDING = 10;


    private TextArea    _text_area;
    private TextArea    _next_turns;

    public CombatTextArea(int origin_x, int origin_y, int width, int height)
    {
        super(origin_x, origin_y, width, height);

        int component_x = this._top_left_x + PADDING;
        int component_y = this._bottom_right_y - PADDING - TextArea.CalculateHeight(FONT_SIZE);
        int component_width = this.GetWidth() - PADDING * 2;
        int component_height = TextArea.CalculateHeight(FONT_SIZE);

        this._next_turns = new TextArea(component_x, component_y, component_width, component_height, FONT_SIZE);

        component_y = this._top_left_y + PADDING;
        component_height = this.GetHeight() - component_height;

        this._text_area = new TextArea(component_x, component_y, component_width, component_height, FONT_SIZE);
    }

    public void ClearTextArea() { this._text_area.Clear(); }

    public void ClearNextTurns() { this._next_turns.Clear(); }

    public void TextAreaAddChunk(String text, Color color) { this._text_area.AddChunk(text, color); }

    public void NextTurnsAddChunk(String text, Color color) { this._next_turns.AddChunk(text, color); }

    @Override
    public void Draw(Graphics g)
    {
        g.setColor(BG_COLOR);
        g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);

        this._text_area.Draw(g);
        this._next_turns.CalculateFontSize();
        this._next_turns.Draw(g);
    }
}
