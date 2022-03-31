import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//class to implement a controller that the player controls with the keyboard
public class Keys extends KeyAdapter{

    boolean pause, text_pause = false, close_text, switchFS, grid = true, debug = false, interact = false;
    int vert, hor, xdir, ydir;
    long time_of_last_movement, time_of_last_action, action_lag;
    final long[] lag_table = { //in nanoseconds
        100000000, //movement
        500000000, //attack1
        400000000, //attack2
        600000000  //throw grenade
    };
    final long[] action_duration = { //will be used so that the player can move while attacks are in cooldown
        100000000, //movement
        500000000, //attack1
        400000000, //attack2
        600000000  //throw grenade
    };

    int input_buffer = 0, action_buffer = 0, attack = 0;


    public Keys() {
        pause = false;
        switchFS = false;
        vert = 0;
        hor = 0;
        time_of_last_movement = System.nanoTime();
        time_of_last_action = System.nanoTime();
        action_lag = 0;
    }



    //handle events when a key is pressed
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!(vert == 0 && hor == 0)) input_buffer = key;
        switch (key) {
            case KeyEvent.VK_UP:
                if (vert == 0 && hor == 0) {
                    vert = 1;
                    hor = 0;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (vert == 0 && hor == 0) {
                    vert = -1;
                    hor = 0;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (vert == 0 && hor == 0) {
                    hor = -1;
                    vert = 0;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (vert == 0 && hor == 0) {
                    hor = 1;
                    vert = 0;
                }
                break;
            case KeyEvent.VK_0:
                System.out.println("switch");
                switchFS = true;
                break;
            case KeyEvent.VK_SPACE:
                if (action_buffer == 0) action_buffer = key;
                break;
            case KeyEvent.VK_X:
            case KeyEvent.VK_C:
            case KeyEvent.VK_V:
                action_buffer = key;
                break;
            case KeyEvent.VK_G:
                grid = !grid;
                break;
            case KeyEvent.VK_T:
                debug = !debug;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                break;
        }
        if (vert == 0 && hor == 0) {
            if (input_buffer == key) input_buffer = 0;
        } else {
                xdir = hor;
                ydir = vert;
            }
    }

    //handle when a key is released.
    public void keyReleased(KeyEvent e) {
        // please add appropriate event handling code
        int key = e.getKeyCode();
        if (input_buffer == key) {
            input_buffer = 0;
        }
        switch (key) {
            case KeyEvent.VK_UP:
                if (vert == 1) {
                    vert = 0;
                    checkBuffer();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (vert == -1) {
                    vert = 0;
                    checkBuffer();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (hor == -1) {
                    hor = 0;
                    checkBuffer();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (hor == 1) {
                    hor = 0;
                    checkBuffer();
                }
                break;
            case KeyEvent.VK_0:

                break;
            case KeyEvent.VK_SPACE:
                if (text_pause) {
                    if (action_buffer == key) {
                        action_lag = (long) Math.pow(10, 8);
                        close_text = true;
                    }
                    interact = false;
                } else interact = true;

                break;
            case KeyEvent.VK_X:
                if (action_buffer == key) {
                    attack = 1;
                    action_buffer = 0;
                    action_lag = lag_table[1];
                }
                break;
            case KeyEvent.VK_C:
                if (action_buffer == key) {
                    attack = 2;
                    action_buffer = 0;
                    action_lag = lag_table[2];
                }
                break;
            case KeyEvent.VK_V:
                if (action_buffer == key) {
                    attack = 3;
                    action_buffer = 0;
                    action_lag = lag_table[3];
                }
                break;
            case KeyEvent.VK_P:
                pause = !pause;
                break;
            case KeyEvent.VK_O:
                switchFS = true;
                break;
            case KeyEvent.VK_OPEN_BRACKET:
                text_pause = !text_pause;
                break;
        }
    }

    public void checkBuffer(){
        if (input_buffer != 0){
            switch (input_buffer){
                case KeyEvent.VK_UP:
                    vert = 1;
                    break;
                case KeyEvent.VK_DOWN:
                    vert = -1;
                    break;
                case KeyEvent.VK_LEFT:
                    hor = -1;
                    break;
                case KeyEvent.VK_RIGHT:
                    hor = 1;
                    break;
            }
            input_buffer = 0;
        }
    }

    public void reset(){

    }
}
