import java.util.Random;
import java.util.concurrent.*;

public class ElevatorSystem {
    private final static int ELEVATORS_AMOUNT = 7;

    public final static ConcurrentSkipListSet<Command> UP_MOVEMENT_COMMANDS = new ConcurrentSkipListSet<>();
    public final static ConcurrentSkipListSet<Command> DOWN_MOVEMENT_COMMANDS = new ConcurrentSkipListSet<>();

    private final static Random random = new Random();

    private final static ExecutorService elevatorSystem = Executors.newFixedThreadPool(ELEVATORS_AMOUNT);

    // Here you can add as many commands you wish.
    static {
        addCommand(new Command(0, 15));
        addCommand(new Command(15, 0));
        addCommand(new Command(5, 14));
        addCommand(new Command(14, 5));
        addCommand(new Command(1, 10));
        addCommand(new Command(1, 2));
        addCommand(new Command(1, 1));
        addCommand(new Command(10, 2));
        addCommand(new Command(4, 20));
        addCommand(new Command(6, 8));
        addCommand(new Command(9, 19));
        addCommand(new Command(1, 20));
        addCommand(new Command(5, 0));
        addCommand(new Command(20, 3));
        addCommand(new Command(4, 3));
        addCommand(new Command(2, 14));
        addCommand(new Command(14, 1));
        addCommand(new Command(3, 7));
        addCommand(new Command(8, 4));
        addCommand(new Command(6, 13));
        addCommand(new Command(13, 14));
    }

    // Theoretically ElevatorSystem can also be a thread that receives commands at different moments, but here it was
    // made mostly for testing (+ no requirements for that). So it reads all the commands in static block and ends
    // execution after all of them are processed.
    // Please note that the elevators may have stops between destination and source floors if they are lucky enough
    // to get them. It is possible that you won't see it easily on 7 threads, because there are not enough commands
    // to represent it properly
    public static void main(String[] args) {
        boolean isRunning = true;

        while (isRunning) {
            if (commandExists()) {
                elevatorSystem.submit(new Elevator(pollRandomCommand()));
            } else {
                isRunning = false;
            }
        }

        elevatorSystem.shutdown();
    }

    /**
     * Add command to the corresponding set.
     *
     * @param command command for elevator to use
     */
    private static void addCommand(Command command) {
        if (command.getDirection().equals(Direction.UP)) {
            UP_MOVEMENT_COMMANDS.add(command);
        } else {
            DOWN_MOVEMENT_COMMANDS.add(command);
        }
    }

    /**
     * Poll "random" command for an elevator: upward or downward motion.
     * Choosing lowest and highest floors was set intentionally to spare as many extra moves for elevators as possible.
     *
     * @return "lowest" upwards motion command or "highest" downwards motion command for an elevator
     */
    private static Command pollRandomCommand() {
        if (random.nextBoolean()) {
            if (!UP_MOVEMENT_COMMANDS.isEmpty()) {
                return UP_MOVEMENT_COMMANDS.pollFirst();
            }
        }

        return DOWN_MOVEMENT_COMMANDS.pollLast();
    }

    /**
     * Check if there are any commands left.
     *
     * @return true if at list one of the sets has at least one command left at the time
     */
    private static boolean commandExists() {
        return !UP_MOVEMENT_COMMANDS.isEmpty() || !DOWN_MOVEMENT_COMMANDS.isEmpty();
    }
}
