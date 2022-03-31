package MenuSystem;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.TreeSet;

public class MenuBox implements Comparable {
    protected MenuBox parent;
    protected TreeSet<MenuBox> children;
    protected int position, depth = 0;
    protected String text;
    Rectangle bounds;
    protected Image image = null;

    public MenuBox() {
        children = new TreeSet<MenuBox>();
        this.text = "";
    }

    public MenuBox(String text) {
        children = new TreeSet<>();
        this.text = text;
    }

    public MenuBox(String text, Rectangle bounds) {
        children = new TreeSet<>();
        this.text = text;
        this.bounds = bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void addChild(MenuBox menuBox) {
        menuBox.parent = this;
        menuBox.position = children.size() + 1;
        menuBox.depth = depth + 1;
        children.add(menuBox);
    }

    public void addChild(MenuBox menuBox, int position) {
        menuBox.parent = this;
        menuBox.position = position;
        menuBox.depth = depth + 1;
        if (children.contains(menuBox)) {
            updatePositions(position);
        }
        children.add(menuBox);
    }

    private void updatePositions(int starting_position) {
        int pos = starting_position;
        Iterator it = children.iterator();
        MenuBox next;
        while (it.hasNext()) {
            next = (MenuBox) it.next();
            if (next.position == pos) {
                next.position++;
                pos++;
            }
        }
    }

    private void setOrder(int position) {
        this.position = position;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void traverse() {
        for (MenuBox item : children) {
            System.out.println(item.text);
            if (item instanceof MenuOption) ((MenuOption) item).action();
            item.traverse();
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof MenuBox)
            if (depth == ((MenuBox) o).depth)
                if (parent == ((MenuBox) o).parent) return position - ((MenuBox) o).position;
                else return parent.position - ((MenuBox) o).parent.position;
            else return depth - ((MenuBox) o).depth;
        else return 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("_").append(text).append("_\n");
        for (MenuBox child : children) sb.append(child.text).append("\n");
        return sb.toString();
    }

    public void draw(Graphics g, MenuBox selected) {
        Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(text, g);
        //Rectangle bounds = g.getClipBounds();
        int startX = bounds.x + (int)((bounds.width - stringBounds.getWidth()) / 2);
        int lineHeight = (int)stringBounds.getHeight();
        g.setColor(Color.white);
        g.drawString(text, startX, bounds.y + lineHeight);

        startX = bounds.x + lineHeight;
        int startY = bounds.y + 2 * lineHeight;
        for (MenuBox child : children) {
            if (child == selected) g.drawString(">", startX - 10, startY);
            g.drawString(child.text, startX, startY);
            startY += 2 * lineHeight;
        }
    }
}
