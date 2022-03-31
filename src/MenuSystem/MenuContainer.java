package MenuSystem;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.NoSuchElementException;

public class MenuContainer {
    protected MenuBox root;
    protected MenuBox current_box, selection;
    public MenuControls ctrl = new MenuControls();

    public MenuContainer(MenuBox root_menu) {
        root = root_menu;
        current_box = root;
        try {
            selection = current_box.children.first();
        } catch (NoSuchElementException nsex) {
            selection = null;
        }
        System.out.println(root);
    }

    public void setRoot(MenuBox root) {
        this.root = root;
    }

    public void addChild(MenuBox child) { //add to next position
        root.addChild(child, root.children.size() + 1); //size 0 means new child will be position 1
        child.parent = root;
        if (selection == null && current_box == root) {
            selection = child;
            System.out.println("> " + selection.text);
        }
    }

    public void addChild(MenuBox child, int position /*Ordinal position i.e. first = 1*/) {
        root.addChild(child, position);
    }

    public void traverse() {
        root.traverse();
    }

    public void update() {
        MenuBox curr = selection;
        int key = ctrl.key_code;
        if (key != 0 && System.nanoTime() - ctrl.time_of_last_action > 100000000.0/6.0) {
            switch (key) {
                case KeyEvent.VK_DOWN:
                    try {
                        MenuBox next = null;
                        if (current_box instanceof MenuGrid) {
                            for (MenuBox child : current_box.children)
                                if (child.position == selection.position + ((MenuGrid) current_box).gridWidth)
                                    next = child;
                        } else next = current_box.children.higher(selection);
                        if (next != null) selection = next;
                    } catch (NoSuchElementException ignored) {
                    }
                    break;
                case KeyEvent.VK_UP:
                    try {
                        MenuBox prev = null;
                        if (current_box instanceof MenuGrid) {
                            for (MenuBox child : current_box.children)
                                if (child.position == selection.position - ((MenuGrid) current_box).gridWidth)
                                    prev = child;
                        } else prev = current_box.children.lower(selection);
                        if (prev != null) selection = prev;
                    } catch (NoSuchElementException ignored) {
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    try {
                        MenuBox prev = null;
                        if (current_box instanceof MenuGrid) prev = current_box.children.lower(selection);
                        if (prev != null) selection = prev;
                    } catch (NoSuchElementException ignored) {
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    try {
                        MenuBox next = null;
                        if (current_box instanceof MenuGrid) next = current_box.children.higher(selection);
                        if (next != null) selection = next;
                    } catch (NoSuchElementException ignored) {
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (selection instanceof MenuAction) {
                        ((MenuAction) selection).action();
                        navigate(((MenuAction) selection).destination());
                    } else navigate(selection);
                    break;
                case KeyEvent.VK_SHIFT:
                    if (current_box != root)
                        navigate(current_box.parent);
                    break;
            }
            ctrl.key_code = 0;
            System.out.println("> " + selection.text);
            ctrl.time_of_last_action = System.nanoTime();
        }
    }

    public void navigate(MenuBox to) {
        if (to != null) {
            current_box = to;
            try {
                selection = to.children.first();
            } catch (NullPointerException | NoSuchElementException ex) {
                selection = to;
            }
        }
        System.out.println(to);
    }

    public void setRootBounds(Rectangle bounds) {
        root.setBounds(bounds);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0,0,0,128));
        g.fill3DRect(root.bounds.x, root.bounds.y, root.bounds.width, root.bounds.height, false);

        //if (current_box.bounds == null)
            current_box.setBounds(root.bounds);
        current_box.draw(g, selection);
    }
}
