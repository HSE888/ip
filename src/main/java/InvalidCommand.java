import java.io.IOException;

/** Reports an input that does not match any supported command. */
public class InvalidCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        throw new IllegalArgumentException("Please input a valid command.");
    }
}
