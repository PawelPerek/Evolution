import java.util.TreeSet;
import java.util.stream.Collectors;

public class Cell {
    private TreeSet<Animal> animals = new TreeSet<>((a1, a2) -> a2.getEnergy() - a1.getEnergy());
    private Plant plant = null;

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
            var strongestAnimals = animals.stream().takeWhile(animal -> animal.getEnergy() == maxEnergy)
                    .collect(Collectors.toList());

            var sharedEnergy = plantEnergy / strongestAnimals.size();

            for (var animal : strongestAnimals) {
                animal.eatPlant(sharedEnergy);
            }

            plant = null;
        }
    }

    public Animal reproduce() {
        Animal newborn = null;

        if (animals.size() >= 2) {
            var parents = animals.stream().limit(2).collect(Collectors.toList());

            newborn = new Animal(parents.get(0), parents.get(1));
        }

        return newborn;
    }

    @Override
    public String toString() {
        return animals.stream().map(el -> el.getEnergy()).collect(Collectors.toList()).toString();
    }
}
