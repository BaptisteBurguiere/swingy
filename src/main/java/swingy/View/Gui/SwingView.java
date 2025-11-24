package swingy.View.Gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import swingy.View.Gui.Panels.ChooseSavePanel;
import swingy.Model.SaveFile;

public class SwingView
{
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;

	private JFrame _frame;
	private JPanel _panel;

	public SwingView()
	{
		this._frame = new JFrame();
		this._frame.setTitle("Swingy");
		this._frame.setSize(WIDTH, HEIGHT);
		this._frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this._frame.setLocationRelativeTo(null);
		this._frame.setVisible(false);
		this._frame.setResizable(false);
	}

	public void DisplayChooseSave(SaveFile save_file)
	{
		this._panel = new ChooseSavePanel(save_file);

		this._frame.add(this._panel);
		this._panel.repaint();
		this._frame.setVisible(true);
	}
}
