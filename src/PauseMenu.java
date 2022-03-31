import MenuSystem.*;
import MenuSystem.MenuContainer;
import java.util.Map;

public class PauseMenu extends MenuContainer {
    private Game game;
    boolean enabled;
    InvMenuGrid inv;

    public PauseMenu() {
        super(new MenuBox("Pause"));
        addChild(new MenuOption("Resume") {
            @Override
            public void action() {
                game.ctrl.pause = false;
                enabled = false;
            }

            @Override
            public MenuBox destination() {
                return parent;
            }
        });

        inv = new InvMenuGrid(7);
        addChild(inv);

        addChild(new MenuOption("Quit") {
            @Override
            public void action() {
                System.exit(1);
            }

            @Override
            public MenuBox destination() {
                return null;
            }
        });
    }

    public void setGame(Game game) {
        this.game = game;
        inv.setInv(game.inventory);
    }
}

class InvMenuGrid extends MenuGrid {
    Map<Item, Integer> inv;

    public InvMenuGrid(int start_size) {
        super("Inventory", null, 5, 5);
        for (int i = 1; i <= start_size; i++) {
            InvSlot invSlot = new InvSlot(null);
            invSlot.setText("Item " + i);
            addChild(invSlot);

        }
    }

    public void setInv(Map<Item, Integer> inventory) {
        inv = inventory;
    }

    public void addItem(Item item) {
        boolean added = false;
        for (MenuBox slot : children) {
            if (!(slot instanceof InvSlot)) continue;
            if (((InvSlot) slot).item == null) {
                ((InvSlot) slot).setItem(item);
                slot.setText(item.name);
                added = true;
                break;
            }
        }
        if (!added) addChild(new InvSlot(item));
    }
}

class InvSlot extends MenuBox implements MenuAction{
    Item item;

    public InvSlot(Item item) {
        if (item != null) {
            this.item = item;
            if (item.name != null) text = item.name;
            this.image = item.sprite;
        }
    }

    public void setItem(Item item) {
        this.item = item;
        if (item.name != null) text = item.name;
        this.image = item.sprite;
    }

    @Override
    public void action() {

    }

    @Override
    public MenuBox destination() {
        if (item == null) return null;
        else return this;
    }


}