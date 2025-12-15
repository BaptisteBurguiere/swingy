package swingy.View.Gui.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import swingy.Model.Hero;
import swingy.Model.Item;
import swingy.Model.Villain;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.ChestSpawn;
import swingy.View.Gui.Components.CombatCard;
import swingy.View.Gui.Components.CombatTextArea;
import swingy.View.Gui.Components.EquipItem;
import swingy.View.Gui.Components.PanelComponent;
import swingy.View.Gui.Components.TextArea;
import swingy.View.Gui.Components.Sprite;

public class CombatPanel extends BasePanel
{
	private Hero	_hero;
	private Villain	_villain;
	private CombatTextArea	_text_area;
	private EquipItem	_equip_item;
	private ChestSpawn	_chest_spawn;

	private boolean _display_equip_item = false;
	private boolean _display_chest_spawn = false;

	public CombatPanel(Hero hero, Villain villain, boolean is_boss)
	{
		this._hero = hero;
		this._villain = villain;

		BindKey(KeyEvent.VK_A, "attack");
		BindKey(KeyEvent.VK_D, "defend");
		BindKey(KeyEvent.VK_ENTER, "pass");
		if (!is_boss)
			BindKey(KeyEvent.VK_F, "flee");
		BindKey(KeyEvent.VK_E, "equip");
		BindKey(KeyEvent.VK_L, "leave");
		
		Graphics g = this._background.createGraphics();
		g.drawImage(SwingView.GetSprite("combat_battleground"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);

		int component_x = (int)((double)SwingView.GetWidth() * 0.2);
		int component_y = (int)((double)SwingView.GetHeight() * 0.4);
		int width = (int)((double)SwingView.GetHeight() * 0.6);
		int height = width;

		g.drawImage(SwingView.GetSprite(String.format("combat_H_%s", this._hero.GetClassStr())), component_x, component_y, width, height, null);

		component_x = (int)((double)SwingView.GetWidth() * 0.1);
		width = (int)((double)SwingView.GetWidth() * 0.2);
		height = 20;

		CombatCard card = new CombatCard(component_x, component_y, width, height, this._hero, true);
		AddComponent(card);

		component_x = (int)((double)SwingView.GetWidth() * 0.92) - (int)((double)SwingView.GetHeight() * 0.6);
		component_y = (int)((double)SwingView.GetHeight() * 0.25);
		width = (int)((double)SwingView.GetHeight() * 0.6);
		height = width;

		Sprite villain_sprite = new Sprite(SwingView.GetSprite(String.format("combat_V_%s", this._villain.GetName())), component_x, component_y, width, height);
		AddComponent(villain_sprite);

		component_x -= (int)((double)SwingView.GetWidth() * 0.1);
		width = (int)((double)SwingView.GetWidth() * 0.2);
		height = 20;

		card = new CombatCard(component_x, component_y, width, height, this._villain, false);
		AddComponent(card);

		component_x = 20;
		component_y = 20;
		width = (int)((double)SwingView.GetWidth() * 0.4);
		height = (int)((double)SwingView.GetHeight() * 0.3);

		this._text_area = new CombatTextArea(component_x, component_y, width, height);

		String to_display = String.format("%s starts a fight against %s lvl. %d!", this._hero.GetName(), this._villain.GetName(), this._villain.GetLevel());
		this._text_area.TextAreaAddChunk(to_display, CombatTextArea.FG_COLOR);

		component_x = (int)((float)SwingView.GetWidth() * 0.2);
		component_y = (int)((float)SwingView.GetHeight() * 0.2);
		width = (int)((float)SwingView.GetWidth() * 0.6);
		height = (int)((float)SwingView.GetHeight() * 0.6);
		this._equip_item = new EquipItem(component_x, component_y, width, height, hero);

		component_x = (int)((float)SwingView.GetWidth() * 0.25);
		height = TextArea.CalculateHeight(ChestSpawn.FONT_SIZE) * 2 + 2 * ChestSpawn.PADDING + 5;
		width = (int)((float)SwingView.GetWidth() * 0.5);
		component_y = (SwingView.GetHeight() - height) / 2;
		this._chest_spawn = new ChestSpawn(component_x, component_y, width, height);
	}

	public void ClearTextArea() { this._text_area.ClearTextArea(); }

	public void ClearNextTurns() { this._text_area.ClearNextTurns(); }

	public void TextAreaAddChunk(String text, Color color) { this._text_area.TextAreaAddChunk(text, color); }

	public void NextTurnsAddChunk(String text, Color color) { this._text_area.NextTurnsAddChunk(text, color); }

	public void SetEquipItem(Item item)
	{
		this._display_equip_item = true;
		this._equip_item.SetItem(item);
	}

	public void DisplayChestSpawn()
	{
		this._display_equip_item = false;
		this._display_chest_spawn = true;
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

		if (this._display_equip_item)
			_equip_item.Draw(g);

		if (this._display_chest_spawn)
			_chest_spawn.Draw(g);
	}
}
