import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Coordinates the application's user interface, command parsing, tasks, and storage. */
public class HSE {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /** Creates an application that stores its tasks at the supplied path. */
    public HSE(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.load();
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /** Runs the command loop until the user enters {@code bye}. */
    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = ui.readCommand(scanner);
            if (Parser.parseCommandType(command) == Parser.CommandType.BYE) {
                break;
            }
            try {
                execute(command);
            } catch (NumberFormatException e) {
                ui.showError("Please enter a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                ui.showError("That task number does not exist.");
            } catch (DateTimeParseException e) {
                ui.showError("Please use yyyy-MM-dd or d/M/yyyy, with optional HHmm time.");
            } catch (IllegalArgumentException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Could not save your tasks.");
            }
        }
        scanner.close();
        ui.showGoodbye();
    }

    /** Performs the operation represented by one user command. */
    private void execute(String command) throws IOException {
        switch (Parser.parseCommandType(command)) {
        case LIST:
            ui.showTaskList(tasks);
            break;
        case FIND:
            LocalDate date = Parser.parseDate(command.substring(5));
            ui.showTasksOnDate(tasks, date);
            break;
        case MARK:
            Task markedTask = tasks.mark(Parser.getIndex(command, tasks.size()));
            storage.save(tasks);
            ui.showMarkedTask(markedTask);
            break;
        case UNMARK:
            Task unmarkedTask = tasks.unmark(Parser.getIndex(command, tasks.size()));
            storage.save(tasks);
            ui.showUnmarkedTask(unmarkedTask);
            break;
        case DELETE:
            Task deletedTask = tasks.delete(Parser.getIndex(command, tasks.size()));
            storage.save(tasks);
            ui.showDeletedTask(deletedTask, tasks.size());
            break;
        case TODO:
            addTodo(command);
            break;
        case DEADLINE:
            addDeadline(command);
            break;
        case EVENT:
            addEvent(command);
            break;
        default:
            throw new IllegalArgumentException("Please input a valid command.");
        }
    }

    /** Adds a todo described by the command. */
    private void addTodo(String command) throws IOException {
        if (command.equals("todo")) {
            throw new IllegalArgumentException("Please give the description of a todo.");
        }
        String description = command.substring(5).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Please give the description of a todo.");
        }
        tasks.add(new ToDos(description));
        storage.save(tasks);
        ui.showAddedTask(tasks.get(tasks.size() - 1), tasks.size());
    }

    /** Adds a deadline described by the command. */
    private void addDeadline(String command) throws IOException {
        String[] parts = command.substring(9).split(" /by ", 2);
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Please use: deadline DESCRIPTION /by DATE.");
        }
        tasks.add(new Deadline(parts[0], Parser.parseDateTime(parts[1])));
        storage.save(tasks);
        ui.showAddedTask(tasks.get(tasks.size() - 1), tasks.size());
    }

    /** Adds an event described by the command. */
    private void addEvent(String command) throws IOException {
        String[] fromParts = command.substring(6).split(" /from ", 2);
        if (fromParts.length != 2) {
            throw new IllegalArgumentException("Please use: event DESCRIPTION /from START /to END.");
        }
        String[] toParts = fromParts[1].split(" /to ", 2);
        if (fromParts[0].isEmpty() || toParts.length != 2 || toParts[0].isEmpty() || toParts[1].isEmpty()) {
            throw new IllegalArgumentException("Please use: event DESCRIPTION /from START /to END.");
        }
        tasks.add(new Event(fromParts[0], Parser.parseDateTime(toParts[0]),
                Parser.parseDateTime(toParts[1])));
        storage.save(tasks);
        ui.showAddedTask(tasks.get(tasks.size() - 1), tasks.size());
    }

    /** Starts the application using its standard data file. */
    public static void main(String[] args) {
        new HSE("data/duke.txt").run();
    }
}
