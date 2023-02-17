import java.util.LinkedList;
import java.util.List;

public class Elevator implements Runnable {
    private static final int ONE_FLOOR_MOVEMENT_TIME = 1000;

    private final Command command;

    public Elevator(Command command) {
        this.command = command;
    }

    @Override
    public void run() {
        final List<Command> midStops = new LinkedList<>();
        long elevatorId = Thread.currentThread().getId();

        int destinationFloor = command.getDestinationFloor();
        int currentFloor = command.getSourceFloor();
        final Direction direction = command.getDirection();

        System.out.println("Elevator " + elevatorId + " has got command: " + command + ", processing");

        do {
            try {
                Thread.sleep(ONE_FLOOR_MOVEMENT_TIME);

                System.out.println("Elevator " + elevatorId + ", current floor: " + currentFloor);

                currentFloor += direction.getDirectionValue();

                Command midCommand;

                // Get the nearest (highest source for upwards movement, lowest source for downwards) command to process
                // while processing others. To simulate doors opening we could add a list of midCommands, but let us
                // amend it because it does not really affect the logic in this particular task.
                if (direction.equals(Direction.UP)) {
                    midCommand = ElevatorSystem.UP_MOVEMENT_COMMANDS.ceiling(
                            new Command(currentFloor, destinationFloor));
                } else {
                    midCommand = ElevatorSystem.DOWN_MOVEMENT_COMMANDS.floor(
                            new Command(currentFloor, destinationFloor));
                }

                // If command exists, add it to the commands list, remove from the set and change destination.
                if (midCommand != null) {
                    midStops.add(midCommand);

                    if (direction.equals(Direction.UP)) {
                        ElevatorSystem.UP_MOVEMENT_COMMANDS.remove(midCommand);

                        destinationFloor = Math.max(destinationFloor, midCommand.getDestinationFloor());
                    } else {
                        ElevatorSystem.DOWN_MOVEMENT_COMMANDS.remove(midCommand);

                        destinationFloor = Math.min(destinationFloor, midCommand.getDestinationFloor());
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (currentFloor != destinationFloor);

        System.out.println("Elevator " + elevatorId + " reached floor " + destinationFloor + ", ended processing");
        System.out.println("Elevator " + elevatorId + ", stops on the way: " + midStops);
    }
}
