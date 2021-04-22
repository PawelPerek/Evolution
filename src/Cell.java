import java.util.ArrayList;
import java.util.List;

public class Cell {
    private boolean isJungle;
    private List<MapElement> elements = new ArrayList<MapElement>();

    Cell(boolean isJungle) {
        this.isJungle = isJungle;
    }

    public void removeDeadAnimals() {
        for (var element : elements) {
            if (element.getMapElementType() == MapElementType.Animal) {
                var animal = (Animal) element;
                if (animal.getEnergy() <= 0) {
                    elements.remove(animal);
                }
            }
        }
    }

    public void moveAnimals() {
        for (var element : elements) {
            if (element.getMapElementType() == MapElementType.Animal) {
                var animal = (Animal) element;
                animal.rotate();
            }
        }
    }

    public void eatPlant(int plantEnergy) {
        Plant plant = null;
        var strongestAnimals = new ArrayList<Animal>();

        for (var element : elements) {
            if (element.getMapElementType() == MapElementType.Animal) {
                var animal = (Animal) element;
                if (strongestAnimals.isEmpty()) {
                    strongestAnimals.add(animal);
                } else {
                    var exampleAnimal = strongestAnimals.get(0);

                    if (exampleAnimal.getEnergy() < animal.getEnergy()) {
                        strongestAnimals.clear();
                        strongestAnimals.add(animal);
                    } else if (exampleAnimal.getEnergy() == animal.getEnergy()) {
                        strongestAnimals.add(animal);
                    }
                }
            } else if (element.getMapElementType() == MapElementType.Plant) {
                plant = (Plant) element;
            }
        }

        if (plant != null) {
            var sharedEnergy = plantEnergy / strongestAnimals.size();

            for (var animal : strongestAnimals) {
                animal.addEnergy(sharedEnergy);
            }

            elements.remove(plant);
        }
    }

    @Override
    public String toString() {
        return elements.get(0).toString();
    }
}
