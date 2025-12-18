package swingy.View.Gui.Panels;

import java.awt.Graphics;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

import swingy.Model.SaveFile;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.Button;
import swingy.View.Gui.Components.PanelComponent;

public class StartPanel extends BasePanel
{
	public StartPanel()
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

		int nb_margin = SaveFile.NB_SAVES + 1;
		int width = (int)((double)SwingView.GetWidth() * 0.7);
		int height = SwingView.GetHeight() / nb_margin;
		int margin_top = height / nb_margin;
		int margin_left = (SwingView.GetWidth() - width) / 2;
		int font_size = (int)((double)height * 0.8);

		int cursor_y = (SwingView.GetHeight() - height * 2 + margin_top) / 2;

		this._components.add(new Button(margin_left, cursor_y, width, height, "Start", font_size, 0));
		
		cursor_y += height + margin_top;
		this._components.add(new Button(margin_left, cursor_y, width, height, "Pantheon", font_size, 1));
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
