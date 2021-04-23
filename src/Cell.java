import java.util.TreeSet;
import java.util.stream.Collectors;

public class Cell {
    private boolean isJungle;

    private TreeSet<Animal> animals = new TreeSet<>((a1, a2) -> a2.getEnergy() - a1.getEnergy());
    private Plant plant = null;

    Cell(boolean isJungle) {
        this.isJungle = isJungle;
    }

    public void placeAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeDeadAnimals() {
        for (var animal : animals) {
            if (animal.getEnergy() <= 0) {
                animals.remove(animal);
            }
        }
    }

    public void moveAnimals() {
        for (var animal : animals) {
            animal.rotate();
        }
    }

    public void eatPlant(int plantEnergy) {
        if (plant != null && !animals.isEmpty()) {
            var maxEnergy = animals.first().getEnergy();
            var strongestAnimals = animals.stream().takeWhile(animal -> animal.getEnergy() == maxEnergy).collect(Collectors.toList());

            var sharedEnergy = plantEnergy / strongestAnimals.size();
            
            for(var animal : strongestAnimals) {
                animal.eatPlant(sharedEnergy);
            }

            plant = null;
        }
    }

    public Animal reproduce(int startEnergy) {
        


        return null;
    }

    @Override
    public String toString() {
        return animals.stream().map(el -> el.getEnergy()).collect(Collectors.toList()).toString();
    }
}
