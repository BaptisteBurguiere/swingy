package swingy.View.Gui.Panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import swingy.Model.SaveFile;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.SaveSlot;
import swingy.Model.Hero;

public class ChooseSavePanel extends BasePanel
{
	private static final int MAX_NAME_LENGTH = 15;
	private BufferedImage	_background;

	public ChooseSavePanel(SaveFile save_file)
	{
		super();

        this._background = new BufferedImage(SwingView.WIDTH, SwingView.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = this._background.getGraphics();
        g.drawImage(SwingView.GetSprite("save_background"), 0, 0, SwingView.WIDTH, SwingView.HEIGHT, null);
        g.dispose();

		int nb_margin = SaveFile.NB_SAVES + 1;
		int width = (int)((double)SwingView.WIDTH * 0.7);
		int height = SwingView.HEIGHT / nb_margin;
		int margin_top = height / 9;
		int margin_left = (SwingView.WIDTH - width) / 2;
		int font_size = (int)((double)height * 0.8);

		int cursor_y = margin_top;

		for (int i = 0; i < SaveFile.NB_SAVES; i++)
		{
			Hero hero = save_file.heroes[i];
			String to_display = "";

			if (hero == null)
				to_display = String.format("New Game");
			else
			{
				String name = hero.GetName();
				if (name.length() > MAX_NAME_LENGTH)
					name = String.format("%s...", name.substring(0, MAX_NAME_LENGTH));

				to_display = String.format("[ ] lvl. %d - %s", hero.GetLevel(), name);
			}

			AddComponent(new SaveSlot(i + 1, to_display, margin_left, cursor_y, width, height, font_size));
			cursor_y += height + margin_top;
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this._background, 0, 0, null);

		for (PanelComponent component : this._components)	
		{
			component.Draw(g);
		}
	}
}
