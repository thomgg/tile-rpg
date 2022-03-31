import java.util.Random;

public class Controller {
    static GameObject player_ref;
    GameObject me;
    int xmove, ymove, xdir, ydir, move_lag, lag_timer;

    public Controller(){
        xmove = 0;
        ymove = 0;
        xdir = 0;
        ydir = 0;

        // time in frames
        move_lag = 30;
        lag_timer = 0;
    }

    public void setGameObjectRef(GameObject gameObject) {
        me = gameObject;
    }

    public void update(){

    }
}

class VBAIController extends Controller { //Very Basic Artificial Intelligence
    Random random;
    private boolean moving;

    VBAIController(){
        super();
        random = new Random();
        xdir = (random.nextInt(4) - 1)%2;
        ydir = xdir == 0 ? (int)Math.pow(-1,random.nextInt(2)) : 0;
    }

    @Override
    public void update(){
        if (random.nextInt(2) == 1) moving = !moving;
        if (random.nextInt(2) == 1) {
            xdir = 0; ydir = 0;
            switch (random.nextInt(4)) {
                case 0 : xdir = 1; break;
                case 1 : ydir = 1; break;
                case 2 : xdir = -1; break;
                case 3 : ydir = -1; break;
            }
        }
        if (moving && lag_timer == 0 && random.nextInt(2) == 1) {
            xmove = xdir;
            ymove = ydir;
            lag_timer = move_lag;
        } else {
            xmove = 0;
            ymove = 0;
        }
        if (lag_timer > 0) lag_timer--;
    }
}

class BasicEnemyController extends VBAIController {
    boolean enemy_spotted;
    Hitbox myHB;
    int atk;

    public BasicEnemyController(GameObject gameObject){
        super();
        me = gameObject;
        if (gameObject instanceof Chara) atk = ((Chara) gameObject).atk;
        move_lag = 60;
    }

    @Override
    public void update() {
        enemy_spotted = GameObject.dist(me, player_ref) <= 6;
        if (enemy_spotted) {
            if (lag_timer == 0) {
                int xdiff = player_ref.x - me.x, ydiff = me.y - player_ref.y;
                if (Math.abs(xdiff) >= Math.abs(ydiff)) {
                    ydir = 0;
                    if (Math.abs(xdiff) <= 1) xdir = 0;
                    else xdir = (xdiff)/Math.abs(xdiff);
                } else {
                    xdir = 0;
                    if (Math.abs(ydiff) <= 1) ydir = 0;
                    else ydir = (ydiff)/Math.abs(ydiff);
                }

                if (xdir == 0 && ydir == 0) {
                    if (Math.abs(xdiff) == 1 && Math.abs(ydiff) == 1) {
                        if (random.nextInt(2) == 1) xdir = (xdiff)/Math.abs(xdiff);
                        else ydir = (ydiff)/Math.abs(ydiff);
                    } else if (Math.abs(xdiff) == 1 || Math.abs(ydiff) == 1) {
                        myHB = new Hitbox(me.x + xdiff, me.y - ydiff, atk, 5, Chara.char_class.enemy);
                        lag_timer += 60;
                    }
                }

                xmove = xdir;
                ymove = ydir;
                lag_timer += move_lag;
            } else {
                xmove = 0;
                ymove = 0;
            }
            if (lag_timer > 0) lag_timer--;
        } else super.update();

    }
}

class PlayerController extends Controller{
    Keys keys;

    PlayerController(Keys k) {
        keys = k;
        lag_timer = 0;
        move_lag = 0;
    }

    @Override
    public void update() {
        xdir = keys.xdir;
        ydir = keys.ydir;
        xmove = keys.hor;
        ymove = keys.vert;
    }
}
