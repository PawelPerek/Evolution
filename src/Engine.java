public class Engine {
    private Map map;
    private Config config;

    public void start() {
        this.map = new Map(config.width(), config.height());



    } 
    
    public void generateConfig() {
        this.config = new Config(
            15,
            10,
            100,
            10,
            20,
            0.1
        );
    } 

    public void loop() {
        map.traverse((cell) -> cell.removeDeadAnimals());
        map.traverse((cell) -> cell.moveAnimals());
        map.traverse((cell) -> cell.eatPlant(config.plantEnergy()));
        map.traverse((cell) -> cell.reproduce(config.startEnergy()));
    } 
}
