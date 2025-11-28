package swingy.View.Gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import swingy.View.Gui.Panels.BasePanel;
import swingy.View.Gui.Panels.ChooseHeroClassPanel;
import swingy.View.Gui.Panels.ChooseSavePanel;
import swingy.View.Gui.Panels.MapPanel;
import swingy.View.View.Action;
import swingy.Model.GameMap;
import swingy.Model.Hero;
import swingy.Model.SaveFile;

public class SwingView
{
	public static final String	FONT_PATH = "./assets/alagard.ttf";
	public static final String	SPRITES_PATH = "./assets/sprites";
	public static final int		TAB_SIZE = 4;

	private static final Map<String, BufferedImage> _sprites = new HashMap<>();

	static
	{
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		_width = (int)((double)screen_size.width / 1.2);
		_height = (int)((double)screen_size.height / 1.2);

		LoadSprites();
	}


	private static int	_width;
	private static int	_height;
	private JFrame		_frame;
	private BasePanel	_panel;
	private CountDownLatch _latch;

	private	boolean _is_main_view_displayed = false;

	public SwingView()
	{
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		this._frame = new JFrame();
		this._frame.setTitle("Swingy");

		this._frame.pack();
		this._frame.setVisible(true);

		// Get decoration size
		Insets insets = this._frame.getInsets();

		// Compute final window size so drawable area = WIDTH x HEIGHT
		int frameWidth  = _width  + insets.left + insets.right;
		int frameHeight = _height + insets.top  + insets.bottom;

		this._frame.setSize(frameWidth, frameHeight);

		this._frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this._frame.setLocationRelativeTo(null);
		this._frame.setVisible(false);
		this._frame.setResizable(false);
	}

	public static int GetWidth() { return _width; }

	public static int GetHeight() { return _height; }

	private static void LoadSprites()
	{
		Path assets_path = Paths.get(SPRITES_PATH);

		try
		{
			Files.walk(assets_path).filter(Files::isRegularFile).forEach(path -> {
				LoadSprite(path);
			});
		}
		catch (Exception e)
		{
			System.err.println("Error loading assets.");
			System.err.println(e.getMessage());
		}
	}

	public static Font LoadCustomFont(float size)
	{
		try
		{
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			return font.deriveFont(size);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			return new Font("SansSerif", Font.PLAIN, (int) size);
		}
	}

	private static void LoadSprite(Path path)
	{
		try
		{
			String filename = path.getFileName().toString();
			int dot = filename.lastIndexOf('.');
			String key = (dot == -1) ? filename : filename.substring(0, dot);

			_sprites.put(key, ImageIO.read(path.toFile()));
		}
		catch (Exception e)
		{
			System.err.println("Error loading asset.");
			System.err.println(e.getMessage());
		}
	}

	public static BufferedImage GetSprite(String name)
	{
		return _sprites.get(name);
	}

	public int DisplayChooseSave(SaveFile save_file)
	{
		this._panel = new ChooseSavePanel(save_file);

		this._latch = new CountDownLatch(1);

    	final int[] selected_slot = { -1 };

		this._panel.SetClickListener(slot ->
		{
			selected_slot[0] = slot;
			this._latch.countDown();   // RELEASE wait
		});

		SwingUtilities.invokeLater(() ->
		{
			this._frame.setContentPane(this._panel);
			this._frame.revalidate();
			this._frame.setVisible(true);
			this._frame.repaint();
		});

		try {
			this._latch.await(); // BLOCK until clicked
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return selected_slot[0];

		// this._frame.add(this._panel);
		// this._panel.repaint();
		// this._frame.setVisible(true);
	}

	public Action DisplayMainView(GameMap map, Hero hero)
	{
		if (!this._is_main_view_displayed)
		{
			this._panel = new MapPanel(map, hero);
		}

		this._latch = new CountDownLatch(1);
		final int[] key_code = { -1 };

		this._panel.SetKeyListener(key -> {
			key_code[0] = key;
			this._latch.countDown();
		});

		SwingUtilities.invokeLater(() -> {
			this._frame.setContentPane(this._panel);
			this._frame.revalidate();
			this._frame.setVisible(true);
			this._frame.repaint();
			this._panel.requestFocusInWindow();       // IMPORTANT
			this._is_main_view_displayed = true;
		});

		while (true) {
			try {
				this._latch.await();

				switch (key_code[0]) {
					case KeyEvent.VK_W: return Action.MOVE_UP;
					case KeyEvent.VK_S: return Action.MOVE_DOWN;
					case KeyEvent.VK_A: return Action.MOVE_LEFT;
					case KeyEvent.VK_D: return Action.MOVE_RIGHT;

					case KeyEvent.VK_ESCAPE:
						System.exit(0);
				}

				// Reset latch for next key
				this._latch = new CountDownLatch(1);

				this._panel.SetKeyListener(key -> {
					key_code[0] = key;
					this._latch.countDown();
				});

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public Hero.Class DisplayCreateHeroClass()
	{
		EnumSet<Hero.Class> all_classes = EnumSet.allOf(Hero.Class.class);
		List<Hero.Class> all_classes_lst = new ArrayList<>(all_classes);

		this._panel = new ChooseHeroClassPanel();

		this._latch = new CountDownLatch(1);

    	final int[] selected_class = { -1 };

		this._panel.SetClickListener(Class ->
		{
			selected_class[0] = Class;
			this._latch.countDown();   // RELEASE wait
		});

		SwingUtilities.invokeLater(() ->
		{
			this._frame.setContentPane(this._panel);
			this._frame.revalidate();
			this._frame.setVisible(true);
			this._frame.repaint();
		});

		try {
			this._latch.await(); // BLOCK until clicked
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return all_classes_lst.get(selected_class[0]);
	}
}
