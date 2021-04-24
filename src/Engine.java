public class Engine {
    private GameMap map;
    private Config config;

    public void start(Config config) {
        this.config = config;
        map = new GameMap(config.width(), config.height(), config.jungleRatio(), config.startEnergy());
    } 

    public void loop() {
        map.removeDeadAnimals();
        map.moveAnimals(config.moveEnergy());
        map.eatPlants(config.plantEnergy());
        map.reproduce(config.startEnergy() / 2);
        map.growPlants();
    }
    
    public RendererState getState(int x, int y) {
        return map.getState(x, y);
    }

    
    
}
