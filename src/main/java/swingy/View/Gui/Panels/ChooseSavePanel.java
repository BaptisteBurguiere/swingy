package swingy.View.Gui.Panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import swingy.Model.SaveFile;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.SaveSlot;
import swingy.Model.Hero;

public class ChooseSavePanel extends BasePanel
{
	private SaveFile _save_file;
	private int _cursor_x;
	private int _cursor_y;

	public ChooseSavePanel(SaveFile save_file)
	{
		this._save_file = save_file;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// int font_size = 18;
		int padding = 10;

		g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
		// g.setColor(Color.WHITE);
		// g.setFont(new Font("Monospaced", Font.BOLD, font_size));
		this._cursor_x = 5;
		this._cursor_y = SwingView.PADDING_TOP;

		for (int i = 0; i < SaveFile.NB_SAVES; i++)
		{
			Hero hero = this._save_file.heroes[i];
			String to_display = "";

			if (hero == null)
				to_display = String.format("%d: Empty", i + 1);
			else
				to_display = String.format("%d: %s lvl. %d - %s", i + 1, hero.GetClassStr(), hero.GetLevel(), hero.GetName());

			AddComponent(new SaveSlot(to_display, this._cursor_x, this._cursor_y));
			// g.drawString(to_display, this._cursor_x, this._cursor_y);
			this._cursor_y += SaveSlot.HEIGHT + padding;
		}

		for (PanelComponent component : this._components)	
		{
			component.Draw(g);
		}
	}
}
