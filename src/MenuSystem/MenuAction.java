package MenuSystem;

public interface MenuAction {
    void action(); //action to perform when selected
    MenuBox destination(); //where to go when selected
}
