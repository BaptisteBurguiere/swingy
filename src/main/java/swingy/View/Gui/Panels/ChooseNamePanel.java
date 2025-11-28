package swingy.View.Gui.Panels;

import java.awt.Color;
import java.awt.Graphics;

import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.TextArea;

public class ChooseNamePanel extends BasePanel
{
	private static final Color FG_COLOR = new Color(220, 220, 220);
	private static final Color	BG_COLOR = new Color(0, 0, 0, 175);
	
	private String	_name;

	public ChooseNamePanel()
	{
		super();

		this._name = "Test";

		Graphics g = this._background.createGraphics();
        g.drawImage(SwingView.GetSprite("save_background"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);
        g.dispose();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this._background, 0, 0, null);
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, SwingView.GetWidth(), SwingView.GetHeight());
		
		int font_size = 92;
		String text = "Choose a name";

		int width = TextArea.CalculateWidth(text, font_size);
		int margin_x = (SwingView.GetWidth() - width) / 2;
		int margin_top = (int)((double)SwingView.GetHeight() * 0.3);

		int height = TextArea.CalculateHeight(font_size);
		TextArea text_area = new TextArea(margin_x, margin_top, width, height, font_size);
		text_area.AddChunk(text, FG_COLOR);

		text_area.Draw(g);

		font_size = 80;

		width = (int)((double)SwingView.GetWidth() * 0.7);
		margin_x = (SwingView.GetWidth() - width) / 2;
		margin_top += height + 20;
		height = TextArea.CalculateHeight(font_size) + 20;

		g.setColor(FG_COLOR);
		g.fillRect(margin_x, margin_top, width, height);

		width -= 12;
		margin_x += 6;
		margin_top += 6;
		height -= 12;

		g.setColor(Color.BLACK);
		g.fillRect(margin_x, margin_top, width, height);

		margin_x += 4;
		margin_top += 4;
		height = TextArea.CalculateHeight(font_size);

		text_area = new TextArea(margin_x, margin_top, width, height, font_size);
		text_area.AddChunk(this._name, FG_COLOR);

		text_area.Draw(g);
	}
}
