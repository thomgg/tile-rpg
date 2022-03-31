import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game {
    List<GameObject> go;
    List<OffGridGO> nongo;
    Map<Item, Integer> inventory;
    GameObject player;
    GameGrid gg;
    HitboxGrid hbg;
    MainFrame gameWindow;
    DrawingPanel gameScreen;
    PauseMenu menu;
    Keys ctrl;
    static TextboxWriter txt;

    static {
        try {
            ImageManager.loadImagesFromDirectory();
            //ImageManager.loadImages(new String[]{"grass1a", "wall", "window", "water", "tree", "PlayerDrumstick1", "PlayerSoundwave1"});
        } catch (IOException ioe){
            System.out.println(ioe.toString());
            System.exit(-1);
        }
    }


    public Game(){/*
        go = new LinkedList<>();
        player = new GameObject(4, 7);
        //go.add(player);
        for (int i = 1; i < 7; i++){
            go.add(new Wall(i,1));
            go.add(new Wall(20, i+5));
        }
        Chara goomb = Chara.newChara(Chara.goomba, 10, 10);
        goomb.setController(new Controller());
        go.add(goomb);

        goomb = Chara.newChara(Chara.goomba, 10, 20);
        goomb.setController(new VBAIController(player));
        go.add(goomb);

        gg = new GameGrid(100, 100);
        gg.list = go;*/

        LevelLoader.loadLevelNames();
        gg = LevelLoader.loadLevel("TestLevelMore");
        go = gg.list;
        nongo = new ArrayList<>(20);
        inventory = new HashMap<>();
        player = new Chara(39, 19, Chara.char_class.player, 10, 1, 2, ImageManager.getImage("player"));
        Controller.player_ref = player;
        ((Chara)player).setInventory(inventory);

        gg.move(new Sign(43,26, "Hey! I'm a sign!!"), 0, 0); //test
        gg.move(new Sign(63,46, LevelLoader.loadText("text1.txt")), 0, 0); //lol

        nongo.add(new Item(38, 15, (float)0.5, (float)0.5).getThisWithName("Money"));

        Door new_door = new Door(32,16);
        gg.move(new_door, 0, 0);
        Key akey = new Key(50, 19, (float)0.25, (float)0).setDoor(new_door);
        akey.getThisWithName("Key");
        akey.sprite = ImageManager.getImage("akey");
        nongo.add(akey);

        hbg = new HitboxGrid(gg.width, gg.height);
        ctrl = new Keys();
        player.ctrl = new PlayerController(ctrl);

        txt = new TextboxWriter();
        txt.setText("This is test text. This is test text. This is test text.");

        menu = new PauseMenu();
        menu.setGame(this);

        gameWindow = new MainFrame(false);
        gameScreen = new DrawingPanel(this);
        gameWindow.add(gameScreen);
        gameWindow.addKeyListener(ctrl);
        gameWindow.addKeyListener(menu.ctrl);
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.update();

        //LevelLoader.loadLevelNames();
        //LevelLoader.saveLevel(game.gg, "TestLevelMore");

        final int TARGET_FRAMERATE = 60;
        while (true) {
            long time = System.nanoTime();
            game.update();
            game.gameScreen.repaint();
            time = System.nanoTime() - time;
            //change the thread delay so that there is a consistent number of frames.
            //if a frame takes longer to display than allowed, don't sleep at all
            if (time < 1000000000.0 / (double) TARGET_FRAMERATE) {
                double delay =  ((1000000000.0 / (double) TARGET_FRAMERATE - time) / 1000000.0);
                try {
                    Thread.sleep((long)delay);
                } catch (InterruptedException e) {
                    System.exit(1);
                }
                game.gameScreen.fps = 1000.0 / (delay + time/1000000.0);
            }
        }
    }

    public void update(){
        if (ctrl.switchFS) {
            boolean fs = !gameWindow.fullscreen;
            ctrl.switchFS = false;
            gameWindow.dispose();
            gameWindow = new MainFrame(fs);
            gameWindow.addKeyListener(ctrl);
            gameWindow.addKeyListener(menu.ctrl);
            gameWindow.add(gameScreen);
        }

        if (ctrl.pause) {
            if (!menu.enabled) menu.enabled = true;
            else menu.update();
            return;
        } else if (ctrl.text_pause) {
            txt.update();
            long time = System.nanoTime();
              if (ctrl.close_text)
                  if (time - ctrl.action_lag >= ctrl.time_of_last_action) {
                    if (txt.end) {
                        ctrl.text_pause = false;
                        ctrl.close_text = false;
                    }
                    else txt.char_pointer = txt.fullText.length();
                    ctrl.time_of_last_action = System.nanoTime();
                 }
              else if (txt.end) {
                      ctrl.close_text = false;
                  }
            return;
        } else {
            menu.enabled = false;
        }

        player.update();
        if ((System.nanoTime() - ctrl.action_lag >= ctrl.time_of_last_action) && (System.nanoTime() - ctrl.time_of_last_movement) >= ctrl.lag_table[0] && gg.move(player, ctrl.hor, ctrl.vert)) {
                ctrl.time_of_last_movement = System.nanoTime();
        }

        hbg.update(player.x, player.y);

        if (ctrl.attack > 0 && (System.nanoTime() - ctrl.time_of_last_action) >= ctrl.action_lag){
            Hitbox newhb;
            switch (ctrl.attack) {
                case 1 :
                    newhb = new Hitbox(player.x + ctrl.xdir, player.y - ctrl.ydir, 3, 20, Chara.char_class.player);
                    newhb.setImage(ImageManager.getImage("PlayerDrumstick1"));
                    newhb.setSpriteRot(Math.atan2(ctrl.xdir, ctrl.ydir));
                    hbg.add(newhb);
                    break;
                case 2 :/*
                    newhb = new Hitbox(player.x + ctrl.xdir, player.y - ctrl.ydir, 2, 100, ctrl.xdir * 4, -ctrl.ydir * 4, Chara.char_class.player);
                    newhb.setImage(ImageManager.getImage("PlayerSoundwave1"));
                    newhb.setSpriteRot(Math.atan2(ctrl.xdir, ctrl.ydir));
                    hbg.add(newhb);
                    */
                    Projectile pro = new Projectile(player.x + ctrl.xdir, player.y - ctrl.ydir, 0.5, 0.5);
                    pro.setVelocity(ctrl.xdir * 4 / 15.0, -ctrl.ydir * 4 / 15.0);
                    pro.rotation = Math.atan2(ctrl.xdir, ctrl.ydir);
                    pro.sprite = ImageManager.getImage("PlayerSoundwave1");

                    newhb = new Hitbox(player.x + ctrl.xdir, player.y - ctrl.ydir, 2, 100, 0,0, Chara.char_class.player);
                    pro.setHitbox(newhb);

                    hbg.add(newhb);
                    nongo.add(pro);
                    break;
                case 3 :
                    Grenade gre = new Grenade(player.x + ctrl.xdir, player.y - ctrl.ydir, 0.5, 0.5, ctrl.xdir / 4.0, -ctrl.ydir / 4.0, 150);
                    gre.rotation = Math.atan2(ctrl.xdir, ctrl.ydir);
                    SpreadHitbox sprhb = new SpreadHitbox(player.x + ctrl.xdir, player.y - ctrl.ydir, 10, 100, Chara.char_class.player, 2, true, true, 50);
                    sprhb.setImage(ImageManager.getImage("fire"));
                    gre.setHitbox(sprhb);
                    //gre.sprite = ImageManager.getImage("null_item");
                    nongo.add(gre);
                    break;
            }
            ctrl.action_lag = ctrl.lag_table[ctrl.attack];
            ctrl.attack = 0;
            ctrl.time_of_last_action = System.nanoTime();
        }

        if (ctrl.interact) {
            if (gg.grid[player.x + ctrl.xdir][player.y - ctrl.ydir] instanceof Sign) {
                txt.setText(((Sign)gg.grid[player.x + ctrl.xdir][player.y - ctrl.ydir]).getText());
                ctrl.text_pause = true;
            }
        }

        List<GameObject> deadList = new ArrayList<>(go.size());

        for (GameObject obj : go){
            if (obj instanceof Chara && Math.abs(obj.x - player.x) < 20 && Math.abs(obj.y - player.y) < 20) {
                obj.update();
                gg.move(obj, obj.getMoveHor(), obj.getMoveVert());
                if (obj.ctrl instanceof BasicEnemyController) {
                    if (((BasicEnemyController) obj.ctrl).myHB != null) {
                        hbg.add(((BasicEnemyController) obj.ctrl).myHB);
                        ((BasicEnemyController) obj.ctrl).myHB = null;
                    }
                }
            }
            if (obj instanceof Chara && ((Chara) obj).dead) deadList.add(obj);
        }

        for (GameObject dead : deadList){
            go.remove(dead);
            gg.grid[dead.x][dead.y] = null;
        }

        Iterator it = nongo.iterator();
        while (it.hasNext()) {
            OffGridGO obj = (OffGridGO) it.next();
            if (Math.abs(obj.x - player.x) < 20 && Math.abs(obj.y - player.y) < 20) {
                obj.update();
                if (obj instanceof Item) {
                    GameObject ongrid = gg.grid[obj.x][obj.y];
                    if (ongrid != null && ongrid == player) {
                        addItem(inventory, (Item)obj);
                        menu.inv.addItem((Item)obj);
                        it.remove();
                    }
                } else if (obj instanceof Grenade) {
                    if (!obj.alive) hbg.add(((Grenade) obj).hitbox);
                }
                if (!obj.alive) it.remove();
            }
        }

        List<SpreadHitbox> spreadHitboxes = null;
        for (Hitbox hb : hbg.hb){
            //if (gg.grid[hb.x][hb.y] instanceof Chara) {
            GameObject gameObject = gg.grid[hb.x][hb.y];
            if (gameObject != null)
                gameObject.hit(hb);
                //hb.used();
            //}
            if (hb instanceof SpreadHitbox) {
                if (spreadHitboxes == null) spreadHitboxes = new ArrayList<>(hbg.hb.size());
                spreadHitboxes.add((SpreadHitbox)hb);
            }
        }
        if (spreadHitboxes != null) for (SpreadHitbox shb : spreadHitboxes) for (Hitbox hb : shb.spread()) hbg.add(hb);


    }

    private static <T> void addItem(Map<T, Integer> map, T item ) {
        map.putIfAbsent(item, 0);
        map.computeIfPresent(item, (key, val) -> val = val + 1);
    }

    private static <T> void removeItems(Map<T, Integer> map, T item, int number) {
        map.computeIfPresent(item, (key, val) -> val = val - number);
        if (map.get(item) <= 0) map.remove(item);
    }
}

class MainFrame extends JFrame {
    boolean fullscreen;

    MainFrame(boolean fullscreen) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 331));
        setSize(getPreferredSize());
        if (fullscreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }
        this.fullscreen = fullscreen;

        setVisible(true);
    }

}

class DrawingPanel extends JPanel {
    private Game game;
    double fps;

    DrawingPanel(Game g) {
        game = g;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int screen_width = getWidth(), screen_height = getHeight();


        int width = 260, height = 260, top = 0, left = 0, scale = screen_height / height;

        left = screen_width / 2 - (width * scale) / 2;
        top = screen_height / 2 - (height * scale) / 2;

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = g2d.getTransform();


        g2d.translate(left, top);
        g2d.scale(scale, scale);

        AffineTransform  screenScroll = g2d.getTransform();
        int screenx = 0, screeny = 0;
        if (game.player.x > 6)
            if (game.player.x > game.gg.width - 7) screenx = game.gg.width - 13;
            else screenx = game.player.x - 6;

        if (game.player.y > 6)
            if (game.player.y > game.gg.height - 7) screeny = game.gg.height - 13;
            else screeny = game.player.y - 6;


        AffineTransform temp = g2d.getTransform();
        g2d.setTransform(temp);


        for (int i = screenx; i < screenx + 13; i++){
            temp = g2d.getTransform();

            for (int j = screeny; j < screeny + 13; j++){
                g2d.drawImage(ImageManager.getImage("grass1a"), null, null);
                GameObject current = game.gg.grid[i][j];
                if (current != null) {
                    current.paint(g2d);
                    if (game.ctrl.debug && current instanceof Chara) {
                        g2d.drawString(((Chara) current).hp+"/"+((Chara) current).max_hp, -30, -5);
                    }
                }
                g2d.translate(0,height / 13);
            }
            g2d.setTransform(temp);
            g2d.translate(width / 13, 0);
        }

        g2d.setTransform(screenScroll);

        for (OffGridGO go : game.nongo) {
            if (go.x > screenx && go.y > screeny && go.x < screenx + screen_width && go.y < screeny + screen_height) {
                temp = g2d.getTransform();
                g2d.translate(((go.x + go.offX - screenx) * width/13), ((go.y + go.offY - screeny) * height/13));
                if (go.rotation != 0.0) g2d.rotate(go.rotation);
                go.paint(g2d);
                g2d.setTransform(temp);
            }
        }


        g2d.setTransform(screenScroll);

        g2d.setColor(new Color(255, 0,0, 125));
        game.hbg.paint(g2d, screenx, screeny, game.ctrl.debug);

        g2d.setColor(Color.black);
        if (game.ctrl.grid) {
            for (int i = 0; i <= 13; i++) {
                g2d.drawLine(0, i * (height / 13), width, i * (height / 13));
                g2d.drawLine(i * (width / 13), 0, i * (width / 13), height);
            }
        }

        g2d.setTransform(at);
        g2d.translate(5,5);
        g2d.drawImage(ImageManager.getImage("healthbar"), null, null);
        double health_pc = ((Chara)game.player).hp/(double)((Chara)game.player).max_hp;
        g2d.setColor(new Color(255, 0,0));
        g.fillRect(22,4, (int)Math.ceil(72*health_pc), 6);
        g2d.setColor(new Color(0, 0,0));

        g2d.setTransform(at);

        if (game.ctrl.debug) {
            g.drawString("FPS: " + String.format("%1.2f", fps), 20, 50);
            g.drawString("x: " + game.player.x + " y: " + game.player.y, 20, 80);
            g.drawString("view x: " + screenx + " view y: " + screeny, 20, 110);
            g.drawString("Input Buffer: " + game.ctrl.input_buffer, 20, 130);
            g.drawString("Action Buffer: " + game.ctrl.action_buffer, 20, 150);
            g.drawString("Direction: x=" + game.ctrl.xdir + " y=" + game.ctrl.ydir, 20, 170);
        }

        if (game.ctrl.text_pause) {
            g2d.translate(20, screen_height - 100);
            g2d.setColor(new Color(0x692014));
            g2d.fillRoundRect(0,0, screen_width - 100, 100, 5, 5);
            g2d.setColor(Color.white);
            g2d.translate(0, 10);
            Game.txt.draw(g);
            g2d.setTransform(at);
        }

        if (game.menu.enabled) {
            Rectangle frame_rect = game.gameWindow.getContentPane().getBounds();
            game.menu.setRootBounds(
                    new Rectangle(frame_rect.width/4, frame_rect.height/4,
                            frame_rect.width/2, frame_rect.height/2));
            game.menu.draw(g);
        }
    }
}
