package swingy.View.Gui.Panels;

import java.awt.Graphics;
import java.awt.TextArea;

import swingy.Model.Hero;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.CombatCard;
import swingy.View.Gui.Components.PanelComponent;

public class CombatPanel extends BasePanel
{
	private Hero	_hero;
	private Villain	_villain;

	public CombatPanel(Hero hero, Villain villain)
	{
		this._hero = hero;
		this._villain = villain;
		
		Graphics g = this._background.createGraphics();
		g.drawImage(SwingView.GetSprite("combat_battleground"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);

		int component_x = (int)((double)SwingView.GetWidth() * 0.2);
		int component_y = (int)((double)SwingView.GetHeight() * 0.4);
		int width = (int)((double)SwingView.GetHeight() * 0.6);
		int height = width;

		g.drawImage(SwingView.GetSprite("combat_Assassin"), component_x, component_y, width, height, null);
		g.dispose();

		component_x = (int)((double)SwingView.GetWidth() * 0.1);
		width = (int)((double)SwingView.GetWidth() * 0.2);
		height = 20;

		CombatCard card = new CombatCard(component_x, component_y, width, height, this._hero, true);
		AddComponent(card);
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
