package swingy.View.Gui.Panels;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseEvent;

import java.util.Map;

import swingy.Model.GameStats;
import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.Button;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.TextArea;

public class PantheonHeroPanel extends BasePanel
{
	public static final int		BORDER_RADIUS = 20;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 180);
	public static final Color	FG_COLOR = new Color(220, 220, 220);
	public static final int		PADDING = 20;
	public static final int		FONT_SIZE = 20;
	
	public PantheonHeroPanel(Hero hero, GameStats stats)
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

		int x = PADDING * 2;
		int height = (int)((double)TextArea.CalculateHeight(FONT_SIZE * 2) / 0.8);
		int width = TextArea.CalculateWidth("Back", FONT_SIZE * 2) + height - TextArea.CalculateHeight(FONT_SIZE * 2);
		int y = SwingView.GetHeight() - PADDING * 2 - height;
		Button btn = new Button(x, y, width, height, "Back", FONT_SIZE * 2, 0);

		this._components.add(btn);

		x += width + PADDING;
		width = TextArea.CalculateWidth("Start endless", FONT_SIZE * 2) + height - TextArea.CalculateHeight(FONT_SIZE * 2);
		btn = new Button(x, y, width, height, "Start endless", FONT_SIZE * 2, 1);

		this._components.add(btn);

		x = PADDING * 2;
		y = PADDING * 2;
		width = (SwingView.GetWidth() - PADDING * 4) / 2 - PADDING / 2;
		height = SwingView.GetHeight() - PADDING * 4 - height;

		TextArea text_area = new TextArea(x, y, width, height, (int)(FONT_SIZE * 1.5));
		text_area.AddChunk("Game Statistics:\n\n", FG_COLOR);
		text_area.AddChunk(String.format("\tDamage dealt: %d\n", stats.damage_dealt), FG_COLOR);
		text_area.AddChunk(String.format("\tDamage received: %d\n", stats.damage_received), FG_COLOR);
		text_area.AddChunk(String.format("\tCritical hits: %d\n", stats.crit), FG_COLOR);
		text_area.AddChunk(String.format("\tAttacks missed: %d\n", stats.missed), FG_COLOR);
		text_area.AddChunk(String.format("\tAttacks dodged: %d\n", stats.dodged), FG_COLOR);
		text_area.AddChunk(String.format("\tAttacks parried: %d\n", stats.parried), FG_COLOR);
		text_area.AddChunk(String.format("\tFailed flees: %d\n", stats.flee_failed), FG_COLOR);
		text_area.AddChunk(String.format("\tSuccessful flees: %d\n", stats.flee_success), FG_COLOR);
		text_area.AddChunk(String.format("\tEnnemies slained: %d\n", stats.villain_slained), FG_COLOR);
		text_area.AddChunk(String.format("\tRooms exited: %d\n", stats.rooms_exited), FG_COLOR);
		text_area.AddChunk(String.format("\tBosses slained: %d\n", stats.boss_slained), FG_COLOR);
		text_area.AddChunk(String.format("\tItems equiped: %d\n", stats.items_equiped), FG_COLOR);
		text_area.AddChunk(String.format("\tChest opened: %d\n\n", stats.chest_opened), FG_COLOR);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		text_area.AddChunk(String.format("\tWin date: %s\n", stats.win_date.format(dtf)), FG_COLOR);
		text_area.CalculateFontSize();

		this._components.add(text_area);

		text_area = new TextArea(x + width + PADDING, y, width, height, FONT_SIZE);
		text_area.AddChunk(String.format("lvl. %d - %s - %s\n\n", hero.GetLevel(), hero.GetName(), hero.GetClassStr()), FG_COLOR);

		Statistic stat = hero.GetStatistic(StatisticTemplate.Type.HEALTH);
		text_area.AddChunk(String.format("%s: %.0f\n", stat.GetName(), stat.GetValue()), FG_COLOR);
		
		stat = hero.GetStatistic(StatisticTemplate.Type.ATTACK);
		text_area.AddChunk(String.format("%s: %.0f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.DEFENSE);
		text_area.AddChunk(String.format("%s: %.0f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.SPEED);
		text_area.AddChunk(String.format("%s: %.0f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.EVASION);
		text_area.AddChunk(String.format("%s: %.2f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.ACCURACY);
		text_area.AddChunk(String.format("%s: %.2f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE);
		text_area.AddChunk(String.format("%s: %.2f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE);
		text_area.AddChunk(String.format("%s: %.2f\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.LUCK);
		text_area.AddChunk(String.format("%s: %.0f\n\n", stat.GetName(), stat.GetValue()), FG_COLOR);

		Item item = hero.GetItem(Item.Type.WEAPON);
		text_area.AddChunk("Weapon: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, text_area);
		else
			text_area.AddChunk("\n", FG_COLOR);
		text_area.AddChunk("\n", FG_COLOR);

		item = hero.GetItem(Item.Type.ARMOR);
		text_area.AddChunk("Armor: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, text_area);
		else
			text_area.AddChunk("\n", FG_COLOR);
		text_area.AddChunk("\n", FG_COLOR);

		item = hero.GetItem(Item.Type.HELMET);
		text_area.AddChunk("Helmet: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, text_area);
		else
			text_area.AddChunk("\n", FG_COLOR);
		text_area.AddChunk("\n", FG_COLOR);

		item = hero.GetItem(Item.Type.RELIC);
		text_area.AddChunk("Relic: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, text_area);
		else
			text_area.AddChunk("\n", FG_COLOR);
		text_area.AddChunk("\n", FG_COLOR);

		text_area.CalculateFontSize();
		this._components.add(text_area);
	}

	private void DisplayEquipmentItem(Item item, TextArea text_area)
	{
		Color color = FG_COLOR;
		switch (item.GetRarity())
		{
			case COMMON:
				color = SwingView.COMMON_COLOR;
				break;
			case RARE:
				color = SwingView.RARE_COLOR;
				break;
			case EPIC:
				color = SwingView.EPIC_COLOR;
				break;
			case LEGENDARY:
				color = SwingView.LEGENDARY_COLOR;
				break;
		}
		
		text_area.AddChunk(String.format("%s (", item.GetName()), FG_COLOR);
		text_area.AddChunk(item.GetRarityStr(), color);
		text_area.AddChunk(")\n", FG_COLOR);

		String text = "";

		for (Map.Entry<StatisticTemplate.Type, Statistic> entry : item.GetStatistics().entrySet())
		{
			Statistic stat = entry.getValue();
			switch (stat.GetType())
			{
				case HEALTH:
					text = String.format("  +%.0f %s\n", stat.GetValue(), stat.GetName());
					break;

				case ATTACK:
					text = String.format("  +%.0f %s\n", stat.GetValue(), stat.GetName());
					break;

				case DEFENSE:
					text = String.format("  +%.0f %s\n", stat.GetValue(), stat.GetName());
					break;

				case SPEED:
					text = String.format("  +%.0f %s\n", stat.GetValue(), stat.GetName());
					break;

				case EVASION:
					text = String.format("  +%.2f %s\n", stat.GetValue(), stat.GetName());
					break;

				case ACCURACY:
					text = String.format("  +%.2f %s\n", stat.GetValue(), stat.GetName());
					break;

				case CRIT_CHANCE:
					text = String.format("  +%.2f %s\n", stat.GetValue(), stat.GetName());
					break;

				case CRIT_DAMAGE:
					text = String.format("  +%.2f %s\n", stat.GetValue(), stat.GetName());
					break;

				case LUCK:
					text = String.format("  +%.0f %s\n", stat.GetValue(), stat.GetName());
					break;
			
				default:
					break;
			}
			
			text_area.AddChunk(text, FG_COLOR);
		}
		
		text = String.format("Description: %s\n", item.GetDescription());
		text_area.AddChunk(text, FG_COLOR);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this._background, 0, 0, null);
		g.setColor(BG_COLOR);
		g.fillRoundRect(PADDING, PADDING, SwingView.GetWidth() - 2 * PADDING, SwingView.GetHeight() - 2* PADDING, BORDER_RADIUS, BORDER_RADIUS);

		for (PanelComponent component : this._components)	
		{
			component.Draw(g);
		}
	}
}
