package Engine;
public enum Direction {
    North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest;

    public Vector toVector() {
        return switch (this) {
            case North -> new Vector(0, -1);
            case NorthEast -> new Vector(1, -1);
            case East -> new Vector(1, 0);
            case SouthEast -> new Vector(1, 1);
            case South -> new Vector(0, 1);
            case SouthWest -> new Vector(-1, 1);
            case West -> new Vector(-1, 0);
            case NorthWest -> new Vector(-1, -1);
        };
    }

    public Direction rotate(int rotation) {
        var index = this.ordinal();
        var directions = Direction.values();

        return directions[(index + rotation) % directions.length];
    };
};
