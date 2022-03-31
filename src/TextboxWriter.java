import java.awt.*;

public class TextboxWriter {
    String fullText;
    double update_speed = 1, char_pointer;
    boolean end;

    public void setText(String text) {
        fullText = text;
        char_pointer = 0;
        end = false;
    }

    public void update() {
        char_pointer += update_speed;
        if (char_pointer > fullText.length()) {
            char_pointer = fullText.length();
            end = true;
        }
    }

    public void draw(Graphics g) {
        String[] split = fullText.substring(0, (int)char_pointer).split("\n");
        int y_offset = 0;
        for (String s : split) {
            g.drawString(s, 5, 5 + y_offset);
            y_offset += 10;
        }
    }
}
