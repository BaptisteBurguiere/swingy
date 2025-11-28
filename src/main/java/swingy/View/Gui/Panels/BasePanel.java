package swingy.View.Gui.Panels;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.PanelComponent;

public class BasePanel extends JPanel
{
	protected BufferedImage			_background;
	protected List<PanelComponent>	_components;
	protected IntConsumer			_click_listener = null;
	protected IntConsumer			_key_listener = null;

	public BasePanel()
	{
		this._background = new BufferedImage(SwingView.GetWidth(), SwingView.GetHeight(), BufferedImage.TYPE_INT_ARGB);
		this._components = new ArrayList<>();
	}

	public void AddComponent(PanelComponent component) { this._components.add(component); }

	public void RemoveComponent(PanelComponent component) { this._components.remove(component); }

	public void SetClickListener(IntConsumer listener) { this._click_listener = listener; }

	public void SetKeyListener(IntConsumer listener) { this._key_listener = listener; }

	protected void FireKey(int keyCode)
	{
        if (_key_listener != null)
		{
			_key_listener.accept(keyCode);
		}
    }

	protected void BindKey(int keyCode, String name)
	{
		// WHEN_IN_FOCUSED_WINDOW: works without explicit focus
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), name);

		getActionMap().put(name, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FireKey(keyCode);
			}
		});
	}
}
