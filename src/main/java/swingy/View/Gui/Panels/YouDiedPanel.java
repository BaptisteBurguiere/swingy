package swingy.View.Gui.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import swingy.View.Gui.SwingView;
import swingy.View.Gui.Components.TextArea;

public class YouDiedPanel extends BasePanel
{
    public YouDiedPanel()
    {
        BindKey(KeyEvent.VK_ENTER, "enter");
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(SwingView.GetSprite("map_background"), 0, 0, SwingView.GetWidth(), SwingView.GetHeight(), null);
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, SwingView.GetWidth(), SwingView.GetHeight());

        int font_size = SwingView.GetHeight() / 3;
        int width = TextArea.CalculateWidth("You Died", font_size);
        int height = TextArea.CalculateHeight(font_size);
        int x = (SwingView.GetWidth() - width) / 2;
        int y = (SwingView.GetHeight() - height) / 2;

        TextArea text_area = new TextArea(x, y, width, height, font_size);
        text_area.AddChunk("You Died", SwingView.VILLAIN_COLOR);

        text_area.Draw(g);
    }
}
