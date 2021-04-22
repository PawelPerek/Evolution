import java.util.Random;

public class Animal implements MapElement{ 
    
    final int[] genotype = new int[32]; 
    private int energy;
    private Direction direction; 

    Animal(int energy) {
        this.energy = energy;
    }

    @Override
    public MapElementType getMapElementType() {
        return MapElementType.Animal;
    }

    public int getEnergy() {
        return energy;
    }
    
    public void addEnergy(int newEnergy) {
        energy += newEnergy;
    }
    
    public int rotate() {
        var rng = new Random();
        var index = rng.nextInt(32);

        var rotation = genotype[index];

        direction.rotate(rotation);

        return energy;
    }

}
