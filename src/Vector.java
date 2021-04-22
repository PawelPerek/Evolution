public class Vector {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
}
