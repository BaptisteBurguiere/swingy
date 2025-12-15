package swingy.View.Gui.Components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import swingy.View.Gui.SwingView;

public class TextArea extends PanelComponent
{
	private static class Chunk
	{
		public String 	text;
		public Color	color;

		public Chunk(String text, Color color)
		{
			this.text = text;
			this.color = color;
		}
	}

	private List<Chunk>	_chunks;
	private int			_font_size;
	
	public TextArea(int origin_x, int origin_y, int width, int height, int font_size)
	{
		super(origin_x, origin_y, width, height);
		this._chunks = new ArrayList<>();
		this._font_size = font_size;
	}

	public void AddChunk(String text, Color color)
	{
		this._chunks.add(new Chunk(text, color));
	}

	public void Clear() { this._chunks.clear(); }

	public static int CalculateWidth(String str, int font_size)
	{
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		FontMetrics fm = g.getFontMetrics(SwingView.LoadCustomFont(font_size));
		g.dispose();

		return fm.stringWidth(str);
	}

	public static int CalculateHeight(int font_size)
	{
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		FontMetrics fm = g.getFontMetrics(SwingView.LoadCustomFont(font_size));
		g.dispose();

		return fm.getAscent() + fm.getDescent();
	}

	public void CalculateFontSize()
	{
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.dispose();

		boolean is_set = false;

		while (!is_set)
		{
			is_set = true;
			FontMetrics fm = g.getFontMetrics(SwingView.LoadCustomFont(this._font_size));
			int line_height = fm.getHeight();

			int cursor_x = this._top_left_x;
			int cursor_y = this._top_left_y + fm.getAscent();

			for (Chunk chunk : this._chunks)
			{
				int chunk_cursor = 0;

				while (chunk_cursor < chunk.text.length())
				{
					String current_text = chunk.text.substring(chunk_cursor);

					if (current_text.charAt(0) == '\n')
					{
						chunk_cursor += 1;
						cursor_y += line_height;
						cursor_x = this._top_left_x;
						continue;
					}
					if (current_text.charAt(0) == '\t')
					{
						String tab = GetTab();
						if (cursor_x + fm.stringWidth(tab) > this._bottom_right_x)
						{
							cursor_y += line_height;
							cursor_x = 0;
						}
						else
							cursor_x += fm.stringWidth(tab);

						chunk_cursor += 1;
						continue;
					}
					if (current_text.charAt(0) == ' ')
					{
						if (cursor_x + fm.stringWidth(" ") > this._bottom_right_x)
						{
							cursor_y += line_height;
							cursor_x = 0;
						}
						else
							cursor_x += fm.stringWidth(" ");

						chunk_cursor += 1;
						continue;
					}

					String word = current_text.substring(0, GetSeparatorIndex(current_text));

					if (cursor_x + fm.stringWidth(word) > this._bottom_right_x)
					{
						cursor_y += line_height;
						cursor_x = this._top_left_x;
					}

					if (cursor_y + fm.getDescent() > this._bottom_right_y)
					{
						is_set = false;
						this._font_size--;
						break;
					}

					cursor_x += fm.stringWidth(word);
					chunk_cursor += word.length();
				}

				if (!is_set)
					break;
			}
		}
	}

	@Override
	public void Draw(Graphics g)
	{
		g.setFont(SwingView.LoadCustomFont(this._font_size));

		FontMetrics fm = g.getFontMetrics();
		int line_height = fm.getHeight();

		int cursor_x = this._top_left_x;
		int cursor_y = this._top_left_y + fm.getAscent();

		for (Chunk chunk : this._chunks)
		{
			g.setColor(chunk.color);

			int chunk_cursor = 0;

			while (chunk_cursor < chunk.text.length())
			{
				String current_text = chunk.text.substring(chunk_cursor);

				if (current_text.charAt(0) == '\n')
				{
					chunk_cursor += 1;
					cursor_y += line_height;
					cursor_x = this._top_left_x;
					continue;
				}
				if (current_text.charAt(0) == '\t')
				{
					String tab = GetTab();
					if (cursor_x + fm.stringWidth(tab) > this._bottom_right_x)
					{
						cursor_y += line_height;
						cursor_x = this._top_left_x;
					}
					else
						cursor_x += fm.stringWidth(tab);

					chunk_cursor += 1;
					continue;
				}
				if (current_text.charAt(0) == ' ')
				{
					if (cursor_x + fm.stringWidth(" ") > this._bottom_right_x)
					{
						cursor_y += line_height;
						cursor_x = this._top_left_x;
					}
					else
						cursor_x += fm.stringWidth(" ");

					chunk_cursor += 1;
					continue;
				}

				String word = current_text.substring(0, GetSeparatorIndex(current_text));

				if (cursor_x + fm.stringWidth(word) > this._bottom_right_x)
				{
					cursor_y += line_height;
					cursor_x = this._top_left_x;
				}

				if (cursor_y + fm.getDescent() > this._bottom_right_y)
					return;

				g.drawString(word, cursor_x, cursor_y);
				cursor_x += fm.stringWidth(word);
				chunk_cursor += word.length();
			}
		}
	}

	private int GetSeparatorIndex(String str)
	{
		char separators[] = {' ', '\n', '\t'};

		int first = str.length();
		for (char c : separators)
		{
			int index = str.indexOf(c);
			if (index != -1 && index < first)
				first = index;
		}

		return first;
	}

	private static String GetTab()
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < SwingView.TAB_SIZE; i++)
			sb.append(' ');

		return sb.toString();
	}
}
