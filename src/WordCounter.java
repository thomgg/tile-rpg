import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class WordCounter {
    public static void main(String[] args) throws IOException {
        int count = 0;
        String path = "docs/";
        final String ext = ".md";
        Path dir = new File(path+"default/").toPath();
        int i = 0;
        while (i < 2) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*" + ext)) {
                for (Path s : stream) {
                    String str;
                    Scanner scn = new Scanner(new File(dir.toString() + "\\" + s.getFileName()));
                    while (scn.hasNextLine()) {
                        str = scn.nextLine();
                        count += str.split(" ").length;
                    }
                }
            }
            dir = new File(path+"MenuSystem/").toPath();
            i++;
        }
        System.out.println("Total count = " + count);
    }
}

class LineCounter {
    public static void main(String[] args) throws IOException {
        int count = 0;
        String path = "src/";
        final String ext = ".java";
        Path dir = new File(path).toPath();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*" + ext)) {
            for (Path s : stream) {
                Scanner scn = new Scanner(new File(dir.toString() + "\\" + s.getFileName()));
                while (scn.hasNextLine()) {
                    scn.nextLine();
                    count++;
                }
            }
        }
        System.out.println("Total count = " + count);
    }
}