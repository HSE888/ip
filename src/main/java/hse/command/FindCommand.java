package hse.command;

import java.io.IOException;
import java.time.LocalDate;

import hse.parser.Parser;
import hse.storage.Storage;
import hse.task.TaskList;
import hse.ui.Ui;

/** Displays deadlines and events that occur on the requested date. */
public class FindCommand extends Command {
    private final String dateText;

    /** Creates a find command for the supplied date text. */
    public FindCommand(String dateText) {
        this.dateText = dateText;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        LocalDate date = Parser.parseDate(dateText);
        ui.showTasksOnDate(tasks, date);
    }
}
