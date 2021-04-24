import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class GameMap {

    private Set<Cell> freeJungleCells = new HashSet<>();
    private Set<Cell> freeSteppeCells = new HashSet<>();

    private Cell[][] keys;
    private Map<Cell, TreeSet<Animal>> map;

    private int width;
    private int height;

    private double jungleX;
    private double jungleY;
    private double jungleWidth;
    private double jungleHeight;

    GameMap(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = width;

        jungleWidth = width * jungleRatio;
        jungleHeight = height * jungleRatio;

        jungleX = width / 2 - jungleWidth / 2;
        jungleY = height / 2 - jungleHeight / 2;

        map = new HashMap<Cell, TreeSet<Animal>>(width * height);
        keys = new Cell[width][height];

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var cell = new Cell(x, y);
                keys[x][y] = cell;
                map.put(cell, new TreeSet<>((a1, a2) -> a2.getEnergy() - a1.getEnergy()));
            }
        }
    }

    public Cell getKey(int x, int y) {
        return keys[x][y];
    }

    public boolean isInJungle(int x, int y) {
        return x > jungleX && x < jungleX + jungleWidth && y > jungleY && y < jungleY + jungleHeight;
    }

    public Cell getRandomSteppeCell() {
        var it = freeSteppeCells.iterator();
        Cell cell = null;
        if (it.hasNext()) {
            var randomCell = freeSteppeCells.iterator().next();

            freeSteppeCells.remove(randomCell);

            cell = randomCell;
        }
        return cell;
    }

    public Cell getRandomJungleCell() {
        var it = freeJungleCells.iterator();
        Cell cell = null;
        if (it.hasNext()) {
            var randomCell = freeJungleCells.iterator().next();

            freeJungleCells.remove(randomCell);

            cell = randomCell;
        }
        return cell;
    }

    public void returnFreeCell(Cell cell) {
        if (isInJungle(cell.x, cell.y)) {
            freeJungleCells.add(cell);
        } else {
            freeSteppeCells.add(cell);
        }
    }

    public Cell findFreeSpace(Cell center) {
        for (var direction : Direction.values()) {
            var vec = direction.toVector();
            var x = center.x + vec.x;
            var y = center.y + vec.y;

            if (x > 0 && x < width && y > 0 && y < height) {
                var cell = getKey(x, y);

                if (!cell.hasPlant() && map.get(cell).isEmpty()) {
                    return cell;
                }
            }
        }

        var rng = new Random();
        var directions = Direction.values();
        var vec = directions[rng.nextInt(directions.length)].toVector();

        return getKey(center.x + vec.x, center.y + vec.y);
    }

    public void removeAnimal(Animal animal, Cell cell) {
        map.get(cell).remove(animal);
    }

    public void removeDeadAnimals() {
        for (var entry : map.entrySet()) {
            for (var animal : entry.getValue()) {
                if (animal.getEnergy() <= 0) {
                    removeAnimal(animal, entry.getKey());
                }
            }
        }
    }

    public void changeAnimalPosition(Animal animal, Cell currentPosition) {
        var vector = animal.getDirection().toVector();

        var newPosition = getKey(currentPosition.x + vector.x, currentPosition.y + vector.y);

        map.get(currentPosition).remove(animal);
        map.get(newPosition).add(animal);
    }

    public void moveAnimals(int lostEnergy) {
        for (var entry : map.entrySet()) {
            for (var animal : entry.getValue()) {
                animal.rotate();
                changeAnimalPosition(animal, entry.getKey());
                animal.loseEnergy(lostEnergy);
            }
        }
    }

    public void eatPlants(int restoredEnergy) {
        for (var entry : map.entrySet()) {
            var cell = entry.getKey();
            var animals = entry.getValue();

            if (cell.hasPlant() && !animals.isEmpty()) {
                var maxEnergy = animals.first().getEnergy();
                var strongestAnimals = animals.stream().takeWhile((animal) -> animal.getEnergy() == maxEnergy)
                        .collect(Collectors.toSet());

                for (var animal : strongestAnimals) {
                    animal.eatPlant(restoredEnergy / strongestAnimals.size());
                }

                cell.eatPlant();
            }
        }
    }

    public void reproduce(int requiredEnergy) {
        for (var entry : map.entrySet()) {
            var cell = entry.getKey();
            var animals = entry.getValue();

            if (animals.size() >= 2) {
                var strongestAnimals = animals.stream().limit(2).collect(Collectors.toList());

                if (strongestAnimals.get(1).getEnergy() >= requiredEnergy) {
                    var freeCell = findFreeSpace(cell);

                    var newborn = new Animal(strongestAnimals.get(0), strongestAnimals.get(1));
                    map.get(freeCell).add(newborn);
                }
            }
        }
    }
    
    public void growPlants() {
        var jungleCell = getRandomJungleCell();
        if(jungleCell != null) {
            jungleCell.growPlant();
        }

        var steppeCell = getRandomSteppeCell();
        if(steppeCell != null) {
            steppeCell.growPlant();
        } 
    }
}
