package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Statistic;
import swingy.Model.StatisticTemplate;
import swingy.View.Gui.SwingView;

public class EquipItem extends PanelComponent
{
    public static final int		BORDER_RADIUS = 20;
    private static final int    PADDING = 20;
	public static final Color	BG_COLOR = new Color(0, 0, 0, 200);
	public static final Color	FG_COLOR = new Color(220, 220, 220);
    public static final int     FONT_SIZE = 20;

    private TextArea    _item_text_area;
    private TextArea    _stats_text_area;

    public EquipItem(int origin_x, int origin_y, int width, int height, Hero hero)
    {
        super(origin_x, origin_y, width, height);

        int component_x = this._top_left_x + PADDING;
        int component_y = this._top_left_y + PADDING;
        int component_width = this.GetWidth() / 2 - PADDING * 2;
        int component_height = this.GetHeight() - PADDING * 2;

        this._item_text_area = new TextArea(component_x, component_y, component_width, component_height, FONT_SIZE);

        component_x += component_width + PADDING;

        this._stats_text_area = new TextArea(component_x, component_y, component_width, component_height, FONT_SIZE);

		Statistic stat = hero.GetStatistic(StatisticTemplate.Type.HEALTH);
        String text = String.format("%s: %.0f/%.0f\n", stat.GetName(), hero.GetCurrentHealth(), stat.GetValue());
        this._stats_text_area.AddChunk(text, FG_COLOR);

        stat = hero.GetStatistic(StatisticTemplate.Type.ATTACK);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.DEFENSE);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.SPEED);
		text = String.format("%s: %.0f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.EVASION);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.ACCURACY);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_CHANCE);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.CRIT_DAMAGE);
		text = String.format("%s: %.2f\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);

		stat = hero.GetStatistic(StatisticTemplate.Type.LUCK);
		text = String.format("%s: %.0f\n\n", stat.GetName(), stat.GetValue());
		this._stats_text_area.AddChunk(text, FG_COLOR);


		Item item = hero.GetItem(Item.Type.WEAPON);
		this._stats_text_area.AddChunk("Weapon: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, this._stats_text_area);
		else
			this._stats_text_area.AddChunk("\n", FG_COLOR);
		this._stats_text_area.AddChunk("\n", FG_COLOR);

		item = hero.GetItem(Item.Type.ARMOR);
		this._stats_text_area.AddChunk("Armor: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, this._stats_text_area);
		else
			this._stats_text_area.AddChunk("\n", FG_COLOR);
		this._stats_text_area.AddChunk("\n", FG_COLOR);

		item = hero.GetItem(Item.Type.HELMET);
		this._stats_text_area.AddChunk("Helmet: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, this._stats_text_area);
		else
			this._stats_text_area.AddChunk("\n", FG_COLOR);
		this._stats_text_area.AddChunk("\n", FG_COLOR);

		item = hero.GetItem(Item.Type.RELIC);
		this._stats_text_area.AddChunk("Relic: ", FG_COLOR);
		if (item != null)
			DisplayEquipmentItem(item, this._stats_text_area);
		else
			this._stats_text_area.AddChunk("\n", FG_COLOR);

		this._stats_text_area.CalculateFontSize();
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

    public void SetItem(Item item)
    {
        this._item_text_area.Clear();

        this._item_text_area.AddChunk("The ennemy dropped an item!\n\n", FG_COLOR);
        this._item_text_area.AddChunk(String.format("[%s] ", item.GetTypeStr()), FG_COLOR);
        DisplayEquipmentItem(item, this._item_text_area);
    }

    @Override
    public void Draw(Graphics g)
    {
		g.setColor(BG_COLOR);
		g.fillRoundRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), BORDER_RADIUS, BORDER_RADIUS);

        this._item_text_area.Draw(g);
        this._stats_text_area.Draw(g);

		int height = TextArea.CalculateHeight(FONT_SIZE);
		int width = TextArea.CalculateWidth("E: Equip", FONT_SIZE);
		int x = this._top_left_x + PADDING;
		int y = this._bottom_right_y - PADDING - height;

		TextArea text_area = new TextArea(x, y, width, height, FONT_SIZE);
		text_area.AddChunk("E: Equip", FG_COLOR);
		text_area.Draw(g);

		x += width + PADDING * 2;
		width = TextArea.CalculateWidth("L: Leave", FONT_SIZE);

		text_area = new TextArea(x, y, width, height, FONT_SIZE);
		text_area.AddChunk("L: Leave", FG_COLOR);
		text_area.Draw(g);
    }
}
