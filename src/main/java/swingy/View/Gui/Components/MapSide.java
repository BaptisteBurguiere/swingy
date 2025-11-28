package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;

public class MapSide extends PanelComponent
{
	public static final int		BORDER_RADIUS = 20;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 125);
	public static final Color	FG_COLOR = new Color(220, 220, 220);

	private Hero					_hero;
	private List<PanelComponent>	_components;

	public MapSide(Hero hero, int origin_x, int origin_y, int width, int height)
	{
		super(origin_x, origin_y, width, height);

		this._hero = hero;
		this._interactive = false;
		
		this._components = new ArrayList<>();
		
		int padding = 20;
		
		int font_size = 32;
		String text = String.format("%d", this._hero.GetLevel());
		int component_height = TextArea.CalculateHeight(font_size);
		int component_width = TextArea.CalculateWidth(text, font_size);
		int component_origin_x = origin_x + width - padding - component_width;
		int component_origin_y = origin_y + padding;

		TextArea text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);
		this._components.add(text_area);

		font_size = 20;
		text = "lvl. ";
		component_width = TextArea.CalculateWidth(text, font_size);
		component_origin_x -= component_width;
		component_origin_y += component_height - TextArea.CalculateHeight(font_size); 
		component_height = TextArea.CalculateHeight(font_size);

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);
		this._components.add(text_area);


		int health = (int)this._hero.GetCurrentHealth();
		int max_health = (int)this._hero.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue();
		
		component_origin_y += component_height + 5;
		component_width = (int)(((double)width - (double)padding * 2) * 0.75);
		component_height = 16;
		component_origin_x = this._bottom_right_x - padding - component_width;

		HealthBar health_bar = new HealthBar(component_origin_x, component_origin_y, component_width, component_height, max_health);
		health_bar.SetValue(health);
		this._components.add(health_bar);

		text = String.format("%d/%d", health, max_health);
		font_size = 16;
		component_width = TextArea.CalculateWidth(text, font_size);
		component_height = TextArea.CalculateHeight(font_size);
		component_origin_x -= component_width;
		component_origin_y += 2;

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);
		this._components.add(text_area);


		int exp_needed = this._hero.GetExperienceNeeded();
		int exp = this._hero.GetExperience();

		component_origin_y += component_height + 5;
		component_width = this._bottom_right_x - padding - text_area.GetOriginX();
		component_height = 12;

		ExpBar exp_bar = new ExpBar(component_origin_x, component_origin_y, component_width,  component_height, exp_needed);
		exp_bar.SetValue(exp);
		this._components.add(exp_bar);

		text = hero.GetName();
		font_size = 32;
		component_width = TextArea.CalculateWidth(text, font_size);
		component_height = TextArea.CalculateHeight(font_size);
		component_origin_y = health_bar.GetOriginY() - component_height - 5;

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);
		this._components.add(text_area);

		font_size = 20;
		component_origin_y = exp_bar.GetOriginY() + exp_bar.GetHeight() + padding * 2;
		component_origin_x = this._top_left_x + padding;
		component_width = this._bottom_right_x - this._top_left_x - padding * 2;
		component_height = TextArea.CalculateHeight(font_size) * 9;
		
		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);

		Statistic stat = hero.GetStatistic(StatisticTemplate.Type.ATTACK);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.DEFENSE);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.SPEED);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.EVASION);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.ACCURACY);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.LUCK);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		text_area.AddChunk(text, FG_COLOR);

		this._components.add(text_area);

		component_origin_y = text_area.GetOriginY() + text_area.GetHeight() + padding * 2;
		component_origin_x = this._top_left_x + padding;
		component_width = this._bottom_right_x - this._top_left_x - padding * 2;
		component_height = this._bottom_right_y - component_origin_y - padding - TextArea.CalculateHeight(font_size);

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		
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

		this._components.add(text_area);

		text = "W, A, S, D: Move";
		component_origin_y = this._bottom_right_y - padding - TextArea.CalculateHeight(font_size);
		component_origin_x = this._top_left_x + padding;
		component_width = TextArea.CalculateWidth(text, font_size);
		component_height = TextArea.CalculateHeight(font_size);

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);

		this._components.add(text_area);

		text = "H: Display help";
		component_origin_y = this._bottom_right_y - padding - TextArea.CalculateHeight(font_size);
		component_origin_x += component_width + padding * 2;
		component_width = TextArea.CalculateWidth(text, font_size);
		component_height = TextArea.CalculateHeight(font_size);

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);

		this._components.add(text_area);

		text = "Esc: Quit";
		component_origin_y = this._bottom_right_y - padding - TextArea.CalculateHeight(font_size);
		component_origin_x += component_width + padding * 2;
		component_width = TextArea.CalculateWidth(text, font_size);
		component_height = TextArea.CalculateHeight(font_size);

		text_area = new TextArea(component_origin_x, component_origin_y, component_width, component_height, font_size);
		text_area.AddChunk(text, FG_COLOR);

		this._components.add(text_area);
	}

	private void DisplayEquipmentItem(Item item, TextArea text_area)
	{
		Color color = FG_COLOR;
		switch (item.GetRarity())
		{
			case COMMON:
				color = Color.WHITE;
				break;
			case RARE:
				color = Color.BLUE;
				break;
			case EPIC:
				color = Color.MAGENTA;
				break;
			case LEGENDARY:
				color = Color.YELLOW;
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
	public void Draw(Graphics g)
	{
		g.setColor(BG_COLOR);
		g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);

		for (PanelComponent component : this._components)
		{
			component.Draw(g);	
		}
	}
}
