package swingy.View.Gui.Components;

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
		g.drawImage(this._image, this._top_left_x, this._top_left_y, this.GetWidth(), this.GetHeight(), null);
	}
}
