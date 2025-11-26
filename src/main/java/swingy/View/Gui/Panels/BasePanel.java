package swingy.View.Gui.Panels;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import javax.swing.JPanel;

import swingy.View.Gui.Components.PanelComponent;

public class BasePanel extends JPanel
{
	protected List<PanelComponent>	_components;
	protected IntConsumer			_click_listener = null;

	public BasePanel()
	{
		this._components = new ArrayList<>();

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
	}

	public void AddComponent(PanelComponent component) { this._components.add(component); }

	public void RemoveComponent(PanelComponent component) { this._components.remove(component); }

	public void setClickListener(IntConsumer listener) { this._click_listener = listener; }
}
