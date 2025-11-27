package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import swingy.Model.Hero;
import swingy.Model.StatisticTemplate;
import swingy.View.Gui.SwingView;

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
		
		int padding = 10;
		
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
