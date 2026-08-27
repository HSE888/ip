import java.io.IOException;

/** Represents one parsed instruction that can be performed by the application. */
public abstract class Command {
    /** Performs this instruction using the application's collaborating objects. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws IOException;

    /** Returns whether executing this instruction ends the application. */
    public boolean isExit() {
        return false;
    }
}
