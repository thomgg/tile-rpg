package MenuSystem;

public abstract class MenuOption extends MenuBox implements MenuAction{

    public MenuOption(String text) {
        super(text);
    }

    public void setChild(MenuBox child) {
        children.clear();
        children.add(child);
    }

    @Override
    public void action() {

    }

    @Override
    public MenuBox destination() {
        return null;
    }
}
