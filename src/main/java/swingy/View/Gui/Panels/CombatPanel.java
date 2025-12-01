package swingy.View.Gui.Panels;

import java.awt.Graphics;
import java.awt.TextArea;

import swingy.Model.Hero;
import swingy.Model.StatisticTemplate;
import swingy.Model.Villain;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.CombatCard;
import swingy.View.Gui.Components.CombatTextArea;
import swingy.View.Gui.Components.PanelComponent;

public class CombatPanel extends BasePanel
{
	private Hero	_hero;
	private Villain	_villain;
	private CombatTextArea	_text_area;

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

		g.drawImage(SwingView.GetSprite(String.format("combat_%s", this._hero.GetClassStr())), component_x, component_y, width, height, null);

		component_x = (int)((double)SwingView.GetWidth() * 0.1);
		width = (int)((double)SwingView.GetWidth() * 0.2);
		height = 20;

		CombatCard card = new CombatCard(component_x, component_y, width, height, this._hero, true);
		AddComponent(card);

		component_x = (int)((double)SwingView.GetWidth() * 0.92) - (int)((double)SwingView.GetHeight() * 0.6);
		component_y = (int)((double)SwingView.GetHeight() * 0.25);
		width = (int)((double)SwingView.GetHeight() * 0.6);
		height = width;

		g.drawImage(SwingView.GetSprite("combat_Brute"), component_x, component_y, width, height, null);
		g.dispose();

		component_x -= (int)((double)SwingView.GetWidth() * 0.1);
		width = (int)((double)SwingView.GetWidth() * 0.2);
		height = 20;

		card = new CombatCard(component_x, component_y, width, height, this._villain, false);
		AddComponent(card);

		component_x = 20;
		component_y = 20;
		width = (int)((double)SwingView.GetWidth() * 0.3);
		height = (int)((double)SwingView.GetHeight() * 0.3);

		this._text_area = new CombatTextArea(component_x, component_y, width, height);

		String to_display = String.format("%s starts a fight against %s lvl. %d!", this._hero.GetName(), this._villain.GetName(), this._villain.GetLevel());
		this._text_area.TextAreaAddChunk(to_display, CombatTextArea.FG_COLOR);
		
		this._text_area.NextTurnsAddChunk("Moi > Brute > Moi", CombatTextArea.FG_COLOR);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(this._background, 0, 0, null);

		this._text_area.Draw(g);

		for (PanelComponent component : this._components)	
		{
			component.Draw(g);
		}
	}
}
