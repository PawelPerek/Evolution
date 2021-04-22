public enum Direction {
    North, NorthEast, East, SouthEast, South, SouthWest, West, NorthWest

    public int[] toVector() {
        return switch (this) {
            case North -> {0, -1};
            case NorthEast -> {1, -1};
            case East -> {1, 0};
            case SouthEast -> {1, 1};
            case South -> {0, 1};
            case SouthWest -> {-1, 1};
            case West -> {-1, 0};
            case NorthWest -> {-1, -1};
        };
    }

    public Direction rotate(int rotation) {
        var index = this.ordinal();
        var directions = Direction.values();

        return directions[(index + rotation) % directions.length];
    };
};
