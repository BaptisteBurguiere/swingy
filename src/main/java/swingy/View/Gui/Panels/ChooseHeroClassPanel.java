package swingy.View.Gui.Panels;

import java.awt.Graphics;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import swingy.Model.Hero;
import swingy.Model.SaveFile;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.HeroClass;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.SaveSlot;

public class ChooseHeroClassPanel extends BasePanel
{
	public ChooseHeroClassPanel()
	{
		super();

		// Listen for clicks, press, release, enter, exit
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				for (PanelComponent component : _components)
				{
					if (component.IsIn(e.getX(), e.getY()))
					{
						int click_res = component.Click(e.getX(), e.getY());
						
						if (click_res != -1 && _click_listener != null)
							_click_listener.accept(click_res);

						return;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});

		// Listen for mouse movement and drag
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e)
			{
				boolean change = false;
				for (PanelComponent component : _components)
				{
					if (component.IsIn(e.getX(), e.getY()) && !component.IsHover())
					{
						change = true;
						component.HoverIn();
						if (component.IsInteractive())
							setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
					else if (!component.IsIn(e.getX(), e.getY()) && component.IsHover())
					{
						change = true;
						component.HoverOut();
						if (component.IsInteractive())
							setCursor(Cursor.getDefaultCursor());
					}
				}

				if (change)
					repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {}
		});

		Graphics g = this._background.createGraphics();
        g.drawImage(SwingView.GetSprite("save_background"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);
        g.dispose();

		EnumSet<Hero.Class> all_classes = EnumSet.allOf(Hero.Class.class);
		List<Hero.Class> all_classes_lst = new ArrayList<>(all_classes);
		List<String> all_classes_str = new ArrayList<String>();

		for (Hero.Class Class : all_classes)
			all_classes_str.add(Hero.GetClassStr(Class));

		int nb_margin = all_classes_lst.size() + 1;
		int width = (int)((double)SwingView.GetWidth() * 0.7);
		int height = SwingView.GetHeight() / nb_margin;
		int margin_top = height / 9;
		int margin_left = (SwingView.GetWidth() - width) / 2;
		int font_size = (int)((double)height * 0.8);

		int cursor_y = margin_top;

		if (height * all_classes_lst.size() + nb_margin * margin_top < SwingView.GetHeight())
			cursor_y += (SwingView.GetHeight() - (height * all_classes_lst.size() + nb_margin * margin_top)) / 2;

		for (int i = 0; i < all_classes.size(); i++)
		{
			String to_display = all_classes_str.get(i);
			AddComponent(new HeroClass(i, to_display, margin_left, cursor_y, width, height, font_size));
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
