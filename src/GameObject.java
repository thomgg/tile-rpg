import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameObject {
    int x, y; //grid position
    Controller ctrl;

    public GameObject(){
        x = 0;
        y = 0;
    }

    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int h, int v){
        moveHor(h);
        moveVert(v);
    }

    public void moveVert(int v){
        y -= v;
    }

    public void moveHor(int v){
        x += v;
    }

    void setController(Controller ctrl) { this.ctrl = ctrl; }

    int getMoveHor() { return ctrl.xmove; }
    int getMoveVert() { return ctrl.ymove; }


    public void paint(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(0,0, 250/12, 250/12);
    }

    public void hit(Hitbox hb){

    }

    public <GOT extends GameObject> boolean collide(GOT other) {
        //false means collision, true means allow.
        return false;
    }

    public void update() {

    }

    public static GameObject parseTypeChar(char c, int x, int y) {

        if (c == 'x') return new Wall(x, y);
        else if (c >= 'a' && c < 'a' + 10) return new Chara(x, y, Chara.char_class.values()['a' - c], 0, 0, 0, null);
        else return null;
    }

    public static double dist(GameObject a, GameObject b) { //returns distance between 2 GameObjects
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + x + " " + y;
    }
}

class Wall extends GameObject {
    int tile; //id of the tile
    boolean solid;

    public Wall(int x, int y) {
        super(x, y);
        solid = true;
    }

    public void hit(Hitbox hb) {
        if (solid) hb.used();
    }

    public void paint(Graphics g){
        g.drawImage(ImageManager.getImage("wall"), 0,0,null);
    }
}

class Chara extends GameObject {
    enum char_class {none, player, enemy}
    char_class cclass = char_class.player;
    int max_hp, atk, def;
    double hp;
    boolean dead;
    Image sprite;
    String prefab = null;
    Map<Item, Integer> inventory;

    public Chara(int x, int y, char_class cclass, int max_hp, int atk, int def, Image sprite){
        super(x, y);
        this.cclass = cclass;
        this.max_hp = max_hp;
        hp = max_hp;
        this.atk = atk;
        this.def = def;
        this.sprite = sprite;
    }

    public void setInventory(Map<Item, Integer> inventory) {
        this.inventory = inventory;
    }

    static Chara newChara(Chara prefab, int x, int y){
        Chara chr = new Chara(x, y, prefab.cclass, prefab.max_hp, prefab.atk, prefab.def, prefab.sprite);
        chr.prefab = prefab.prefab;
        chr.ctrl = prefab.ctrl;
        chr.ctrl.setGameObjectRef(chr);
        return chr;
    }

    //prefabs
    private final static Chara goomba;

    static Map<String, Chara> prefabs = new HashMap<>();

    static {
        try {
            ImageManager.loadImages(new String[]{"goomba2"});
        } catch (IOException ioe){
            System.out.println(ioe.toString());
            System.exit(-1);
        }
        goomba = new Chara(-1, -1, char_class.enemy, 5,1,1,ImageManager.getImage("goomba2"));
        goomba.prefab = "goomba";
        goomba.ctrl = new BasicEnemyController(goomba);
        prefabs.put(goomba.prefab, goomba);
    }

    public void update(){
        if (hp <= 0.0) dead = true;
        if (ctrl != null) {
            ctrl.update();
        }
    }

    public void hit(Hitbox hb){
        if (cclass == hb.owner_class) return;
        double damage = hb.power - def;
        if (damage < 1) damage = 1;
        hp -= damage;
        hb.used();
    }

    public void paint(Graphics g){
        if (sprite == null) super.paint(g);
        else {
            if (ctrl != null && sprite.getWidth(null) == 80) {
                int tlx = 0;
                if (ctrl.ydir != 0) tlx += 20 * (ctrl.ydir + 1);
                else if (ctrl.xdir != 0) tlx += 20 * (2 - ctrl.xdir);
                g.drawImage(sprite, 0, 0, 20, 20, tlx,0, tlx + 20,20, null);
            } else g.drawImage(sprite, 0,0,null);
        }
    }

    @Override
    public String toString() {
        if (prefab != null) return super.toString() + " " + prefab;
        return super.toString() + " " + cclass + " " + max_hp  + " " + atk + " " + def;
    }
}

class Water extends Wall {


    public Water(int x, int y) {
        super(x, y);
        solid = false;
    }

    public void paint(Graphics g){
        g.drawImage(ImageManager.getImage("water"), 0,0,null);
    }
}

class WindowWall extends Wall {

    public WindowWall(int x, int y) {
        super(x, y);
        solid = false;
    }

    public void paint(Graphics g){
        g.drawImage(ImageManager.getImage("window"), 0,0,null);
    }
}

class TreeScenery extends Wall {

    public TreeScenery(int x, int y) {super(x, y);}

    public void paint(Graphics g){
        g.drawImage(ImageManager.getImage("tree"), 0,0,null);
    }
}

class Sign extends Wall {
    private String text;

    public Sign(int x, int y, String text) {
        super(x, y);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void paint(Graphics g) {
        g.drawImage(ImageManager.getImage("sign"), 0, 0, null);
    }
}

class Door extends Wall {

    public Door(int x, int y) {
        super(x, y);
    }

    @Override
    public <GOT extends GameObject> boolean collide(GOT other) {
        if (other instanceof Chara) {
            Chara ch_other = (Chara)other;
            if (ch_other.inventory != null) {
                Key copy = new Key(0,0).setDoor(this);
                if (ch_other.inventory.containsKey(copy)) {
                    ch_other.inventory.compute(copy, (k, v) -> {
                        if (v != null) return v-1;
                        else return v;
                    });
                    int num = ch_other.inventory.get(copy);
                    if (num <= 0) ch_other.inventory.remove(copy);
                    return true;
                } else return false;
            } else return false;
        } else return false;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(ImageManager.getImage("keydoor"), 0,0,null);
    }
}
