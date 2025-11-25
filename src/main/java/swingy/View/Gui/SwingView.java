package swingy.View.Gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import swingy.View.Gui.Panels.ChooseSavePanel;
import swingy.Model.SaveFile;

public class SwingView
{
	public static final String	ASSETS_PATH = "./assets";
	public static final int 	WIDTH = 700;
	public static final int 	HEIGHT = 700;
	public static final int 	PADDING_TOP = 20;

	private static final Map<String, BufferedImage> _sprites = new HashMap<>();

	static
	{
		LoadSprites();
	}



	private JFrame						_frame;

	public SwingView()
	{
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		this._frame = new JFrame();
		this._frame.setTitle("Swingy");
		this._frame.setSize(WIDTH, HEIGHT);
		this._frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this._frame.setLocationRelativeTo(null);
		this._frame.setVisible(false);
		this._frame.setResizable(false);
	}

	private static void LoadSprites()
	{
		Path assets_path = Paths.get(ASSETS_PATH);

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
		ChooseSavePanel panel = new ChooseSavePanel(save_file);

		CountDownLatch latch = new CountDownLatch(1);

    	final int[] selected_slot = { -1 };

		panel.setClickListener(slot ->
		{
			selected_slot[0] = slot;
			latch.countDown();   // RELEASE wait
		});

		SwingUtilities.invokeLater(() ->
		{
			this._frame.setContentPane(panel);
			this._frame.revalidate();
			this._frame.repaint();
			this._frame.setVisible(true);
		});

		try {
			latch.await(); // BLOCK until clicked
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return selected_slot[0];

		// this._frame.add(this._panel);
		// this._panel.repaint();
		// this._frame.setVisible(true);
	}
}
