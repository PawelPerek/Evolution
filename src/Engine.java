public class Engine {
    private Map map;
    private Config config;

    public void generateConfig() {
        config = new Config(
            15,
            10,
            100,
            10,
            20,
            0.1
        );
    } 

    public void start() {
        map = new Map(config.width(), config.height(), config.jungleRatio());
    } 

    public void loop() {
        map.traverse((cell) -> {
            var deadAnimals = cell.getDeadAnimals();
            for(var animal : deadAnimals) {
                map.removeAnimal(animal, cell);
            }
        });
        
        map.moveAnimals();

        map.traverse((cell) -> {
            var isSuccessfulEat = cell.eatPlant(config.plantEnergy());
            if(isSuccessfulEat) {
                map.returnFreeCell(cell);
            }
        });
        
        map.traverse((cell) -> {
            var baby = cell.reproduce();
            if(baby != null) {
                var freeSpace = map.searchForFreeSpace(cell);
                if(freeSpace != null) {
                    freeSpace.placeAnimal(baby);
                }
            }
        });

        map.getRandomJungleCell().growPlant();
        map.getRandomSteppeCell().growPlant();

    } 
}
