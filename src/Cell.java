import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Cell {
    private TreeSet<Animal> animals = new TreeSet<>((a1, a2) -> a2.getEnergy() - a1.getEnergy());
    private boolean isPlant = false;

    public final int x;
    public final int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void growPlant() {
        isPlant = true;
    }

    public void placeAnimal(Animal animal) {
        animals.add(animal);
    }
    
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public Set<Animal> getDeadAnimals() {
        var deadAnimals = new HashSet<Animal>();
        for (var animal : animals) {
            if (animal.getEnergy() <= 0) {
                deadAnimals.add(animal);
            }
        }

        return deadAnimals;
    }

    public boolean eatPlant(int plantEnergy) {
        if (isPlant && !animals.isEmpty()) {
            var maxEnergy = animals.first().getEnergy();
            var strongestAnimals = animals.stream().takeWhile(animal -> animal.getEnergy() == maxEnergy)
                    .collect(Collectors.toList());

            var sharedEnergy = plantEnergy / strongestAnimals.size();

            for (var animal : strongestAnimals) {
                animal.eatPlant(sharedEnergy);
            }

            isPlant = false;

            return true;
        }
        return false;
    }

    public Animal reproduce() {
        Animal newborn = null;

        if (animals.size() >= 2) {
            var parents = animals.stream().limit(2).collect(Collectors.toList());

            newborn = new Animal(parents.get(0), parents.get(1));
        }

        return newborn;
    }
    
    public boolean isFree() {
        return !isPlant && animals.isEmpty();
    }

    @Override
    public String toString() {
        return animals.stream().map(el -> el.getEnergy()).collect(Collectors.toList()).toString();
    }
}
