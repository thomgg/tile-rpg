package MenuSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuTest {
    public static void main(String[] args) throws InterruptedException {
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        win.setPreferredSize(new Dimension(600, 320));
        win.setSize(win.getPreferredSize());

        Rectangle frame_rect = win.getContentPane().getBounds();

        MenuContainer menu = new MenuContainer(
                new MenuBox("Container",
                        new Rectangle(frame_rect.width/4, frame_rect.height/4,
                                      frame_rect.width/2, frame_rect.height/2)));
        MenuOption mo = new MenuOption("Option 1") {
            @Override
            public void action() {
                System.out.println("Option 1 selected.");
            }
        };
        MenuOption mo2 = new MenuOption("Option 2") {
            @Override
            public void action() {
                super.action();
            }
        };
        MenuBox b1 = new MenuBox("Box");
        b1.addChild(mo);
        b1.addChild(mo2);
        menu.addChild(b1);

        win.addKeyListener(menu.ctrl);
        win.setVisible(true);

        while (menu.ctrl.key_code != KeyEvent.VK_ESCAPE) {
            menu.update();
            frame_rect = win.getContentPane().getBounds();
            menu.setRootBounds(
                    new Rectangle(frame_rect.width/4, frame_rect.height/4,
                               frame_rect.width/2, frame_rect.height/2));
            menu.draw(win.getContentPane().getGraphics());
            Thread.sleep(1000/60);
        }
    }
}
