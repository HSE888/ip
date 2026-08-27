import java.io.IOException;

/** Adds a deadline task described by the user. */
public class DeadlineCommand extends Command {
    private final String details;

    /** Creates a deadline command from the text after its keyword. */
    public DeadlineCommand(String details) {
        this.details = details;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        String[] parts = details.split(" /by ", 2);
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Please use: deadline DESCRIPTION /by DATE.");
        }
        Task task = new Deadline(parts[0], Parser.parseDateTime(parts[1]));
        tasks.add(task);
        storage.save(tasks);
        ui.showAddedTask(task, tasks.size());
    }
}
