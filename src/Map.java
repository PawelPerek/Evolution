import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {
    private List<List<Cell>> map;
    private Set<Cell> freeJungleCells = new HashSet<>();
    private Set<Cell> freeSteppeCells = new HashSet<>();

    Map(int width, int height, double jungleRatio) {
        var jungleWidth = width * jungleRatio;
        var jungleHeight = height * jungleRatio;

        var jungleX = width / 2 - jungleWidth / 2;
        var jungleY = height / 2 - jungleHeight / 2;

        map = new ArrayList<List<Cell>>(width);
        for (int y = 0; y < width; y++) {
            map.add(new ArrayList<Cell>(height));
            for (int x = 0; x < height; x++) {
                var isJungle = 
                    x > jungleX && x < jungleX + jungleWidth &&
                    y > jungleY && y < jungleY + jungleHeight;
                map.get(y).add(new Cell(isJungle));
            }
        }
    }

    public void traverse(Runner runner) {
        for (var list : map) {
            for (var cell : list) {
                runner.run(cell);
            }
        }
    }
}
