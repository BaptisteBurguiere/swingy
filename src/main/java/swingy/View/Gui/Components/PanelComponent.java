package swingy.View.Gui.Components;

import java.awt.Graphics;


public class PanelComponent
{
	protected int _top_left_x;
	protected int _top_left_y;
	protected int _bottom_right_x;
	protected int _bottom_right_y;
	
	protected boolean	_hover = false;
	protected boolean	_interactive = false;
	protected boolean	_hidden = false;

	public PanelComponent()
	{
		this._top_left_x = 0;
		this._top_left_y = 0;
		this._bottom_right_x = 0;
		this._bottom_right_y = 0;
	}

	public PanelComponent(int origin_x, int origin_y, int width, int height)
	{
		this._top_left_x = origin_x;
		this._top_left_y = origin_y;
		this._bottom_right_x = origin_x + width;
		this._bottom_right_y = origin_y + height;
	}

	public PanelComponent(int origin_x, int origin_y)
	{
		this._top_left_x = origin_x;
		this._top_left_y = origin_y;
		this._bottom_right_x = origin_x;
		this._bottom_right_y = origin_y;
	}

	public int GetOriginX() { return this._top_left_x; }

	public int GetOriginY() { return this._top_left_y; }

	public int GetWidth() { return this._bottom_right_x - this._top_left_x; }

	public int GetHeight() { return this._bottom_right_y - this._top_left_y; }

	public void Set(int origin_x, int origin_y, int width, int height)
	{
		this._top_left_x = origin_x;
		this._top_left_y = origin_y;
		this._bottom_right_x = origin_x + width;
		this._bottom_right_y = origin_y + height;
	}

	public void SetOrigin(int origin_x, int origin_y)
	{
		this._bottom_right_x = this._top_left_x - origin_x;
		this._bottom_right_y = this._top_left_y - origin_y;
		this._top_left_x = origin_x;
		this._top_left_y = origin_y;
	}

	public void SetOriginX(int origin_x)
	{
		int width = this.GetWidth();
		this._top_left_x = origin_x;
		this._bottom_right_x = origin_x + width;
	}

	public void SetOriginY(int origin_y)
	{
		int height = this.GetHeight();
		this._top_left_y = origin_y;
		this._bottom_right_y = origin_y + height;
	}

	public void SetSize(int width, int height)
	{
		this._bottom_right_x = this._top_left_x + width;
		this._bottom_right_y = this._top_left_y + height;
	}

	public void SetWidth(int width)
	{
		this._bottom_right_x = this._top_left_x + width;
	}

	public void SetHeight(int height)
	{
		this._bottom_right_y = this._top_left_y + height;
	}


	public boolean IsIn(int x, int y)
	{
		if (	x >= this._top_left_x && x <= this._bottom_right_x
			&&	y >= this._top_left_y && y <= this._bottom_right_y)
			return true;

		return false;
	}

	public boolean IsHover() { return this._hover; }

	public void HoverIn() { this._hover = true; }

	public void HoverOut() { this._hover = false; }

	public boolean IsInteractive() { return this._interactive; }

	public int Click(int x, int y) { return -1; }

	public void Hide() { this._hidden = true; }

	public void Show() { this._hidden = false; }
	
	public boolean IsHidden() { return this._hidden; } 

	public void Draw(Graphics g) {}
}
