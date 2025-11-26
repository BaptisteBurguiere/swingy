package swingy.View.Gui.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import swingy.Model.GameMap;
import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.PanelComponent;

public class MapPanel extends BasePanel
{
	private static final Random rand = new Random();

	public MapPanel(GameMap map)
	{
		Graphics g = this._background.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SwingView.GetWidth(), SwingView.GetHeight());

		int height = SwingView.GetHeight();
		int width = SwingView.GetHeight();
		int map_size = map.GetSize();
		int tile_size = width / (map_size + 2);

		int cursor_x = 0;
		int cursor_y = 0;

		if (tile_size * (map_size + 2) < height)
			cursor_y = (height - tile_size * (map_size + 2)) / 2;

		// Top wall
		g.drawImage(SwingView.GetSprite("top_left_wall"), cursor_x, cursor_y, tile_size, tile_size, null);
		cursor_x += tile_size;
		for (int i = 0; i < map_size; i++)
		{
			g.drawImage(SwingView.GetSprite("top_bottom_wall"), cursor_x, cursor_y, tile_size, tile_size, null);
			cursor_x += tile_size;
		}
		g.drawImage(SwingView.GetSprite("top_right_wall"), cursor_x, cursor_y, tile_size, tile_size, null);

		cursor_y += tile_size;

		// Map
		for (int i = 0; i < map_size; i++)
		{
			cursor_x = 0;
			g.drawImage(SwingView.GetSprite("left_wall"), cursor_x, cursor_y, tile_size, tile_size, null);
			cursor_x += tile_size;

			for (int j = 0; j < map_size; j++)
			{
				g.drawImage(GetRandomFloor(), cursor_x, cursor_y, tile_size, tile_size, null);
				cursor_x += tile_size;
			}
			
			g.drawImage(SwingView.GetSprite("right_wall"), cursor_x, cursor_y, tile_size, tile_size, null);
			cursor_y += tile_size;
		}

		cursor_x = 0;

		// Bottom wall
		g.drawImage(SwingView.GetSprite("bottom_left_wall"), cursor_x, cursor_y, tile_size, tile_size, null);
		cursor_x += tile_size;
		for (int i = 0; i < map_size; i++)
		{
			g.drawImage(SwingView.GetSprite("top_bottom_wall"), cursor_x, cursor_y, tile_size, tile_size, null);
			cursor_x += tile_size;
		}
		g.drawImage(SwingView.GetSprite("bottom_right_wall"), cursor_x, cursor_y, tile_size, tile_size, null);

		g.dispose();
	}

	private static BufferedImage GetRandomFloor()
	{
		double roll = rand.nextDouble();

		if (roll < 0.1)
			return SwingView.GetSprite("cracked_floor");
		if (roll < 0.3)
			return SwingView.GetSprite("small_cracked_floor_1");
		if (roll < 0.5)
			return SwingView.GetSprite("small_cracked_floor_2");
		
		return SwingView.GetSprite("floor");
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
