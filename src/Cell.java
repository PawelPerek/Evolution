import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                animal.lose();
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
                animal.eatPlant(sharedEnergy);
            }

            elements.remove(plant);
        }
    }

    public Animal reproduce(int startEnergy) {
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
            }
        }

        if(strongestAnimals.size() == 2) {
            return new Animal(startEnergy, strongestAnimals.get(0), strongestAnimals.get(1));
        } else if (strongestAnimals.size() > 2) {
            var rng = new Random();

            var mother = strongestAnimals.get(rng.nextInt(strongestAnimals.size()));
            strongestAnimals.remove(mother);
            var father = strongestAnimals.get(rng.nextInt(strongestAnimals.size()));

            return new Animal(startEnergy, mother, father);
        }

        return null;
    }

    @Override
    public String toString() {
        return elements.get(0).toString();
    }
}
