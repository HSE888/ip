package hse.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import hse.task.Task;
import hse.task.TaskList;

/** Handles all console interaction with the user. */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private final Scanner scanner;

    /** Creates a user interface that reads from the standard input stream. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Prints the greeting displayed when the application starts. */
    public void showWelcome() {
        System.out.println(" _   _  ____  _____ \n"
                + "| | | |/ ___|| ____|\n"
                + "| |_| |\\___ \\|  _|  \n"
                + "|  _  | ___) | |___ \n"
                + "|_| |_| |____/|_____|\n");
        System.out.println("Hello! I'm HSE.");
        System.out.println("What can I do for you?");
    }

    /** Returns whether another command is available from the console. */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /** Reads and trims one command from the console. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /** Prints the divider used to separate command interactions. */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /** Prints the saved tasks with one-based task numbers. */
    public void showTaskList(TaskList tasks) {
        showNumberedTasks(tasks.getTasks());
    }

    /** Prints tasks whose descriptions match a requested keyword. */
    public void showMatchingTasks(List<Task> matchingTasks) {
        System.out.println("Here are the matching tasks in your list:");
        showNumberedTasks(matchingTasks);
    }

    /** Prints tasks that occur on a date, or a message when there are no matches. */
    public void showTasksOnDate(TaskList tasks, LocalDate date) {
        List<Integer> positions = tasks.getTaskPositionsOn(date);
        if (positions.isEmpty()) {
            System.out.println("No deadlines or events occur on that date.");
            return;
        }
        for (int position : positions) {
            System.out.println(position + "." + tasks.get(position - 1));
        }
    }

    /** Prints confirmation for an added task. */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /** Prints confirmation for a marked task. */
    public void showMarkedTask(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /** Prints confirmation for an unmarked task. */
    public void showUnmarkedTask(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /** Prints confirmation for a deleted task. */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /** Prints an application error in the established error format. */
    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    /** Prints the error shown when saved tasks cannot be loaded. */
    public void showLoadingError() {
        showError("Could not load saved tasks.");
    }

    /** Prints the farewell displayed when the application exits. */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /** Prints a list of tasks with one-based numbering. */
    private void showNumberedTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
}
