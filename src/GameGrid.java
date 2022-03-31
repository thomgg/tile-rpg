import java.util.List;

public class GameGrid {
    GameObject[][] grid;
    List<GameObject> list;
    int width, height;

    GameGrid(int width, int height){
        this.width = width;
        this.height = height;
        grid = new GameObject[width][height];
    }

    public boolean move(GameObject go, int hor_move, int vert_move){
        if (go.x + hor_move < 0 || go.y - vert_move < 0 || go.x + hor_move >= width || go.y - vert_move >= height) return false;
        if (grid[go.x + hor_move][go.y - vert_move] == go) return true;
        else if (grid[go.x + hor_move][go.y - vert_move] == null || grid[go.x + hor_move][go.y - vert_move].collide(go)) {
            grid[go.x][go.y] = null;
            go.move(hor_move, vert_move);
            grid[go.x][go.y] = go;
            return true;
        }
        else return false;
    }



}
