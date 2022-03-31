import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

public class OffGridGO extends GameObject {
    //x, y (from GameObject) refer the grid space the object is in
    double  offX, offY, //refer to offset from middle of grid position, as fraction of a grid square length. From -0.5 to 0.5
            velx, vely, //velocity in x and y directions
            rotation; //rotation in degrees
    //static int GRID_SQUARE_LENGTH; //not used (yet?)
    Image sprite;
    boolean airborne = false, alive = true;

    //static int GRID_SQUARE_LENGTH;

    public OffGridGO(int x, int y){
        super(x, y);
        offX = 0;
        offY = 0;
    }

    public OffGridGO(int x, int y, double xoffset, double yoffset){
        super(x, y);
        offX = xoffset;
        offY = yoffset;
        fixGridPos();
    }

    private void fixGridPos() {
        if (Math.abs(offX) > 0.5) {
        x += (int) offX;
        offX = offX % 1;
        }
        if (Math.abs(offY) > 0.5) {
            y += (int) offY;
            offY = offY % 1;
        }
    }

    public void gridMove(int xmove, int ymove) {
        x += xmove;
        y += ymove;
    }

    public void offGridMove(double xmove, double ymove) {
        offX += xmove;
        offY += ymove;
        fixGridPos();
    }

    public OffGridGO setVelocity(double vx, double vy) {
        velx = vx;
        vely = vy;
        return this;
    }

    @Override
    public void update() {
        if (velx != 0 || vely != 0) {
            offGridMove(velx, vely);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (sprite == null) {
            g.setColor(Color.cyan);
            g.fillRect(-125 / 24, -125 / 24, 250 / 18, 250 / 18);
        } else g.drawImage(sprite, -125 / 12, -125 / 12, null);
    }
}

class Item extends OffGridGO {
    String name;

    public Item(int x, int y) {
        super(x, y);
    }

    public Item(int x, int y, float xoffset, float yoffset) {
        super(x, y, xoffset, yoffset);
    }

    public Item getThisWithName(String name) {
        this.name = name;
        sprite = grabImage(this);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) return name.equals(((Item) obj).name);
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    private static Image grabImage(Item item) {
        if (item.name == null) return null;
        if (item.name.equals("Money")) {
            return ImageManager.getImage("money1");
        }
        else return ImageManager.getImage("null_item");
    }
}

class Key extends Item {
    Door door;
    public Key(int x, int y) {
        super(x, y);
    }

    public Key(int x, int y, float xoffset, float yoffset) {
        super(x, y, xoffset, yoffset);
    }

    public Key setDoor(Door door) {
        this.door = door;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key) return ((Key) obj).door == door;
        return false;
    }

    @Override
    public int hashCode() {
        return "Key".hashCode() + door.hashCode();
    }
}

class Projectile extends OffGridGO {
    Hitbox hitbox;

    public Projectile(int x, int y) {
        super(x, y);
    }

    public Projectile(int x, int y, double xoffset, double yoffset) {
        super(x, y, xoffset, yoffset);
    }

    public Projectile(int x, int y, Hitbox hitbox) {
        super(x, y);
        this.hitbox = hitbox;
    }

    public Projectile(int x, int y, double xoffset, double yoffset, Hitbox hitbox) {
        super(x, y, xoffset, yoffset);
        this.hitbox = hitbox;
    }

    public Projectile setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
        return this;
    }

    @Override
    public void update() {
        super.update();
        if (!hitbox.active) alive = false;
        hitbox.x = x;
        hitbox.y = y;
    }
}

class Grenade extends Projectile {
    int hangtime, detonator, lifetime; //in frames

    public Grenade(int x, int y, double xoffset, double yoffset, double velx, double vely, int detonator_length) {
        super(x, y, xoffset, yoffset);
        this.velx = velx;
        this.vely = vely;
        airborne = true;
        hangtime = (int)(Math.sqrt(Math.abs(velx+vely)) * 40);
        lifetime = 0;
        detonator = detonator_length;
    }

    @Override
    public void update() {
        super.update();
        lifetime++;
        if (airborne) {
            if (lifetime > hangtime) airborne = false;
        } else {
            vely = 0;
            velx = 0;
        }
        if (lifetime > detonator) alive = false;
    }
}

class Bullet extends Projectile {
    public Bullet(int x, int y) {
        super(x, y);

    }

    public Bullet(int x, int y, float xoffset, float yoffset) {
        super(x, y, xoffset, yoffset);
    }
}