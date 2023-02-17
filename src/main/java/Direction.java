public enum Direction {
    UP(1),
    DOWN(-1);

    private final int directionValue;

    Direction(final int directionValue) {
        this.directionValue = directionValue;
    }

    public int getDirectionValue() {
        return directionValue;
    }
}
