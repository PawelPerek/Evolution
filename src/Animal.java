import java.util.Random;

public class Animal { 
    
    final int[] genotype = new int[32]; 
    private int energy;
    private Direction direction; 

    Animal(int initialEnergy) {
        energy = initialEnergy;
    }
    
    Animal(Animal mother, Animal father) {
        energy = mother.giveBirth() + father.giveBirth();
    }

    public int getEnergy() {
        return energy;
    }
    
    public void eatPlant(int additionalEnergy) {
        energy += additionalEnergy;
    }
    
    public int giveBirth() {
        var energyLoss = energy / 4;
        
        loseEnergy(energyLoss);
        
        return energyLoss;
    }
    
    public void loseEnergy(int lostEnergy) {
        energy -= lostEnergy;
    }
    
    public void rotate() {
        var rng = new Random();
        var index = rng.nextInt(32);

        var rotation = genotype[index];

        direction = direction.rotate(rotation);
    }
    
    public Direction getDirection() {
        return this.direction;
    }

}
