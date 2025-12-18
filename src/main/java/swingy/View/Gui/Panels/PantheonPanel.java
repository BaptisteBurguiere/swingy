package swingy.View.Gui.Panels;

import java.awt.Graphics;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseEvent;

import swingy.Model.GameStats;
import swingy.Model.Hero;
import swingy.Model.PantheonFile;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.Button;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.PantheonSlot;
import swingy.View.Gui.Components.TextArea;

public class PantheonPanel extends BasePanel
{
	private static final int PAGE_SIZE = 7;
	
	private PantheonFile _pantheon;

	private PantheonSlot[] 	_slots;
	private Button			_previous;
	private Button			_next;

	private int	_current_page = 0;

	public PantheonPanel(PantheonFile pantheon)
	{
		super();
		// Listen for clicks, press, release, enter, exit
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				for (PantheonSlot component : _slots)
				{
					if (component.IsIn(e.getX(), e.getY()) && !component.IsHidden())
					{
						int click_res = component.Click(e.getX(), e.getY());
						
						if (click_res != -1 && _click_listener != null)
							_click_listener.accept(click_res);

						return;
					}
				}

				if (_previous.IsIn(e.getX(), e.getY()) && !_previous.IsHidden())
				{
					int click_res = _previous.Click(e.getX(), e.getY());
					
					if (click_res != -1 && _click_listener != null)
						_click_listener.accept(click_res);

					return;
				}

				if (_next.IsIn(e.getX(), e.getY()) && !_next.IsHidden())
				{
					int click_res = _next.Click(e.getX(), e.getY());
					
					if (click_res != -1 && _click_listener != null)
						_click_listener.accept(click_res);

					return;
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
				for (PanelComponent component : _slots)
				{
					if (component.IsIn(e.getX(), e.getY()) && !component.IsHover() && !component.IsHidden())
					{
						change = true;
						component.HoverIn();
						if (component.IsInteractive())
							setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
					else if (!component.IsIn(e.getX(), e.getY()) && component.IsHover() && !component.IsHidden())
					{
						change = true;
						component.HoverOut();
						if (component.IsInteractive())
							setCursor(Cursor.getDefaultCursor());
					}
				}

				if (_previous.IsIn(e.getX(), e.getY()) && !_previous.IsHover() && !_previous.IsHidden())
				{
					change = true;
					_previous.HoverIn();
					if (_previous.IsInteractive())
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				else if (!_previous.IsIn(e.getX(), e.getY()) && _previous.IsHover() && !_previous.IsHidden())
				{
					change = true;
					_previous.HoverOut();
					if (_previous.IsInteractive())
						setCursor(Cursor.getDefaultCursor());
				}

				if (_next.IsIn(e.getX(), e.getY()) && !_next.IsHover() && !_next.IsHidden())
				{
					change = true;
					_next.HoverIn();
					if (_next.IsInteractive())
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				else if (!_next.IsIn(e.getX(), e.getY()) && _next.IsHover() && !_next.IsHidden())
				{
					change = true;
					_next.HoverOut();
					if (_next.IsInteractive())
						setCursor(Cursor.getDefaultCursor());
				}

				if (change)
					repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {}
		});

		this._slots = new PantheonSlot[PAGE_SIZE];
		this._pantheon = pantheon;

		Graphics g = this._background.createGraphics();
		g.drawImage(SwingView.GetSprite("save_background"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);
		g.dispose();



		int width = (int)((double)SwingView.GetWidth() * 0.7);
		int height = SwingView.GetHeight() / (PAGE_SIZE + 2);
		int margin_top = height / (PAGE_SIZE + 2);
		int x = (SwingView.GetWidth() - width) / 2;
		int y = margin_top;
		int font_size = (int)((double)height * 0.8);

		for (int i = 0; i < PAGE_SIZE; i++)
		{
			this._slots[i] = new PantheonSlot();
			this._slots[i].Set(x, y, width, height);
			this._slots[i].SetFontSize(font_size);
			y += height + margin_top;
		}

		width = (int)((double)TextArea.CalculateWidth(">", font_size) / 0.9);
		height = Math.max(width, (int)((double)TextArea.CalculateHeight(font_size) / 0.9));
		width = height;

		x = (SwingView.GetWidth() - width * 3) / 2;
		y = SwingView.GetHeight() - margin_top - height;

		this._previous = new Button(x, y, width, height, "<", font_size, -2);

		x += width * 2;
		this._next = new Button(x, y, width, height, ">", font_size, -1);

		this._previous.Hide();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		for (int i = 0; i < PAGE_SIZE; i++)
		{
			if (i < this._pantheon.heroes.size())
			{
				Hero hero = this._pantheon.heroes.get(i);
				GameStats stats = this._pantheon.stats.get(i);
				String date = "";
				if (stats.win_date != null)
					date = stats.win_date.format(dtf);

				String to_display = String.format("lvl. %d - %s - %s", hero.GetLevel(), hero.GetName(), date);
				this._slots[i].SetContent(to_display);
				this._slots[i].SetSlot(i);
				this._slots[i].Show();
			}
			else
				this._slots[i].Hide();
		}

		if (this._pantheon.heroes.size() <= PAGE_SIZE)
			this._next.Hide();
	}

	public void NextPage()
	{
		this._current_page++;

		this._previous.Show();

		if (this._current_page * PAGE_SIZE + PAGE_SIZE <= this._pantheon.heroes.size())
			this._next.Hide();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		for (int i = 0; i < PAGE_SIZE; i++)
		{
			int current = this._current_page * PAGE_SIZE + i;
			
			if (current < this._pantheon.heroes.size())
			{
				Hero hero = this._pantheon.heroes.get(current);
				GameStats stats = this._pantheon.stats.get(current);
				String date = "";
				if (stats.win_date != null)
					date = stats.win_date.format(dtf);

				String to_display = String.format("lvl. %d - %s - %s", hero.GetLevel(), hero.GetName(), date);
				this._slots[i].SetContent(to_display);
				this._slots[i].SetSlot(current);
				this._slots[i].Show();
			}
			else
				this._slots[i].Hide();
		}
	}

	public void PreviousPage()
	{
		this._current_page--;

		this._next.Show();

		if (this._current_page == 0)
			this._previous.Hide();

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		for (int i = 0; i < PAGE_SIZE; i++)
		{
			int current = this._current_page * PAGE_SIZE + i;

			if (current < this._pantheon.heroes.size())
			{
				Hero hero = this._pantheon.heroes.get(current);
				GameStats stats = this._pantheon.stats.get(current);
				String date = "";
				if (stats.win_date != null)
					date = stats.win_date.format(dtf);

				String to_display = String.format("lvl. %d - %s - %s", hero.GetLevel(), hero.GetName(), date);
				this._slots[i].SetContent(to_display);
				this._slots[i].SetSlot(current);
				this._slots[i].Show();
			}
			else
				this._slots[i].Hide();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this._background, 0, 0, null);

		for (PanelComponent component : this._slots)	
		{
			component.Draw(g);
		}

		this._previous.Draw(g);
		this._next.Draw(g);
	}
}