package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite extends PanelComponent
{
	private BufferedImage _image;
	
	public Sprite(BufferedImage image, int origin_x, int origin_y, int width, int height)
	{
		super(origin_x, origin_y, width, height);
		this._image = image;
		this._interactive = false;
	}

	@Override
	public void Draw(Graphics g)
	{
		if (!this._changed)
			return;

		int width = GetWidth();
		int height = GetHeight();
		int image_width = this._image.getWidth();
		int image_height = this._image.getHeight();

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				int pixel_x = (int)(((double)x / (double)width) * (double)image_width);
				int pixel_y = (int)(((double)y / (double)height) * (double)image_height);

				int argb = _image.getRGB(pixel_x, pixel_y);
				g.setColor(new Color(argb, true));
				g.fillRect(x, y, 1, 1);
			}
		}

		this._changed = false;
	}
}
