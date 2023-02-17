public class Command implements Comparable<Command> {
    private final static int MINIMAL_FLOOR_VALUE = 0;
    private final static int MAXIMAL_FLOOR_VALUE = 55;

    private final Integer sourceFloor;
    private final Integer destinationFloor;

    private final Direction direction;

    /**
     * Command constructor.
     *
     * @param sourceFloor      source floor value
     * @param destinationFloor destination floor value
     * @throws IllegalArgumentException if source or destination floor value is not in valid range
     */
    public Command(Integer sourceFloor, Integer destinationFloor) {
        if (isFloorNotInRange(sourceFloor)) {
            throw new IllegalArgumentException(
                    "Source floor not in range " + MINIMAL_FLOOR_VALUE + "-" + MAXIMAL_FLOOR_VALUE);
        }
        if (isFloorNotInRange(destinationFloor)) {
            throw new IllegalArgumentException(
                    "Destination floor not in range " + MINIMAL_FLOOR_VALUE + "-" + MAXIMAL_FLOOR_VALUE);
        }

        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;

        this.direction = sourceFloor < destinationFloor ? Direction.UP : Direction.DOWN;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Check if floor value is not in valid range (0 - 55).
     *
     * @param floorValue floor value to check
     * @return true if not in range and false otherwise
     */
    private boolean isFloorNotInRange(Integer floorValue) {
        return MINIMAL_FLOOR_VALUE > floorValue || floorValue > MAXIMAL_FLOOR_VALUE;
    }

    @Override
    public int compareTo(Command o) {
        return this.sourceFloor.compareTo(o.getSourceFloor());
    }

    @Override
    public String toString() {
        return "Command{" + "source=" + sourceFloor + ", destination=" + destinationFloor + ", direction=" + direction +
                '}';
    }
}
