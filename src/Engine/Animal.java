package Engine;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Animal { 
    
    final Integer[] genotype; 
    private int energy;
    private Direction direction = Direction.North; 

    Animal(int initialEnergy) {
        energy = initialEnergy;
        var arr = new Integer[32];
        var rng = new Random();
        for(int i = 0; i < 32; i++) {
            arr[i] = rng.nextInt(7);
        }
        genotype = arr;
    }
    
    Animal(Animal mother, Animal father) {
        energy = mother.giveBirth() + father.giveBirth();
        
        var rng = new Random();

        var firstChunkBound = rng.nextInt(32);
        var secondChunkBound = rng.nextInt(32 - firstChunkBound);
        var thirdChunkBound = rng.nextInt(32 - firstChunkBound - secondChunkBound);

        var firstChunk = Arrays.copyOfRange(mother.genotype, 0, firstChunkBound);
        var secondChunk = Arrays.copyOfRange(mother.genotype, firstChunkBound, firstChunkBound + secondChunkBound);
        var thirdChunk = Arrays.copyOfRange(father.genotype, firstChunkBound + secondChunkBound, firstChunkBound + secondChunkBound + thirdChunkBound);
    
        genotype = Stream.of(firstChunk, secondChunk, thirdChunk).toArray(Integer[]::new);
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
