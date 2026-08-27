import java.io.IOException;

/** Ends the application after displaying its farewell message. */
public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
