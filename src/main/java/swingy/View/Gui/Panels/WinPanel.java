package swingy.View.Gui.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.TextArea;

public class WinPanel extends BasePanel
{
	public static final int		BORDER_RADIUS = 20;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 180);
	public static final Color	FG_COLOR = new Color(220, 220, 220);
	public static final int		PADDING = 20;
	
	public WinPanel()
	{
		BindKey(KeyEvent.VK_ENTER, "enter");

		Graphics g = this._background.createGraphics();
        g.drawImage(SwingView.GetSprite("save_background"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);
        g.dispose();

		int x = (int)((double)SwingView.GetWidth() * 0.2) + PADDING;
		int y = (int)((double)SwingView.GetHeight() * 0.2) + PADDING;
		int width = (int)((double)SwingView.GetWidth() * 0.6) - PADDING * 2;
		int height = (int)((double)SwingView.GetHeight() * 0.6) - PADDING * 2;

		TextArea text_area = new TextArea(x, y, width, height, 80);
		text_area.AddChunk("After beating the World Ender, you managed to escape the dungeon.\n", FG_COLOR);
		text_area.AddChunk("Your name will be written in history books", FG_COLOR);

		this._components.add(text_area);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this._background, 0, 0, null);
		g.setColor(BG_COLOR);

		int x = (int)((double)SwingView.GetWidth() * 0.2);
		int y = (int)((double)SwingView.GetHeight() * 0.2);
		int width = (int)((double)SwingView.GetWidth() * 0.6);
		int height = (int)((double)SwingView.GetHeight() * 0.6);
		g.fillRoundRect(x, y, width, height, BORDER_RADIUS, BORDER_RADIUS);

		for (PanelComponent component : this._components)	
		{
			component.Draw(g);
		}
	}
}
