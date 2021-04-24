public class Cell {
    private boolean plant = false;

    public final int x;
    public final int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void growPlant() {
        this.plant = true;
    }
    
    public void eatPlant() {
        this.plant = false;
    }
    
    public boolean hasPlant() {
        return this.plant;
    }
}
