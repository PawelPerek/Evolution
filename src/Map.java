import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {
    private List<List<Cell>> map;

    private Set<Cell> freeJungleCells = new HashSet<>();
    private Set<Cell> freeSteppeCells = new HashSet<>();
    
    private Set<Animal> allAnimals = new HashSet<>();

    private int width;
    private int height;

    private double jungleX;
    private double jungleY;
    private double jungleWidth;
    private double jungleHeight;

    Map(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = width;

        jungleWidth = width * jungleRatio;
        jungleHeight = height * jungleRatio;

        jungleX = width / 2 - jungleWidth / 2;
        jungleY = height / 2 - jungleHeight / 2;

        map = new ArrayList<List<Cell>>(width);
        for (int y = 0; y < width; y++) {
            map.add(new ArrayList<Cell>(height));
            for (int x = 0; x < height; x++) {
                var cell = new Cell(x, y);
                map.get(y).add(cell);

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
    
    public void returnFreeCell(Cell cell) {
        if(isInJungle(cell.x, cell.y)) {
            freeJungleCells.add(cell);
        } else {
            freeSteppeCells.add(cell);
        }
    }
    
    public Cell searchForFreeSpace(Cell center) {
        for(var direction : Direction.values()) {
            var vec = direction.toVector();
            var newX = center.x + vec.x;
            var newY = center.y + vec.y;

            if(newX > 0 && newX < width && newY > 0 && newY < height) {
                var cell = map.get(newX).get(newY);
                
                if(cell.isFree()) {
                    return cell;
                }
            }
        }

        return null;
    }

    public void removeAnimal(Animal animal, Cell cell) {
        allAnimals.remove(animal);
        cell.removeAnimal(animal);
    }

    public void moveAnimal(Animal animal, Cell oldCell, Cell cell) {
        allAnimals.add(animal);
        cell.placeAnimal(animal);
    }
    
    public void placeAnimal(Animal animal, Cell cell) {
        allAnimals.add(animal);
        cell.placeAnimal(animal);
    }

    public void traverse(Runner runner) {
        for (var list : map) {
            for (var cell : list) {
                runner.run(cell);
            }
        }
    }
}
