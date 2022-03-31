import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LevelLoader {
    public static Map<String, String> levelNames; //maps Name to file name
    public static final String EXTENSION = ".lvl";
    public static String directory = "src/levels/";

    public static void loadLevelNames(String[] names, String[] filenames) {
        levelNames = new HashMap<>();
        int arr_length = Math.min(names.length, filenames.length);
        for (int i = 0; i < arr_length; i++) {
            levelNames.put(names[i], filenames[i]);
        }

        levelNames.put("TestLevel", "test");
        levelNames.put("TestLevelMore", "test2");
    }

    public static void loadLevelNames() {
        loadLevelNames(new String[]{}, new String[]{});
    }

    public static GameGrid loadLevel(String levelName) {
        try {
            Scanner scn = new Scanner(new File(directory + levelNames.get(levelName) + EXTENSION));
            GameGrid newGrid = new GameGrid(scn.nextInt(), scn.nextInt());
            scn.nextLine();
            newGrid.list = new ArrayList<>(10);
            for (int y = 0; y < newGrid.height; y++) {
                char[] line = scn.nextLine().toCharArray();
                for (int x = 0; x < newGrid.width; x++) {
                    newGrid.grid[x][y] = getGameObject(x, y, line[x]);
                }
            }
            while (scn.hasNextLine()) {
                GameObject newGO = null;
                String[] line = scn.nextLine().split(" ");
                switch (line[0]) {
                    case "GameObject":
                        newGO = new GameObject(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                        break;
                    case "Wall":
                        newGO = new Wall(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                        break;
                    case "Chara":
                        if (line.length == 4 && Chara.prefabs.containsKey(line[3]))
                            newGO = Chara.newChara(Chara.prefabs.get(line[3]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                        else
                            newGO = new Chara(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Chara.char_class.valueOf(line[3]),
                                    Integer.parseInt(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]), null);
                        break;
                }
                if (newGO != null) {
                    newGrid.list.add(newGO);
                    newGrid.move(newGO, newGO.x, newGO.y);
                }
            }
            scn.close();
            return newGrid;
        } catch (FileNotFoundException fnfx) {
            System.out.println("Failed to Load.");
            System.out.println(fnfx.getMessage());
        }

        return null;
    }

    public static void saveLevel(GameGrid game, String levelName) {
        try {
            FileWriter fw = new FileWriter(new File(directory + levelNames.get(levelName) + EXTENSION));
            fw.write("");
            String dim = game.width + " " + game.height + "\n";
            fw.append(dim);
            for (int y = 0; y < game.height; y++) {
                for (int x = 0; x < game.width; x++)
                    fw.append(getChar(game.grid[x][y]));
                fw.append('\n');
            }
            for (GameObject go : game.list)
                fw.append(go.toString()).append("\n");
            fw.close();
        } catch (IOException iox) {
            System.out.println("Failed to Save.");
            System.out.println(iox.getMessage());
        }

    }

    public static char getChar(GameObject go) {
        if (go instanceof Wall) {
            if (go instanceof Water) return 'w';
            else if (go instanceof WindowWall) return 'o';
            else if (go instanceof TreeScenery) return 't';
            else return 'x';
        } else return '.';
    }

    public static GameObject getGameObject(int x, int y, char c) {
        switch (c) {
            case 'x': return new Wall(x, y);
            case 'w': return new Water(x, y);
            case 'o': return new WindowWall(x, y);
            case 't': return new TreeScenery(x, y);
            default:
                return null;
        }
    }

    public static String loadText(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scn = new Scanner(new File(directory + filename));
            while (scn.hasNextLine()) sb.append(scn.nextLine()).append("\n");
        } catch (IOException iox) {
            return "";
        }
        return sb.toString();
    }
}
