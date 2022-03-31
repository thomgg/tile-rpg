import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HitboxGrid {
    int width, height;
    protected List<Hitbox> hb;

    public HitboxGrid(int width, int height) {
        this.width = width;
        this.height = height;
        hb = new LinkedList<>();
    }

    public boolean add(int x, int y, int power, int lifetime) {
        return add(x, y, power, lifetime, 0, 0);
    }

    public boolean add(int x, int y, int power, int lifetime, int xspeed, int yspeed) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) return false;
        Hitbox newh = new Hitbox(x, y, power, lifetime, xspeed, yspeed);
        Iterator it = hb.iterator();
        int res;
        while (it.hasNext()) {
            Hitbox h = (Hitbox) it.next();
            res = h.compareTo(newh);
            if (res >= 0) {
                if (res > 0) {
                    hb.add(newh);
                } else if (newh.power > h.power) {
                    it.remove();
                    hb.add(newh);
                }
                return true;
            } else return false;
        }
        hb.add(newh);
        return true;
    }

    public boolean add(Hitbox newh) {
        if (newh.x < 0 || newh.x > width - 1 || newh.y < 0 || newh.y > height - 1) return false;
        Iterator it = hb.iterator();
        int res;
        while (it.hasNext()) {
            Hitbox h = (Hitbox) it.next();
            res = h.compareTo(newh);
            if (res >= 0) {
                if (res > 0) {
                    hb.add(newh);
                } else if (newh.power > h.power) {
                    it.remove();
                    hb.add(newh);
                } else return false;
                return true;
            }// else return false;
        }
        hb.add(newh);
        return true;
    }

    public void update(int playerx, int playery) {
        Iterator it = hb.iterator();
        while (it.hasNext()) {
            Hitbox h = (Hitbox) it.next();
            h.update(playerx, playery);
            if (!h.active) it.remove();
        }
    }

    public void paint(Graphics g, int sx, int sy, boolean debug) {
        for (Hitbox h : hb) {
            if (h.sprite != null) {
                AffineTransform at = ((Graphics2D)g).getTransform();
                g.translate((h.x - sx) * 20, (h.y - sy) * 20);
                ((Graphics2D)g).rotate(h.sprite_rotation, 10, 10);
                g.drawImage(h.sprite, 0, 0, null);
                ((Graphics2D)g).setTransform(at);
            }
            if (debug) g.fillRect((h.x - sx) * 20, (h.y - sy) * 20, 20, 20);
        }
    }

}

class Hitbox implements Comparable<Hitbox> {
    int x, y,
            age, //time since creation in frames
            lifetime; //how long to live in frames
    double power,
            xspeed = 0, yspeed = 0; //how many frames to update
    boolean active, multihit = false;
    Chara.char_class owner_class = Chara.char_class.none;
    Image sprite = null;
    double sprite_rotation = 0;

    Hitbox(int x, int y, double power, int lifetime, double xspeed, double yspeed, Chara.char_class owner) {
        this.x = x;
        this.y = y;
        this.power = power;
        age = 0;
        this.lifetime = lifetime;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        active = true;
        owner_class = owner;
    }

    Hitbox(int x, int y, double power, int lifetime, double xspeed, double yspeed) {
        this.x = x;
        this.y = y;
        this.power = power;
        age = 0;
        this.lifetime = lifetime;
        this.xspeed = xspeed;
        this.yspeed = yspeed;
        active = true;
    }

    Hitbox(int x, int y, double power, int lifetime, Chara.char_class owner) {
        this.x = x;
        this.y = y;
        this.power = power;
        age = 0;
        this.lifetime = lifetime;
        active = true;
        owner_class = owner;
    }

    public void setImage(Image image) {
        this.sprite = image;
    }

    public void setSpriteRot(double rotation) { this.sprite_rotation = rotation; }

    public int collision(Hitbox other) {
        return (int) (this.power - other.power);
    }

    public int compareTo(Hitbox other) {
        //Hitboxes should be sorted by their position, by x first.

        if (x == other.x)
            if (y == other.y)
                if (age == other.age) return toString().compareTo(other.toString());
                else return age - other.age;
        else return y - other.y;
        else return x - other.x;
    }

    void update(int playerx, int playery) {
        age++;
        if (age > 0) {
            if (xspeed != 0 && age % xspeed == 0) {
                x += xspeed / Math.abs(xspeed);
            }
            if (yspeed != 0 && age % yspeed == 0) {
                y += yspeed / Math.abs(yspeed);
            }
        }
        if (age > lifetime || x < 0 || y < 0 || Math.abs(x - playerx) > 15 || Math.abs(y - playery) > 15)
            active = false;
    }

    void used() {
        if (!multihit) active = false;
    }
}

class SpreadHitbox extends Hitbox {
    int spread, dimFac, iteration = 1;
    boolean square, dim;

    SpreadHitbox(int x, int y, double power, int lifetime, Chara.char_class owner) {
        super(x, y, power, lifetime, owner);
        //default produces spread of radius 1 square around centre
        spread = 1;
        square = true;
    }

    SpreadHitbox(int x, int y, double power, int lifetime, Chara.char_class owner,
                 int spread_radius, boolean square_spread, boolean diminish_power, int diminish_factor/*percent*/) {
        super(x, y, power, lifetime, owner);
        if (spread_radius < 1) spread_radius = 1;
        spread = spread_radius;
        dim = diminish_power;
        if (dim && dimFac > 0 && dimFac <= 100) dimFac = diminish_factor; else dimFac = 100;
        square = square_spread;

    }

    public List<Hitbox> spread() {
        List<Hitbox> l = new ArrayList<>(iteration * 8);
        if (iteration <= spread) {
            int x = -iteration, y = iteration, //centre is (0,0)
                xd = 1, yd = 0;
            double dimmed_power = power * Math.pow(((100-dimFac) / 100.0),iteration);
            do {
                Hitbox newhb = new Hitbox(this.x + x, this.y - y, dimmed_power, lifetime, owner_class);
                newhb.setImage(sprite);
                newhb.multihit = multihit;
                if (x != 0) newhb.setSpriteRot(-(Math.atan2(y, x) - Math.PI/2));
                else if (y < 0) newhb.setSpriteRot(180);
                l.add(newhb);
                if (xd == 1 && x == iteration) {
                    xd = 0; yd = -1;
                } else if (yd == -1 && y == -iteration) {
                    xd = -1; yd = 0;
                } else if (xd == -1 && x == -iteration) {
                    xd = 0; yd = 1;
                }
                x += xd;
                y += yd;
           } while (!(x == -iteration && y == iteration));
            iteration++;
        }

        return l;
    }
}
