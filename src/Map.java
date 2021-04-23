import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {
    private List<List<Cell>> map;

    private Set<Cell> freeJungleCells = new HashSet<>();
    private Set<Cell> freeSteppeCells = new HashSet<>();

    private double jungleX;
    private double jungleY;
    private double jungleWidth;
    private double jungleHeight;

    Map(int width, int height, double jungleRatio) {
        jungleWidth = width * jungleRatio;
        jungleHeight = height * jungleRatio;

        jungleX = width / 2 - jungleWidth / 2;
        jungleY = height / 2 - jungleHeight / 2;

        map = new ArrayList<List<Cell>>(width);
        for (int y = 0; y < width; y++) {
            map.add(new ArrayList<Cell>(height));
            for (int x = 0; x < height; x++) {
                var cell = new Cell();
                map.get(y).add(new Cell());

                if(isInJungle(x, y)) {
                    freeJungleCells.add(cell);
                } else {
                    freeSteppeCells.add(cell);
                }
            }
        }
    }

    public boolean isInJungle(int x, int y) {
        return x > jungleX && x < jungleX + jungleWidth && y > jungleY && y < jungleY + jungleHeight;
    }

    public Cell getRandomSteppeCell() {
        // TODO: ADD NULL SAFETY!!!
        
        var randomCell = freeSteppeCells.iterator().next();

        freeSteppeCells.remove(randomCell);

        return randomCell;
    }
    
    public Cell getRandomJungleCell() {
        // TODO: ADD NULL SAFETY!!!
        
        var randomCell = freeJungleCells.iterator().next();

        freeJungleCells.remove(randomCell);

        return randomCell;
    }
    
    public void addFreeJungleCell(Cell cell) {
        freeJungleCells.add(cell);
    }

    public void addFreeSteppeCell(Cell cell) {
        freeJungleCells.add(cell);
    }

    public void traverse(Runner runner) {
        for (var list : map) {
            for (var cell : list) {
                runner.run(cell);
            }
        }
    }
}
