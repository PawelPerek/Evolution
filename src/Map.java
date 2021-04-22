import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<List<Cell>> map;

    Map(int width, int height) {
        map = new ArrayList<List<Cell>>(width);
        for (int i = 0; i < width; i++) {
            map.add(new ArrayList<Cell>(height));
            for (int j = 0; j < height; j++) {
                map.get(i).add(new Cell(false));
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
