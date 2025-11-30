package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;

import swingy.Model.Entity;
import swingy.Model.Hero;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;

public class CombatCard extends PanelComponent
{
    private static final Color BG_COLOR = new Color(0, 0, 0, 125);
	private static final Color	FG_COLOR = new Color(220, 220, 220);

    private Entity  _entity;
    private boolean _is_hero;

    public CombatCard(int origin_x, int origin_y, int width, int height, Entity entity, boolean is_hero)
    {
        super(origin_x, origin_y, width, height);
        this._interactive = false;
        this._entity = entity;
        this._is_hero = is_hero;
    }

    @Override
    public void Draw(Graphics g)
    {
        g.setColor(BG_COLOR);
        g.fillRect(this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight());
        
        int font_size = 16;
        g.setColor(FG_COLOR);

        int max_health = (int)this._entity.GetStatistic(StatisticTemplate.Type.HEALTH).GetValue();
        int health;
        
        if (this._is_hero)
        {
            Hero hero = (Hero)this._entity;
            health = (int)hero.GetCurrentHealth();
        }
        else
        {
            Villain villain = (Villain)this._entity;
            health = (int)villain.GetCurrentHealth();
        }

        int origin_x = this._top_left_x + TextArea.CalculateWidth(String.format(" %d/%d ", max_health, max_health), font_size);
        int origin_y = this._top_left_y;
        int width = this._bottom_right_x - origin_x;
        int height = this.GetHeight();

        HealthBar health_bar = new HealthBar(origin_x, origin_y, width, height, max_health);
        health_bar.SetValue(health);
        health_bar.Draw(g);

        width = TextArea.CalculateWidth(String.format(" %d/%d ", health, max_health), font_size);
        height = TextArea.CalculateHeight(font_size);
        origin_x -= width;
        origin_y += (this.GetHeight() - height) / 2;

        TextArea text_area = new TextArea(origin_x, origin_y, width, height, font_size);
        text_area.AddChunk(String.format(" %d/%d ", health, max_health), FG_COLOR);

        text_area.Draw(g);
    }
}
