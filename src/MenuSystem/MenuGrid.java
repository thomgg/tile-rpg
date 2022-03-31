package MenuSystem;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.TreeSet;

public class MenuGrid extends MenuBox {
    int gridWidth, // number of columns of the grid
        maxHeight; // maximum number of grid rows

    public MenuGrid() {
        super();
        gridWidth = 1;
    }

    public MenuGrid(String text) {
        super(text);
        gridWidth = 1;
    }

    public MenuGrid(String text, Rectangle bounds) {
        super(text, bounds);
        gridWidth = 1;
    }

    public MenuGrid(String text, Rectangle bounds, int cols) {
        super(text, bounds);
        gridWidth = cols;
    }

    public MenuGrid(String text, Rectangle bounds, int cols, int maxRows) {
        super(text, bounds);
        gridWidth = cols;
        maxHeight = maxRows;
    }

    @Override
    public void draw(Graphics g, MenuBox selected) {
        Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(text, g);
        int startX = bounds.x + (int)((bounds.width - stringBounds.getWidth()) / 2);
        int lineHeight = (int)stringBounds.getHeight();
        g.setColor(Color.white);
        g.drawString(text, startX, bounds.y + lineHeight);

        int boxwidth = bounds.width / gridWidth;

        startX = bounds.x;
        int startY = bounds.y + 2 * lineHeight, i = 0;
        for (MenuBox child : children) {
            if (i > 0 && i % gridWidth == 0) {
                startY += boxwidth;
                startX = bounds.x;
            }
            i++;
            if (child.image != null) g.drawImage(child.image ,startX, startY, boxwidth, boxwidth, null);
            if (child == selected) {
                g.drawString(">", startX + 3, startY + boxwidth/2 + lineHeight/2);
                g.drawRect(startX, startY, boxwidth, boxwidth);
            }
            g.drawString(child.text, startX + lineHeight, startY + boxwidth/2 + lineHeight/2);
            startX += boxwidth;
        }
    }
}
