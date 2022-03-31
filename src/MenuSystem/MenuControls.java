package MenuSystem;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuControls extends KeyAdapter {
    int key_code = 0;
    long time_of_last_action = System.nanoTime();

    @Override
    public void keyPressed(KeyEvent e) {
        if (key_code == 0) {
            key_code = e.getKeyCode();
            System.out.print("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (key_code == e.getKeyCode()) key_code = 0;
    }
}
