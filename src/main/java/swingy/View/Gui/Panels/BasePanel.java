package swingy.View.Gui.Panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import swingy.View.Gui.Components.PanelComponent;

public class BasePanel extends JPanel
{
	protected List<PanelComponent> _components;

	public BasePanel()
	{
		this._components = new ArrayList<>();
	}

	public void AddComponent(PanelComponent component) { this._components.add(component); }

	public void RemoveComponent(PanelComponent component) { this._components.remove(component); }
}
